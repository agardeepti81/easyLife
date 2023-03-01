package org.perscholas.easylife.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Table(name="auth_group")
public class AuthGroup {
    @Id @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NonNull
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
