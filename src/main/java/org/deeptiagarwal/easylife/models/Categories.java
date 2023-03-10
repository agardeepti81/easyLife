//This class serves the purpose of storing Categories information.
// It also has helper methods to add items to categories

package org.deeptiagarwal.easylife.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Entity
@Table(name="categories")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int cid;

    @NonNull
    @Size(min = 4, max=30)
    String categoryName;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    Set<Users> users = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Items> items = new LinkedList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categories that)) return false;
        return cid == that.cid && categoryName.equals(that.categoryName) && Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cid, categoryName, items);
    }


    public void addItems(Items i)
    {
        items.add(i);
        i.addCategory(this);
    }

    public void deleteItems(Items i)
    {
        items.remove(i);
        i.setCategory(null);
    }
}
