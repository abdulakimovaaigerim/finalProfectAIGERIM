package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entities.Restaurant;
import peaksoft.repository.RestaurantRepository;
import peaksoft.service.RestaurantService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {

        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        restaurant.setRestType(restaurantRequest.restType());
        restaurant.setService(restaurantRequest.service());

        restaurantRepository.save(restaurant);

        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Restaurant with name: " +
                        restaurant.getName() + "successfully saved ")).build();
    }

    @Override
    public RestaurantResponse getByIdRes(Long id) {
        return restaurantRepository.getByIdRes(id);
    }

    @Override
    public SimpleResponse removeById(Long id) {
        if (!restaurantRepository.existsById(id)) {
            return SimpleResponse.builder().status(HttpStatus.NOT_FOUND)
                    .message(String.format("Restaurant with id: " + id + " not found")).build();
        }
        restaurantRepository.deleteById(id);
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Restaurant with id: " + id + " is deleted!")).build();
    }

    @Override
    public SimpleResponse updateRes(Long id, RestaurantRequest restaurantRequest) {
        if (!restaurantRepository.existsById(id)) {
            return SimpleResponse.builder().status(HttpStatus.NOT_FOUND)
                    .message(String.format("Restaurant with id: " + id + " not found")).build();
        }
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(()->
                new NoSuchElementException("not found id"));
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        restaurant.setRestType(restaurantRequest.restType());
        restaurant.setService(restaurantRequest.service());

        restaurantRepository.save(restaurant);

        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Restaurant with id: " + id + "is updated")).build();
    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepository.getAllRestaurants();
    }

}
