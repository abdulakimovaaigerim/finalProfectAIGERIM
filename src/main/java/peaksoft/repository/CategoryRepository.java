package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select new peaksoft.dto.response.CategoryResponse(c.id,c.name) from Category c")
    List<CategoryResponse> getAllCategory();

    @Query("select new peaksoft.dto.response.CategoryResponse(c.id,c.name) from Category c where c.id=:id")
    Optional<CategoryResponse> getCategoryById(Long id);

    @Override
    Page<Category> findAll(Pageable pageable);

}
