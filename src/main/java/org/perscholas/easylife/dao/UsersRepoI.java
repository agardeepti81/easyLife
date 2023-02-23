package org.perscholas.easylife.dao;

import org.perscholas.easylife.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepoI extends JpaRepository<Users,Integer> {

    Users findByEmailAndPassword(String email, String password);

    Users findByEmail(String email);


//    @Query("SELECT u FROM Users u WHERE u.email = :email and u.password = :password")
//    Users findUserByEmailAndPassword(
//            @Param("email") String email,
//            @Param("password") String password);
//    Optional<Users> findByEmailAndPassword(String email, String password);
}
