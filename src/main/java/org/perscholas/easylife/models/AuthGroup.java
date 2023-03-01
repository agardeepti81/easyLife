package org.perscholas.easylife.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name="auth_group")
public class AuthGroup {
    @Id
    int id;

    @NonNull
    String email;

    @NonNull
    String role;

}
