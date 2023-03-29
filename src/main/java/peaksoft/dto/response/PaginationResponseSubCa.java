package peaksoft.dto.response;

import lombok.Getter;
import lombok.Setter;
import peaksoft.entities.SubCategory;

import java.util.List;

@Getter
@Setter
public class PaginationResponseSubCa {
    private List<SubCategory> subCategories;
    private int currentPage;
    private int PageSize;

}
