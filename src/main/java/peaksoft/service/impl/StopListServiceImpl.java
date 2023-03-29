package peaksoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.PaginationResponseStopList;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.entities.MenuItem;
import peaksoft.entities.StopList;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.service.StopListService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public StopListServiceImpl(StopListRepository stopListRepository, MenuItemRepository menuItemRepository) {
        this.stopListRepository = stopListRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public SimpleResponse saveStopList(StopListRequest stopListRequest) {
        MenuItem menuItem = menuItemRepository.findById(stopListRequest.menuItemId()).orElseThrow(()
                -> new NoSuchElementException("MenuItem is not"));
            StopList stopList = new StopList();
            stopList.setDate(stopListRequest.date());
            stopList.setReason(stopListRequest.reason());
            stopList.setMenuItem(menuItem);
            menuItem.setStopList(stopList);
            stopListRepository.save(stopList);

            return SimpleResponse.builder().status(HttpStatus.OK)
                    .message(String.format("StopList with id:" + stopList.getId() + " is successfully saved!")).build();
        }

    @Override
    public List<StopListResponse> getAllStopLists() {
        return stopListRepository.getAllStopLists();
    }

    @Override
    public StopListResponse getStopListById(Long id) {
        return stopListRepository.getStopById(id).orElseThrow(()->
                new NoSuchElementException("StopList with id: " + id + " not found"));
    }

    @Override
    public SimpleResponse updateStopList(Long id, StopListRequest stopListRequest) {
        StopList stopList = stopListRepository.findById(id).orElseThrow(()->
                new NoSuchElementException("StopList with id: " + id + " is not found!"));
        MenuItem menuItem = menuItemRepository.findById(stopListRequest.menuItemId()).orElseThrow(()->
                new NoSuchElementException("MenuItem with if: " + id + " is not found!"));

        stopList.setDate(stopListRequest.date());
        stopList.setReason(stopListRequest.reason());
        stopList.setMenuItem(menuItem);
        menuItem.setStopList(stopList);

        stopListRepository.save(stopList);

        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("StopList with id: " +stopList.getId() + " is successfully updated!")).build();
    }

    @Override
    public SimpleResponse deleteStopListById(Long id) {
        StopList stopList = stopListRepository.findById(id).orElseThrow(()->
                new NoSuchElementException("StopList with id: " + id + " is not found!"));
        stopList.getMenuItem().setInStock(true);
        stopListRepository.deleteById(id);

        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("StopList with id: " + stopList.getId() + " is successfully deleted!")).build();
    }

    @Override
    public PaginationResponseStopList getStopListPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StopList> stopListPage = stopListRepository.findAll(pageable);
        PaginationResponseStopList paginationResponseStopList = new PaginationResponseStopList();
        paginationResponseStopList.setStopLists(stopListPage.getContent());
        paginationResponseStopList.setPageSize(stopListPage.getSize());

        return paginationResponseStopList;
    }
}
