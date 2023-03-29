package peaksoft.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RestaurantRequest(
        @Size(min = 2, max = 20, message = "Name's length should be between 2 and 20!")
        String name,

        @NotNull(message = "Location shouldn't be null!")
        String location,

        @NotNull(message = "RestType shouldn't be null!")
        String restType,

        @Positive(message = "service should be positive number!")
        double service
) {
}
