package peaksoft.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
@Builder
public class Cheque {
    @Id
    @GeneratedValue(generator = "cheque_id_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "cheque_id_gen", sequenceName = "cheque_id_seq", allocationSize = 1)
    private Long id;

    @NotEmpty
    @NotNull
    private Integer priceAverage;

    @NotEmpty
    @NotNull
    private LocalDate createAt;

    @ManyToMany(cascade = {DETACH, MERGE, PERSIST, REFRESH}, fetch = FetchType.EAGER)
    private List<MenuItem> menuItems = new ArrayList<>();

    @ManyToOne(cascade = ALL, fetch = FetchType.EAGER)
    private User user;

}
