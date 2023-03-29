package peaksoft.dto.response;

import lombok.Getter;
import lombok.Setter;
import peaksoft.entities.Restaurant;

import java.util.List;

@Getter
@Setter
public class PaginationResponseRest {
    private List<Restaurant> restaurants;
    private int currentPage;
    private int PageSize;
}
