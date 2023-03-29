package peaksoft.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record ChequeRequest(
        Long userId,
        List<Long> menuItemsId,

        @NotNull(message = "date shouldn't be null!")
        LocalDate createAt
) {
}
