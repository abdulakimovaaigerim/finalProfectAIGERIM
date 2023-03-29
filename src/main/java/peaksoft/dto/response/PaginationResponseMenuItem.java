package peaksoft.dto.response;

import lombok.Getter;
import lombok.Setter;
import peaksoft.entities.MenuItem;

import java.util.List;

@Getter
@Setter
public class PaginationResponseMenuItem {
    private List<MenuItem> menuItems;
    private int currentPage;
    private int PageSize;
}
