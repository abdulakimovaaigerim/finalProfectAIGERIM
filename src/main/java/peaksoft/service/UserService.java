package peaksoft.service;

import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.AuthResponse;
import peaksoft.dto.request.AuthRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    AuthResponse authenticate(AuthRequest authRequest);

    SimpleResponse saveUser(UserRequest userRequest);

    SimpleResponse saveUserWithRes(Long id, UserRequest userRequest);

    UserResponse getUserById(Long id);

    SimpleResponse updateUser(Long id, UserRequest userRequest);

    SimpleResponse deleteById(Long id);

    List<UserResponse> getAllUsers();


}
