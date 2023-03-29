package peaksoft.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;
import peaksoft.enums.Role;

import java.time.LocalDate;

@Builder
public record UserRequest(

        Long restaurantId,
        @NotNull(message = "firstName shouldn't ne null!")
        @Size(min = 2, max = 20, message = "Name's length should be between 2 and 20!")
        String firstName,
        @NotNull(message = "LastName shouldn't ne null!")
        @Size(min = 2, max = 20, message = "Name's length should be between 2 and 20!")
        String lastName,
        @NotNull(message = "date shouldn't be null!")
        LocalDate dateOfBirth,
        @NotNull(message = "Email shouldn't be null")
        @Email(message = "Email should be valid")
        String email,
        @NotNull(message = "password shouldn't be null")
        @Length(min = 4, max = 15, message = "password size should be between 4 and 15")
        String password,

        @NotNull(message = "phone number shouldn't be null!")
        @Length(min = 13, max = 13, message = "phone number length should be 13")
        String phoneNumber,

        @NotNull(message = "experience shouldn't be null!")
        @Positive(message = "experience should be positive number!")
        int experience,
        @Enumerated(EnumType.STRING)
        Role role) {
}
