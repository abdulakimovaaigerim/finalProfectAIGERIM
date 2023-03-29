package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.SubcategoryRequest;
import peaksoft.dto.response.PaginationResponseStopList;
import peaksoft.dto.response.PaginationResponseSubCa;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubcategoryResponse;
import peaksoft.service.SubCategoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subCategories")
public class SubCategoryApi {
    private final SubCategoryService subcategoryService;

@Autowired
    public SubCategoryApi(SubCategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public SimpleResponse save(@RequestBody @Valid SubcategoryRequest subcategoryRequest){
        return subcategoryService.saveSubCategory(subcategoryRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/{id}")
    public SubcategoryResponse getById(@PathVariable Long id){
        return subcategoryService.getSubCategoryById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER','CHEF')")
    @GetMapping("/get")
    public List<SubcategoryResponse> get(@RequestParam String word){
        return subcategoryService.getByCategoryName(word);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid SubcategoryRequest subcategoryRequest){
        return subcategoryService.updateSubCategory(id,subcategoryRequest);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return subcategoryService.deleteById(id);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/grouping")
    public Map<String,List<SubcategoryResponse>> filter(){
        return subcategoryService.filterByCategory();
    }
    @GetMapping("/paginationSubCa")
    public PaginationResponseSubCa getSubCaPagination(@RequestParam int page, @RequestParam int size) {
        return subcategoryService.getSubCaPagination(page, size);

    }
}