package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.AuthRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.request.UserTokenRequest;
import peaksoft.dto.response.*;
import peaksoft.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class USerApi {

    private final UserService userService;

    @Autowired
    public USerApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public UserTokenResponse login(@RequestBody UserTokenRequest userTokenRequest) {
        return userService.authenticate(userTokenRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse save(@RequestBody @Valid UserRequest request) {
        return userService.saveUserByAdmin(request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER','CHEF')")
    @PostMapping("/save")
    public SimpleResponse userSave(@RequestBody @Valid AuthRequest authRequest){
        return userService.user(authRequest);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public List<UserResponse> jobApplication(@RequestParam(required = false) Long id, @RequestParam String word) {
        return userService.jobApplication(id, word);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/getAll/{restId}")
    public List<UserResponse> getAll(@PathVariable Long restId) {
        return userService.getAllUsersById(restId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/get/{userId}")
    public UserResponse getById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{userId}")
    public SimpleResponse update(@PathVariable Long userId, @RequestBody @Valid UserRequest userRequest) {
        return userService.updateUser(userId, userRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
    @GetMapping("/paginationUser")
    public PaginationResponseUser getUserPagination(@RequestParam int page, @RequestParam int size) {
        return userService.getUserPagination(page, size);

    }
}