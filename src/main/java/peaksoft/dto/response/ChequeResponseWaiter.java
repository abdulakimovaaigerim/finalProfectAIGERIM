package peaksoft.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ChequeResponseWaiter(
        String waiterFullName,
        LocalDate date,
        int numberOfCheques,
        int price,
        double service,
        double grandTotal
) {
}
