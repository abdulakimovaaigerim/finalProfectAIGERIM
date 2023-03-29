package peaksoft.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String name;

    private String image;

    private int price;

    private String description;

    private Boolean isVegetarian;

    private Boolean InStock;

    @ManyToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    private Restaurant restaurant;

    @OneToOne(mappedBy = "menuItem", cascade = CascadeType.ALL)
    @JsonIgnore
    private StopList stopList;

    @ManyToMany(mappedBy = "menuItems", cascade = {PERSIST,REFRESH,DETACH,MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Cheque> cheques = new ArrayList<>();

    @ManyToOne(cascade = {PERSIST,REFRESH,DETACH,MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    private SubCategory subCategory;

}
