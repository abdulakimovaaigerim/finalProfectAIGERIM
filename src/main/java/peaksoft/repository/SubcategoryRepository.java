package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.SubcategoryResponse;
import peaksoft.entities.SubCategory;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<SubCategory, Long> {
    @Query("select new peaksoft.dto.response.SubcategoryResponse(s.category.name, s.id, s.name) from SubCategory s")
    List<SubcategoryResponse> getAllSubCa();
    @Query("select new peaksoft.dto.response.SubcategoryResponse(s.category.name,s.id,s.name) from SubCategory s where s.id=:id")
    Optional<SubcategoryResponse> getSubCaById(Long id);
    @Query("select new peaksoft.dto.response.SubcategoryResponse(s.category.name,s.id,s.name) from SubCategory s where s.category.name ilike %:word% order by s.name")
    List<SubcategoryResponse> getByCategory(String word);

    @Override
    Page<SubCategory> findAll(Pageable pageable);
}
