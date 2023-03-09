//User Repository for creating User beans when needed and storing the data in the database.
//Custom queries to find user by email and password, and find user by email

package org.deeptiagarwal.easylife.dao;

import org.deeptiagarwal.easylife.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepoI extends JpaRepository<Users,Integer> {

    Users findByEmailAndPassword(String email, String password);

    Users findByEmail(String email);

    Optional<Users> findByEmailAllIgnoreCase(String username);
}
