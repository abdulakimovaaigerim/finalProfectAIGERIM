package peaksoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.PaginationResponseRest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entities.Restaurant;
import peaksoft.exceptiron.BadCredentialException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.service.RestaurantService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }


    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {
            List<Restaurant> restaurants = restaurantRepository.findAll();
            if (restaurants.size() > 0) {
                throw new BadCredentialException("Only one restaurant will be!!!");
            }
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        restaurant.setRestType(restaurantRequest.restType());
        restaurant.setService(restaurantRequest.service());

        restaurantRepository.save(restaurant);

        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Restaurant with name: " + restaurant.getName() + " is saved!")).build();
    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepository.getAllRestaurants();
    }

    @Override
    public SimpleResponse updateRestaurant(Long id, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(()->
                new NoSuchElementException("Restaurant with id: " + id + " is not found!"));
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurant.getLocation());
        restaurant.setRestType(restaurantRequest.restType());
        restaurant.setService(restaurantRequest.service());
        restaurantRepository.save(restaurant);

        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Restaurant with name: " + restaurant.getName() + " is successfully updated!!")).build();
    }

    @Override
    public SimpleResponse deleteRestaurantById(Long id) {
        restaurantRepository.deleteById(id);
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Restaurant with name: " + id + " is successfully deleted!")).build();
    }

    @Override
    public RestaurantResponse getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(()->
                new NoSuchElementException("Restaurant with id: " + id + " is not found!"));
        restaurant.setNumberOfEmployees(restaurant.getUsers().size());
        restaurantRepository.save(restaurant);

        return restaurantRepository.getRestaurantById(id);
    }

    @Override
    public PaginationResponseRest getRestPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Restaurant> restaurantPage = restaurantRepository.findAll(pageable);
        PaginationResponseRest paginationResponseRest = new PaginationResponseRest();
        paginationResponseRest.setRestaurants(restaurantPage.getContent());
        paginationResponseRest.setPageSize(restaurantPage.getSize());
        paginationResponseRest.setCurrentPage(restaurantPage.getTotalPages());

        return paginationResponseRest;
    }
}
