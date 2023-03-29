package peaksoft.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;


@Builder
public record MenuRequest(

        Long restaurantId,
        Long subCategoryId,

        @Length(min = 2, max = 20, message = "Name's length should be between 2 and 20!")
        String name,

        @NotNull(message = "Image shouldn't be null!")
        String image,

        @NotNull(message = "price shouldn't be null!")
        @Positive(message = "price should be positive number!")
        int price,

        @NotNull(message = "description shouldn't be null!")
        String description,

        @NotNull(message = "isVegetarian shouldn't be null!")
        Boolean isVegetarian
) {
}
