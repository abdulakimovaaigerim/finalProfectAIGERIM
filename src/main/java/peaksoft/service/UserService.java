package peaksoft.service;


import peaksoft.dto.request.AuthRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.request.UserTokenRequest;
import peaksoft.dto.request.UserUpdateRequest;
import peaksoft.dto.response.PaginationResponseUser;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.dto.response.UserTokenResponse;

import java.util.List;

public interface UserService {

    UserTokenResponse authenticate(UserTokenRequest userTokenRequest);
    SimpleResponse user(AuthRequest authRequest);

    List<UserResponse> jobApplication(Long id, String word);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsersById(Long restaurantId);

    SimpleResponse updateUser(Long id, UserUpdateRequest request);

    SimpleResponse deleteUserById(Long id);

    PaginationResponseUser getUserPagination(int page, int size);

}
