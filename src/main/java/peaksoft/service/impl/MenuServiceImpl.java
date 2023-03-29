package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.MenuRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.PaginationResponseMenuItem;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entities.*;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.service.MenuService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository repository;
    private final SubcategoryRepository subcategoryRepository;
    private final StopListRepository stopListRepository;


    @Override
    public SimpleResponse save(MenuRequest menuRequest) {
        Restaurant restaurant = repository.findById(menuRequest.restaurantId()).orElseThrow(() ->
                new NoSuchElementException("Restaurant with id: " + menuRequest.restaurantId() + " is not found!"));

        SubCategory subCategory = subcategoryRepository.findById(menuRequest.subCategoryId()).orElseThrow(() ->
                new NoSuchElementException("SubCategory with id: " + menuRequest.subCategoryId() + " is not found!"));
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuRequest.name());
        menuItem.setImage(menuRequest.image());
        menuItem.setPrice(menuRequest.price());
        menuItem.setDescription(menuRequest.description());
        menuItem.setIsVegetarian(menuRequest.isVegetarian());
        menuItem.setRestaurant(restaurant);
        menuItem.setSubCategory(subCategory);
        menuItem.setInStock(true);
        menuItemRepository.save(menuItem);

        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("MenuItem with id: " + menuItem.getId() + " is saved!")).build();
    }


    @Override
    public MenuItemResponse getByIdMenuItem(Long id) {
        return menuItemRepository.getMenuById(id).orElseThrow(() ->
                new NoSuchElementException("MenuItem with id: " + id + " is not found!"));
    }

    @Override
    public SimpleResponse updateMenuItem(Long id, MenuRequest menuRequest) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("MenuItem with id: " + id + " is not found!"));
        menuItem.setName(menuRequest.name());
        menuItem.setImage(menuRequest.image());
        menuItem.setPrice(menuRequest.price());
        menuItem.setIsVegetarian(menuRequest.isVegetarian());
        menuItemRepository.save(menuItem);

        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Menu with id: " + id + " is successfully updated!")).build();
    }

    @Override
    public SimpleResponse deleteMenuItemById(Long id) {

        if (!menuItemRepository.existsById(id)){
            return SimpleResponse.builder().status(HttpStatus.NOT_FOUND)
                    .message(String.format("MenuItem with id: " + id + " is not found!")).build();
        }

        menuItemRepository.deleteById(id);
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Menu with id: " + id + " is successfully deleted!")).build();
    }

    @Override
    public List<MenuItemResponse> globalSearch(String word) {
        LocalDate localDate = LocalDate.now();

        if (word == null) {
            for (StopList stopList : stopListRepository.findAll()) {
                if (stopList.getDate().equals(localDate)) {
                    stopList.getMenuItem().setInStock(false);
                    stopListRepository.save(stopList);
                } else {
                    stopList.getMenuItem().setInStock(true);
                    stopListRepository.save(stopList);
                }
            }
            return menuItemRepository.getAllMenus();
        } else {
            return menuItemRepository.globalSearch(word);
        }
    }

    @Override
    public List<MenuItemResponse> sortByPrice(String word) {
        if (word.equals("asc")) {
            return menuItemRepository.sortByPriceAsc();
        } else if (word.equals("desc")) {
            return menuItemRepository.sortByPriceDesc();
        }else
        return menuItemRepository.getAllMenus();
    }

    @Override
    public Map<Boolean, List<MenuItemResponse>> filterByVegetarian() {
        return menuItemRepository.getAllMenus().stream().collect(Collectors.groupingBy(MenuItemResponse::isVegetarian));
    }

    @Override
    public List<MenuItemResponse> getAllMenus() {
        return menuItemRepository.getAllMenus();
    }

    @Override
    public List<MenuItemResponse> getAllMenuItems() {
        return menuItemRepository.getAllMenus();
    }

    @Override
    public PaginationResponseMenuItem getMenuItemPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MenuItem> menuItemPage = menuItemRepository.findAll(pageable);
        PaginationResponseMenuItem paginationResponseMenuItem = new PaginationResponseMenuItem();
        paginationResponseMenuItem.setMenuItems(menuItemPage.getContent());
        paginationResponseMenuItem.setCurrentPage(menuItemPage.getTotalPages());
        paginationResponseMenuItem.setPageSize(menuItemPage.getSize());

        return paginationResponseMenuItem;
    }
}