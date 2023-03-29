package peaksoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.ChequeOneDayWaiterRequest;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.ChequeResponseWaiter;
import peaksoft.dto.response.PaginationResponseCheque;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entities.Cheque;
import peaksoft.entities.MenuItem;
import peaksoft.entities.User;
import peaksoft.enums.Role;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ChequeService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ChequeServiceImpl implements ChequeService {

    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public ChequeServiceImpl(ChequeRepository chequeRepository, UserRepository userRepository, MenuItemRepository menuItemRepository) {
        this.chequeRepository = chequeRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public SimpleResponse saveCheque(ChequeRequest chequeRequest) {
        double counter = 0;
        User user = userRepository.findById(chequeRequest.userId()).orElseThrow(() ->
                new NoSuchElementException("User with id: " + chequeRequest.userId() + " is not found!"));
        Cheque cheque = new Cheque();
        cheque.setUser(user);
        for (MenuItem menuItem : menuItemRepository.findAllById(chequeRequest.menuItemsId())) {
            cheque.addMenuItem(menuItem);
            counter += menuItem.getPrice();
        }
        cheque.setPriceAverage((int) counter);
        cheque.setCreateAt(chequeRequest.createAt());
        double total = (counter * cheque.getUser().getRestaurant().getService()) / 100;
        cheque.setGrandTotal(counter + total);
        chequeRepository.save(cheque);


        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Cheque with id: " + cheque.getId() + " is successfully saved!")).build();

    }

    @Override
    public List<ChequeResponse> getAllCheques() {
        List<Cheque> cheques = chequeRepository.findAll();
        List<ChequeResponse> chequeResponses = new ArrayList<>();
        ChequeResponse chequeResponse = new ChequeResponse();
        for (Cheque cheque : cheques) {
            chequeResponse.setId(cheque.getId());
            chequeResponse.setFullName(cheque.getUser().getFirstName() + cheque.getUser().getFirstName());
            chequeResponse.setMenuItems(cheque.getMenuItems());
            chequeResponse.setAveragePrice(cheque.getPriceAverage());
            chequeResponse.setService(cheque.getUser().getRestaurant().getService());
            chequeResponse.setGrandTotal(cheque.getGrandTotal());
            chequeResponses.add(chequeResponse);

        }
        return chequeResponses;
    }

    @Override
    public ChequeResponse getByIdCheque(Long id) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Cheque with id: " + id + " is not found!"));
        ChequeResponse chequeResponse = new ChequeResponse();
        chequeResponse.setId(cheque.getId());
        chequeResponse.setFullName(cheque.getUser().getFirstName() + cheque.getUser().getLastName());
        chequeResponse.setMenuItems(cheque.getMenuItems());
        chequeResponse.setAveragePrice(cheque.getPriceAverage());
        chequeResponse.setService(cheque.getUser().getRestaurant().getService());
        chequeResponse.setGrandTotal(cheque.getGrandTotal());


        return chequeResponse;
    }

    @Override
    public SimpleResponse updateCheque(Long id, ChequeRequest chequeRequest) {

        Cheque cheque = chequeRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Cheque with id: " + id + " is not found!"));


        List<MenuItem> menuItems = new ArrayList<>();
        for (MenuItem menuItem : cheque.getMenuItems()) {
            for (Long aLong : chequeRequest.menuItemsId()) {
                if (menuItem.getId().equals(aLong))
                    menuItems.add(menuItem);
            }
        }

        User user = userRepository.findById(id).orElseThrow(()->
                new NoSuchElementException("User with id: " + chequeRequest.userId() + " is not found!"));

        cheque.setCreateAt(chequeRequest.createAt());
        cheque.setMenuItems(menuItems);
        cheque.setUser(user);
        user.addCheque(cheque);
        chequeRepository.save(cheque);

        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Cheque with id: " + cheque.getId() + " is successfully updated!")).build();
    }

    @Override
    public SimpleResponse deleteChequeById(Long id) {
        chequeRepository.deleteById(id);
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Cheque with id: " + id + " is successfully deleted!!")).build();
    }

    @Override
    public ChequeResponseWaiter getAverageSumma(ChequeOneDayWaiterRequest chequeOneDayWaiterRequest) {
        User user = userRepository.findById(chequeOneDayWaiterRequest.waiterId()).orElseThrow(()->
                new NoSuchElementException("User with id: " + chequeOneDayWaiterRequest.waiterId() + " is not found"));

        int chequeCount = 0;
        int averagePrice = 0;
        int totalAmount = 0;

        if (user.getRole().equals(Role.WAITER)) {
            for (Cheque cheque : user.getCheques()) {
                if (cheque.getCreateAt().equals(chequeOneDayWaiterRequest.date())) {
                    double service = cheque.getPriceAverage() * user.getRestaurant().getService() / 100;
                    totalAmount += service + cheque.getPriceAverage();
                    chequeCount++;
                    averagePrice += cheque.getPriceAverage();

                }
            }
        }
        return ChequeResponseWaiter.builder()
                .price(averagePrice)
                .date(chequeOneDayWaiterRequest.date())
                .numberOfCheques(chequeCount)
                .service(user.getRestaurant().getService())
                .grandTotal(totalAmount)
                .waiterFullName(user.getFirstName() + " " + user.getLastName())
                .build();    }

    @Override
    public Double getAverageSumma(Long restaurantId) {
        return chequeRepository.getAverageSum(restaurantId);

    }

    @Override
    public PaginationResponseCheque getChequePagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cheque> chequePage = chequeRepository.findAll(pageable);
        PaginationResponseCheque paginationResponse = new PaginationResponseCheque();
        paginationResponse.setCheques(chequePage.getContent());
        paginationResponse.setCurrentPage(chequePage.getTotalPages());
        paginationResponse.setPageSize(chequePage.getSize());

        return paginationResponse;
    }
}
