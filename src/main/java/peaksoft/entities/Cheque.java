package peaksoft.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "cheques")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cheque {
    @Id
    @GeneratedValue(generator = "cheque_id_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "cheque_id_gen", sequenceName = "cheque_id_seq", allocationSize = 1)
    private Long id;


    private Integer priceAverage;

    private LocalDate createAt;

    private double grandTotal;


    @ManyToMany(cascade = {DETACH, MERGE, PERSIST, REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<MenuItem> menuItems = new ArrayList<>();

    @ManyToOne(cascade = ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;

    public void addMenuItem(MenuItem menuItem) {
        this.menuItems.add(menuItem);
    }

}
