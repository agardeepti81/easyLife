//This class serves the purpose of storing user information along with their categories and Items.
// It also has helper methods to add categories and Items to the user

package org.deeptiagarwal.easylife.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@NoArgsConstructor
@Slf4j
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int uid;

    @NonNull
    @Size(min = 3, max = 25)
    String name;

    @NonNull
    @Pattern(regexp = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")
    @Email
    String email;

    @NonNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])([a-zA-Z0-9@$!%*?&]{8,})$")
    String password;


    public Users(@NonNull String name, @NonNull String email, @NonNull String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "users_categories",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "uid"),
            inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "cid"))
    @JsonManagedReference
    List<Categories> categories = new LinkedList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Items> items = new LinkedList<>();


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
