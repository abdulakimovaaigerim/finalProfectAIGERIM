package peaksoft.service;


import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.PaginationResponseRest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface RestaurantService {
    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest);
    List<RestaurantResponse> getAllRestaurants();
    SimpleResponse updateRestaurant(Long id, RestaurantRequest restaurantRequest);
    SimpleResponse deleteRestaurantById(Long id);
    RestaurantResponse getRestaurantById(Long id);
    PaginationResponseRest getRestPagination(int page, int size);


}
