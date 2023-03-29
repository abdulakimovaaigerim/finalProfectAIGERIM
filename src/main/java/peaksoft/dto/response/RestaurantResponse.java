package peaksoft.dto.response;

import lombok.Builder;

@Builder
public record RestaurantResponse(
        Long id,
        String name,
        String location,
        String restType,
        int numberOfEmployees
) {
}
