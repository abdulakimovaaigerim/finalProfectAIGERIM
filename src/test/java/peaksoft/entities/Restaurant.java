package peaksoft.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(generator = "restaurant_id_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "restaurant_id_gen", sequenceName = "restaurant_id_seq", allocationSize = 1)
    private Long id;

    @NotEmpty
    @NotNull
    private String name;

    @NotEmpty
    @NotNull
    private String location;

    @NotEmpty
    @NotNull
    private String restType;

    @NotEmpty
    @NotNull
    private int numberOfEmployees;

    @NotEmpty
    @NotNull
    private double service;

    @OneToMany(mappedBy = "restaurant", cascade = ALL, fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = ALL)
    private List<MenuItem> menuItems = new ArrayList<>();

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addMenuItem(MenuItem menuItem) {
        this.menuItems.add(menuItem);
    }
}
