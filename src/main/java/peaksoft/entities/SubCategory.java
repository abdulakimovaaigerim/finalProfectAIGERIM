package peaksoft.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    @NotEmpty
    @NotNull
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    @OneToMany(mappedBy = "subCategory",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuItem> menuItems;

    public void addMenuItem(MenuItem menuItem) {
        this.menuItems.add(menuItem);
    }
}
