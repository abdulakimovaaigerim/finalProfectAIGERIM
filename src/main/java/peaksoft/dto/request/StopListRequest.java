package peaksoft.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StopListRequest(
        Long menuItemId,

        @NotNull(message = "reason shouldn't be null!")
        String reason,

        @NotNull(message = "date shouldn't be null!")
        LocalDate date
) {
}
