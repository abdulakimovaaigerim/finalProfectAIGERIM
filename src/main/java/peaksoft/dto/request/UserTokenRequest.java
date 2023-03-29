package peaksoft.dto.request;

import lombok.Builder;

@Builder
public record UserTokenRequest(
        String email,
        String password
) {
}
