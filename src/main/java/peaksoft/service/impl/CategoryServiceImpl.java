package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.PaginationResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entities.Category;
import peaksoft.repository.CategoryRepository;
import peaksoft.service.CategoryService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public SimpleResponse save(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        categoryRepository.save(category);

        return SimpleResponse.builder().status(HttpStatus.OK)
                .message("Category with id: " +category.getId()+ " is successfully saved!").build();
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.getAllCategory();
    }

    @Override
    public CategoryResponse getById(Long id) {
        return categoryRepository.getCategoryById(id).orElseThrow(()->
                new NoSuchElementException("Category with id: " + id + " is not found!"));
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        categoryRepository.deleteById(id);
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Category with id: " + id + " is successfully deleted")).build();
    }

    @Override
    public SimpleResponse update(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(()->
                new NoSuchElementException("Category with id: " + id + " is not found"));
        category.setName(categoryRequest.name());
        categoryRepository.save(category);

        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Category with id: " + id + " is successfully updated!")).build();
    }

    @Override
    public PaginationResponse getCategoryPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setCategoryResponseList(categoryPage.getContent());
        paginationResponse.setCurrentPage(pageable.getPageNumber());
        paginationResponse.setPageSize(pageable.getPageSize());
        return paginationResponse;
    }
}
