package peaksoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.SubcategoryRequest;
import peaksoft.dto.response.PaginationResponseSubCa;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubcategoryResponse;
import peaksoft.entities.Category;
import peaksoft.entities.SubCategory;
import peaksoft.exceptiron.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.service.SubCategoryService;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SubCategoryServiceImpl(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public SimpleResponse saveSubCategory(SubcategoryRequest subcategoryRequest) {
        SubCategory subCategory = new SubCategory();
        Category category = categoryRepository.findById(subcategoryRequest.categoryId()).orElseThrow(() ->
                new NotFoundException("SubCategory with id: " + subcategoryRequest.categoryId() + " is no exist!"));
        subCategory.setName(subcategoryRequest.name());
        subCategory.setCategory(category);
        subcategoryRepository.save(subCategory);

        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Subcategory with id: " + subCategory.getId() + " is successfully saved!")).build();

    }

    @Override
    public List<SubcategoryResponse> getByCategoryName(String word) {
        if (word == null) {
            return subcategoryRepository.getAllSubCa();
        } else {
            return subcategoryRepository.getByCategory(word);
        }
    }

    @Override
    public SubcategoryResponse getSubCategoryById(Long id) {
        return subcategoryRepository.getSubCaById(id).orElseThrow(()->
                new NotFoundException("SubCategory with id: " + id + " is not found!"));

    }

    @Override
    public SimpleResponse updateSubCategory(Long id, SubcategoryRequest subcategoryRequest) {
        SubCategory subCategory = subcategoryRepository.findById(id).orElseThrow(()->
                new NoSuchElementException("SubCategory with id: " + id + " is not found!"));
        Category category = categoryRepository.findById(id).orElseThrow(()->
                new NoSuchElementException("Category with id: " + id + " is not found"));

        subCategory.setName(subcategoryRequest.name());
        subCategory.setCategory(category);
        category.addSubCa(subCategory);
        subcategoryRepository.save(subCategory);

        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Subcategory with id: " + subCategory.getId() + " is successfully saved!")).build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        subcategoryRepository.deleteById(id);
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Subcategory with id: " + id + " is successfully deleted!")).build();
    }

    @Override
    public Map<String, List<SubcategoryResponse>> filterByCategory() {
        return subcategoryRepository.getAllSubCa().stream().collect(Collectors.groupingBy(SubcategoryResponse::categoryName));
    }

    @Override
    public PaginationResponseSubCa getSubCaPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SubCategory> subCategoryPage = subcategoryRepository.findAll(pageable);
        PaginationResponseSubCa paginationResponseSubCa = new PaginationResponseSubCa();
        paginationResponseSubCa.setSubCategories(subCategoryPage.getContent());
        paginationResponseSubCa.setPageSize(subCategoryPage.getSize());
        paginationResponseSubCa.setCurrentPage(subCategoryPage.getTotalPages());

        return paginationResponseSubCa;
    }
}
