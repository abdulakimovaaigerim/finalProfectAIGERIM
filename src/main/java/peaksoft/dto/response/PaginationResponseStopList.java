package peaksoft.dto.response;

import lombok.Getter;
import lombok.Setter;
import peaksoft.entities.StopList;

import java.util.List;

@Getter
@Setter
public class PaginationResponseStopList {
    private List<StopList> stopLists;
    private int currentPage;
    private int PageSize;

}
