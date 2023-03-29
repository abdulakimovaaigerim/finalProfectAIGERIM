package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.StopListResponse;
import peaksoft.entities.StopList;

import java.util.List;
import java.util.Optional;

public interface StopListRepository extends JpaRepository<StopList, Long> {

    @Query("select new peaksoft.dto.response.StopListResponse(s.menuItem.name,s.id,s.reason,s.date) from StopList s")
    List<StopListResponse> getAllStopLists();

    @Query("select new peaksoft.dto.response.StopListResponse(s.menuItem.name,s.id,s.reason,s.date) from StopList s where s.id=:id")
    Optional<StopListResponse> getStopById(Long id);


    @Modifying
    @Query("delete from StopList s where s.id = ?1")
    void delete(Long stopListId);
    @Override
    Page<StopList> findAll(Pageable pageable);
}
