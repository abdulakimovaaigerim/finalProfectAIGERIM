package peaksoft.service.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.jwt.JwtUtil;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.AuthResponse;
import peaksoft.dto.request.AuthRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.entities.Restaurant;
import peaksoft.entities.User;
import peaksoft.enums.Role;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.restaurantRepository = restaurantRepository;
    }


    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.email(),
                        authRequest.password()
                )
        );

        User user = userRepository.findByEmail(authRequest.email())
                .orElseThrow(() -> new NoSuchElementException(String.format
                        ("User with email: %s doesn't exists", authRequest.email())));

        String token = jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

    }

    @Override
    public SimpleResponse saveUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());
        user.setPhoneNumber(userRequest.phoneNumber());
        user.setExpiration(userRequest.expiration());
        user.setRole(userRequest.role());
        user.setDateOfBirth(userRequest.dateOfBirth());
//        int age = LocalDate.now().getYear()-userRequest.dateOfBirth().getYear();
//
//        if (userRequest.role().equals(Role.CHEF)){
//            if (age <= 25 && age>=45){
//                userRepository.save(user);
//            }
//        } else if (userRequest.role().equals(Role.WAITER)) {
//            if (age <= 18 && age >= 45) {
//                userRepository.save(user);
//            }
//
//        }else throw new RuntimeException();
        userRepository.save(user);

        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("User with name: " + user.getFirstName() + " successfully saved")).build();
    }

    @Override
    public SimpleResponse saveUserWithRes(Long id, UserRequest userRequest) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(RuntimeException::new);
        User user = convert(userRequest);

//        int size = restaurant.getUsers().size();
//        if (size < 15) {
//            user.setRestaurant(restaurant);
//            restaurant.addUser(user);
//            restaurant.setNumberOfEmployees(size++);
//            userRepository.save(user);
//        } else if (size > 15) {
//            userRepository.deleteById(user.getId());
//
//        }
        user.setRestaurant(restaurant);
        restaurant.addUser(user);
        userRepository.save(user);

        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("User with fullName: %s " + "successfully saved",
                        user.getFirstName().concat(" ").concat(user.getLastName()))).build();
    }

    @Override
    public UserResponse getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public SimpleResponse updateUser(Long id, UserRequest userRequest) {

        if (!userRepository.existsById(id)) {
            return SimpleResponse.builder().status(HttpStatus.NOT_FOUND)
                    .message(String.format("User with id: " + id + " not found")).build();
        }

        User user = userRepository.findById(id).orElseThrow(RuntimeException::new);
        convert(userRequest);
        user.setLastName(userRequest.firstName());
        user.setFirstName(userRequest.lastName());
        user.setDateOfBirth(userRequest.dateOfBirth());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setPhoneNumber(userRequest.phoneNumber());
        user.setExpiration(userRequest.expiration());

        userRepository.save(user);

        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("User with id: " + user.getId() + " is updated")).build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            return SimpleResponse.builder().status(HttpStatus.NOT_FOUND)
                    .message(String.format("User with id: " + id + " is not found")).build();
        }
        userRepository.deleteById(id);
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message("User with id: " + id + " is deleted").build();
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.getAllUser();
    }

    @PostConstruct
    public void saveAdmin() {
        User user = new User();
        user.setFirstName("Aigerim");
        user.setLastName("Bektenova");
        user.setDateOfBirth(LocalDate.of(2005, 6, 5));
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setPhoneNumber("+996700875997");
        user.setRole(Role.ADMIN);
        user.setExpiration(2);
        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        }

    }

    private User convert(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setDateOfBirth(userRequest.dateOfBirth());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setPhoneNumber(userRequest.phoneNumber());
        user.setExpiration(userRequest.expiration());
        user.setRole(Role.CHEF);
        user.setDateOfBirth(userRequest.dateOfBirth());

        return user;
    }
}