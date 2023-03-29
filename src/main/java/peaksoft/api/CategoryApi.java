package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.PaginationResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryApi {
    private final CategoryService categoryService;

    @Autowired
    public CategoryApi(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping
    public SimpleResponse save(@RequestBody @Valid CategoryRequest categoryRequest) {
        return categoryService.save(categoryRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping
    public List<CategoryResponse> getAll() {
        return categoryService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/{id}")
    public CategoryResponse getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid CategoryRequest categoryRequest) {
        return categoryService.update(id, categoryRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return categoryService.deleteById(id);
    }

    @GetMapping("/pagination")
    public PaginationResponse getCategoryPagination(@RequestParam int page, @RequestParam int size){
        return categoryService.getCategoryPagination(page, size);

    }

}

