package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.UserResponse;
import peaksoft.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String userName);

    boolean existsByEmail(String email);

    @Query("select new peaksoft.dto.response.UserResponse(u.firstName, u.lastName, u.dateOfBirth, u.email, u.password, u.phoneNumber, u.expiration, u.role) from User u where u.id=?1")
    UserResponse getUserById(Long id);

    @Query("select new peaksoft.dto.response.UserResponse(u.firstName, u.lastName, u.dateOfBirth, u.email, u.password, u.phoneNumber, u.expiration, u.role) from User u")
    List<UserResponse> getAllUser();


}
