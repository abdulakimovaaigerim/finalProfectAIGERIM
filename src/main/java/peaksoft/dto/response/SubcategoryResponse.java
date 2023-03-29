package peaksoft.dto.response;

import lombok.Builder;

@Builder
public record SubcategoryResponse(
        String categoryName,
        Long id,
        String name
) {
}
