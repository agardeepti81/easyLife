//This class serves the purpose of storing user information along with their categories and Items.
// It also has helper methods to add categories and Items to the user

package org.deeptiagarwal.easylife.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@NoArgsConstructor
@Slf4j
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name="users")
public class Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int uid;

    @NonNull
    @NotBlank
    @Size(min = 3, max = 25)
    String name;

    @NonNull
    @Email
    String email;

    @Setter(AccessLevel.NONE)
    @NonNull
    String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "users_categories",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "uid"),
            inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "cid"))
    @JsonManagedReference
    List<Categories> categories = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Items> items = new LinkedHashSet<>();



    public String setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
        return this.password;
    }


    public Users(@NonNull String name, @NonNull String email, @NonNull String password) {
        this.name = name;
        this.email = email;
        this.password = setPassword(password);
    }

    public Users(@NonNull String name, @NonNull String email, @NonNull String password, List<Categories> categories) {
        this.name = name;
        this.email = email;
        this.password = setPassword(password);
        this.categories = categories;
    }

    public Users(@NonNull String name, @NonNull String email, @NonNull String password, List<Categories> categories, Set<Items> items) {
        this.name = name;
        this.email = email;
        this.password = setPassword(password);
        this.categories = categories;
        this.items = items;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users users)) return false;
        return uid == users.uid && name.equals(users.name) && email.equals(users.email) && password.equals(users.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, name, email, password);
    }

    public void addCategory(Categories c)
    {
        categories.add(c);
        c.getUsers().add(this);
    }

    public void addItems(Items i)
    {
        items.add(i);
        i.addUser(this);
    }

    public void deleteItems(Items i)
    {
        items.remove(i);
        i.setUser(null);
    }
}
