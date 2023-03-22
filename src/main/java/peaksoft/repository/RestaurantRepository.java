package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.entities.Restaurant;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("select new peaksoft.dto.response.RestaurantResponse(r.name, r.location, r.restType, r.numberOfEmployees, r.service) from Restaurant r where r.id=?1")
    RestaurantResponse getByIdRes(Long id);


    @Query("select new peaksoft.dto.response.RestaurantResponse(r.name, r.location, r.restType, r.numberOfEmployees, r.service) from Restaurant r")
    List<RestaurantResponse> getAllRestaurants();
}
