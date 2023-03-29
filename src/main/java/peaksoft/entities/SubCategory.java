package peaksoft.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "subCategories")
@Getter
@Setter
@NoArgsConstructor
public class SubCategory {
    @Id
    @GeneratedValue(generator = "subCategory_id_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "subCategory_id_gen", sequenceName = "subCategory_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Category category;

    @OneToMany(mappedBy = "subCategory",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuItem> menuItems;

}
