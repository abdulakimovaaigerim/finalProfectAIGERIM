package peaksoft.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CategoryRequest(
        @Size(min = 2, max = 20, message = "Name's length should be between 2 and 20!")
        String name
) {
}
