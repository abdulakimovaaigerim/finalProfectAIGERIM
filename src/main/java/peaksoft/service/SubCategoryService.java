package peaksoft.service;

import peaksoft.dto.request.SubcategoryRequest;
import peaksoft.dto.response.PaginationResponseSubCa;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubcategoryResponse;

import java.util.List;
import java.util.Map;

public interface SubCategoryService {
    SimpleResponse saveSubCategory(SubcategoryRequest subcategoryRequest);
    List<SubcategoryResponse> getByCategoryName(String word);
    SubcategoryResponse getSubCategoryById(Long id);
    SimpleResponse updateSubCategory(Long id, SubcategoryRequest subcategoryRequest);
    SimpleResponse deleteById(Long id);
    Map<String,List<SubcategoryResponse>> filterByCategory();

    PaginationResponseSubCa getSubCaPagination(int page, int size);
}
