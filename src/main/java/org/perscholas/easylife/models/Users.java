package org.perscholas.easylife.models;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@NoArgsConstructor
//@RequiredArgsConstructor
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
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    int uid;
    @NonNull
    String name;
    @NonNull
    String email;
    @NonNull
    String password;

    public Users(@NonNull String name, @NonNull String email, @NonNull String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "users_categories",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "uid"),
            inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "cid"))
    Set<Categories> categories = new LinkedHashSet<>();

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
}
