package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.entities.Restaurant;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("select new peaksoft.dto.response.RestaurantResponse(r.id,r.name,r.location,r.restType,r.numberOfEmployees) from Restaurant r")
    List<RestaurantResponse> getAllRestaurants();

    @Query("select new peaksoft.dto.response.RestaurantResponse(r.id,r.name,r.location,r.restType,r.numberOfEmployees) from Restaurant r where r.id=:id")
    RestaurantResponse getRestaurantById(Long id);

    @Override
    Page<Restaurant> findAll(Pageable pageable);

}
