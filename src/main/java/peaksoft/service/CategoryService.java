package peaksoft.service;

import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.PaginationResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface CategoryService {
    SimpleResponse save(CategoryRequest categoryRequest);
    List<CategoryResponse> getAll();
    CategoryResponse getById(Long id);
    SimpleResponse deleteById(Long id);
    SimpleResponse update(Long id, CategoryRequest categoryRequest);

    PaginationResponse getCategoryPagination(int page, int size);
}
