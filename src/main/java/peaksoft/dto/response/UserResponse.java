package peaksoft.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserResponse(
        Long id,
        String fullName,
        LocalDate dateOfBirth,
        String email,
        String phoneNumber,
        int experience
) {
}
