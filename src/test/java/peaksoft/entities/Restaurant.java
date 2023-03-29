package peaksoft.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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

    @Length(min = 2, max = 20, message = "Name's length should be between 2 and 20!")
    private String name;

    @NotNull(message = "Location shouldn't be null!")
    private String location;

    @NotNull(message = "RestType shouldn't be null!")
    private String restType;

    @NotEmpty
    @NotNull
    private int numberOfEmployees;

    @Positive(message = "service should be positive number!")
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
