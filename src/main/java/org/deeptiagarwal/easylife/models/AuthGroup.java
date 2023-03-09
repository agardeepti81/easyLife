//This class serves the purpose of storing user roles for Spring security.
// Based on their reoles users can access different part of the application.

package org.deeptiagarwal.easylife.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name="auth_group")
public class AuthGroup {
    @Id @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NonNull
    @Email
    String email;

    @NonNull
    String role;

    public AuthGroup(@NonNull String email, @NonNull String role) {
        this.email = email;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthGroup authGroup)) return false;
        return id == authGroup.id && email.equals(authGroup.email) && role.equals(authGroup.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, role);
    }

}
