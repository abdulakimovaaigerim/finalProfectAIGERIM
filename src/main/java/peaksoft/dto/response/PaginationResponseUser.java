package peaksoft.dto.response;

import lombok.Getter;
import lombok.Setter;
import peaksoft.entities.User;

import java.util.List;

@Getter
@Setter
public class PaginationResponseUser {
    private List<User> users;
    private int currentPage;
    private int PageSize;
}
