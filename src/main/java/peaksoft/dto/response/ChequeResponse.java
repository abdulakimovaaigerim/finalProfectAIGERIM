package peaksoft.dto.response;

import lombok.Data;
import peaksoft.entities.MenuItem;

import java.util.List;

@Data
public class ChequeResponse {
    private Long id;
    private String fullName;
    private List<MenuItem> menuItems;
    private double averagePrice;
    private double service;
    private double grandTotal;
}
