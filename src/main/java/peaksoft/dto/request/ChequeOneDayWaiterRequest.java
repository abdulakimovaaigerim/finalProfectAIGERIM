package peaksoft.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ChequeOneDayWaiterRequest(

        @NotBlank(message = "Name cannot be empty!")
        String restName,
        Long waiterId,
        LocalDate date
) {
}
