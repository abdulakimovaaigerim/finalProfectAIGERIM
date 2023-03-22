package peaksoft.service;

import org.springframework.stereotype.Service;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;


public interface RestaurantService {

    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest);

    RestaurantResponse getByIdRes(Long id);

    SimpleResponse removeById(Long id);

    SimpleResponse updateRes(Long id, RestaurantRequest restaurantRequest);

    List<RestaurantResponse> getAllRestaurants();
}
