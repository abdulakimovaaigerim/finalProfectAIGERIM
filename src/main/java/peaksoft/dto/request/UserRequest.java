package peaksoft.dto.request;

import lombok.Builder;
import peaksoft.enums.Role;

import java.time.LocalDate;

@Builder
public record UserRequest(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String email,
        String password,
        String phoneNumber,
        int expiration,
        Role role) {
}
