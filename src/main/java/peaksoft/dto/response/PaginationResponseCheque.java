package peaksoft.dto.response;

import lombok.Getter;
import lombok.Setter;
import peaksoft.entities.Cheque;

import java.util.List;

@Getter
@Setter
public class PaginationResponseCheque {
    private List<Cheque> cheques;
    private int currentPage;
    private int PageSize;

}
