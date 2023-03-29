package peaksoft.dto.response;



import lombok.Getter;
import lombok.Setter;
import peaksoft.entities.Category;

import java.util.List;

@Getter
@Setter
public class PaginationResponse {
    private List<Category> categoryResponseList;
    private int currentPage;
    private int PageSize;

}
