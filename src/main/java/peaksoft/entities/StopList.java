package peaksoft.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;


@Entity
@Table(name = "stopLists")
@Getter
@Setter
@NoArgsConstructor
public class StopList {
    @Id
    @GeneratedValue(generator = "stopList_id_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "stopList_id_gen", sequenceName = "stopList_id_seq", allocationSize = 1)
    private Long id;

    private String reason;

    private LocalDate date;

    @OneToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH}, fetch = FetchType.EAGER)
    private MenuItem menuItem;

}
