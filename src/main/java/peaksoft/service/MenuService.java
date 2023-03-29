package peaksoft.service;

import peaksoft.dto.request.MenuRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.PaginationResponseMenuItem;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;
import java.util.Map;

public interface MenuService {
    SimpleResponse save(MenuRequest menuRequest);
    MenuItemResponse getByIdMenuItem(Long id);
    SimpleResponse updateMenuItem(Long id, MenuRequest menuRequest);
    SimpleResponse deleteMenuItemById(Long id);
    List<MenuItemResponse> globalSearch(String word);
    List<MenuItemResponse> sortByPrice(String word);
    Map<Boolean, List<MenuItemResponse>> filterByVegetarian();
    List<MenuItemResponse> getAllMenus();

    List<MenuItemResponse> getAllMenuItems();

    PaginationResponseMenuItem getMenuItemPagination(int page, int size);
}

