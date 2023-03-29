package peaksoft.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StopListResponse(
        String menuItemName,
        Long id,
        String reason,
        LocalDate date
) {
}
