package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.MenuRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.PaginationResponseCheque;
import peaksoft.dto.response.PaginationResponseMenuItem;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.MenuService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menuItems")
public class MenuItemApi {
    private final MenuService menuService;

    @Autowired
    public MenuItemApi(MenuService menuService) {
        this.menuService = menuService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping
    public SimpleResponse save(@RequestBody @Valid MenuRequest menuRequest) {
        return menuService.save(menuRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/{id}")
    public MenuItemResponse getById(@PathVariable Long id) {
        return menuService.getByIdMenuItem(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid MenuRequest request) {
        return menuService.updateMenuItem(id, request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return menuService.deleteMenuItemById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @PutMapping(("/search"))
    public List<MenuItemResponse> globalSearch(@RequestParam(required = false) @Valid String word) {
        return menuService.globalSearch(word);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/sort")
    public List<MenuItemResponse> sortByPrice(@RequestParam String word) {
        return menuService.sortByPrice(word);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/grouping")
    public Map<Boolean, List<MenuItemResponse>> filter() {
        return menuService.filterByVegetarian();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping
    public List<MenuItemResponse> getAll() {
        return menuService.getAllMenus();
    }

    @GetMapping("/paginationMenuItem")
    public PaginationResponseMenuItem getMenuItemPagination(@RequestParam int page, @RequestParam int size) {
        return menuService.getMenuItemPagination(page, size);

    }
}