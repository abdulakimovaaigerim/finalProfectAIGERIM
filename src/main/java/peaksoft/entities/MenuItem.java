package peaksoft.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "menuItems")
@Getter
@Setter
@NoArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(generator = "menuIte_id_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "menuItem_id_gen", sequenceName = "menuItem_id_seq", allocationSize = 1)
    private Long id;

    @NotEmpty
    @NotNull
    private String name;

    @NotEmpty
    @NotNull
    private String image;

    @NotEmpty
    @NotNull
    private Integer price;

    @NotEmpty
    @NotNull
    private String description;

    @NotEmpty
    @NotNull
    private Boolean isVegetarian;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Restaurant restaurant;

    @OneToOne(mappedBy = "menuItem", cascade = CascadeType.ALL)
    private StopList stopList;

    @ManyToMany(mappedBy = "menuItems", cascade = {DETACH, PERSIST, REFRESH, MERGE}, fetch = FetchType.LAZY)
    private List<Cheque> cheques = new ArrayList<>();

    @ManyToOne(cascade = {DETACH, PERSIST, REFRESH, MERGE}, fetch = FetchType.LAZY)
    private SubCategory subCategory;


    public void addCheque(Cheque cheque) {
        this.cheques.add(cheque);
    }
}
