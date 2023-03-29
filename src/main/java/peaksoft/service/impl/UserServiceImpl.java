package peaksoft.service.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.jwt.JwtUtil;
import peaksoft.dto.request.AuthRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.request.UserTokenRequest;
import peaksoft.dto.request.UserUpdateRequest;
import peaksoft.dto.response.PaginationResponseUser;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.dto.response.UserTokenResponse;
import peaksoft.entities.Restaurant;
import peaksoft.entities.User;
import peaksoft.enums.Role;
import peaksoft.exceptiron.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserRepository repository, RestaurantRepository restaurantRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserTokenResponse authenticate(UserTokenRequest userTokenRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userTokenRequest.email(),
                        userTokenRequest.password()
                )
        );
        User userName = repository.findByEmail(userTokenRequest.email()).orElseThrow(()->
                new NoSuchElementException("User with email: " + userTokenRequest.email()));

        String token = jwtUtil.generateToken(userName);

        return UserTokenResponse.builder()
                .token(token)
                .email(userName.getEmail())
                .build();
    }

    @PostConstruct
    public void saveAdmin(){
        User user = new User();
        user.setRole(Role.ADMIN);
        user.setDateOfBirth(LocalDate.of(2005, 6, 21));
        user.setFirstName("Aigerim");
        user.setLastName("Bektenova");
        user.setPhoneNumber("+99655487654");
        user.setEmail("admin@gmail.com");
        user.setPassword(encoder.encode("admin"));
        user.setExpiration(3);
        if (!repository.existsByEmail(user.getEmail())) {
            repository.save(user);
        }
    }
    @Override
    public SimpleResponse user(AuthRequest authRequest) {
        Restaurant restaurant = restaurantRepository.findById(authRequest.restId()).orElseThrow();
        Boolean exists = repository.existsByEmail(authRequest.email());
        if (!exists) {
            User user = new User();
            user.setFirstName(authRequest.firstName());
            user.setLastName(authRequest.lastName());
            user.setDateOfBirth(authRequest.dateOfBirth());
            user.setEmail(authRequest.email());
            user.setPassword(authRequest.password());
            user.setPhoneNumber(authRequest.phoneNumber());
            user.setExpiration(authRequest.expiration());
            user.setRole(authRequest.role());
            int year = LocalDate.now().minusYears(authRequest.dateOfBirth().getYear()).getYear();
            if (authRequest.phoneNumber().startsWith("+996")) {
                if (user.getRole().equals(Role.CHEF)) {
                    if (year >= 25 && year <= 45 && authRequest.expiration() >= 2) {
                        restaurant.addUser(user);
                        user.setRestaurant(restaurant);
                        repository.save(user);
                        return SimpleResponse.builder().status(HttpStatus.OK).message("User with id: " + user.getId() + " is successfully saved").build();
                    } else {
                        return SimpleResponse.builder().status(HttpStatus.BAD_REQUEST).message("Chef's years old should be between 25-45 and experience>=2").build();
                    }
                } else if (user.getRole().equals(Role.WAITER)) {
                    if (year >= 18 && year <= 30 && user.getExpiration() >= 1) {
                        restaurant.addUser(user);
                        user.setRestaurant(restaurant);
                        repository.save(user);
                        return SimpleResponse.builder().status(HttpStatus.OK).message("User with id: " + user.getId() + " is successfully saved").build();
                    } else {
                        return SimpleResponse.builder().status(HttpStatus.BAD_REQUEST).message("Waiter's years old should be between 18-30 and experience>=1").build();
                    }
                }
            } else {
                return SimpleResponse.builder().status(HttpStatus.BAD_REQUEST).message("Phone number should starts with +996").build();
            }
        } else {
            return SimpleResponse.builder().status(HttpStatus.CONFLICT).message("Email already exist!").build();
        }
        return null;
    }




    @Override
    public List<UserResponse> jobApplication(Long id, String word) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(()->new NoSuchElementException("Restaurant with no exist"));
        if (word.equals("Vacancy")) {
            return repository.getAllApp();
        } else if (word.equals("accept")) {
            List<UserResponse> users = repository.getAllUsers(restaurant.getId());
            if (users.size() <= 15) {
            } else
                SimpleResponse.builder().status(HttpStatus.FORBIDDEN).message("No vacancy").build();
        } else if (word.equals("cancel")) {
            deleteUserById(id);
        } else {
            SimpleResponse.builder().status(HttpStatus.FORBIDDEN).message("User id or keyWord not matched!").build();
        }
        return null;
    }



    @Override
    public UserResponse getUserById(Long id) {
        return repository.getUserById(id).orElseThrow(() ->
                new NoSuchElementException("User with id: " + id + " is no exist!"));
    }

    @Override
    public List<UserResponse> getAllUsersById(Long restaurantId) {
        return repository.getAllUsers(restaurantId);
    }

    @Override
    public SimpleResponse updateUser(Long id, UserUpdateRequest request) {
        User user = repository.findById(id).orElseThrow(() ->
                new NotFoundException("User with id: " + id + " is no exist!"));

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setDateOfBirth(request.dateOfBirth());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setPhoneNumber(request.phoneNumber());
        user.setExpiration(request.expiration());
        user.setRole(request.role());

        repository.save(user);

        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("User with id: " + id + " is successfully updated")).build();
    }

    @Override
    public SimpleResponse deleteUserById(Long id) {
        repository.deleteById(id);
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message("User with id: " + id + " is successfully deleted!").build();

    }

    @Override
    public PaginationResponseUser getUserPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = repository.findAll(pageable);
        PaginationResponseUser paginationResponseUser = new PaginationResponseUser();
        paginationResponseUser.setUsers(userPage.getContent());
        paginationResponseUser.setPageSize(userPage.getSize());
        paginationResponseUser.setCurrentPage(userPage.getTotalPages());

        return paginationResponseUser;
    }

    private User convert(UserRequest userRequest){
        User user = new User();
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setDateOfBirth(userRequest.dateOfBirth());
        user.setEmail(userRequest.email());
        user.setPassword(userRequest.password());
        user.setExpiration(userRequest.experience());
        user.setRole(userRequest.role());
        return user;
    }
}