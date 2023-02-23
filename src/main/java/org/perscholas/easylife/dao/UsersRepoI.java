package org.perscholas.easylife.dao;

import org.perscholas.easylife.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.Optional;

@Repository
public interface UsersRepoI extends JpaRepository<Users,Integer> {
    @Query("SELECT u FROM Users u WHERE u.email = :email and u.password = :password")
    Users findUserByEmailAndPassword(
            @Param("email") String email,
            @Param("password") String password);
//    Optional<Users> findbyEmailandPassword(String email, String password);
}
