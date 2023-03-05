//This class serves the purpose of storing Items information.
// It has methods to add a item to a user and a category because an item belong to a user in a certain category

package org.deeptiagarwal.easylife.models;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="items")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NonNull
    @Size(min = 3, max=100)
    String itemName;
    @NonNull
    @Min(0)
    @Max(100)
    int quantity;
    @NonNull
    String measuringUnit;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "category_id")
    Categories category;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "users_id")
    Users user;

    public void addUser(Users u){
        this.user = u;
    }

    public void addCategory(Categories c){
        this.category = c;
    }

}
