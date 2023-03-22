package peaksoft.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(generator = "category_id_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "category_id_gen", sequenceName = "category_id_seq", allocationSize = 1)
    private Long id;

    @NotEmpty
    @NotNull
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<SubCategory> subCategories = new ArrayList<>();

    public void addSubCa(SubCategory subCategory) {
        this.subCategories.add(subCategory);

    }
}
