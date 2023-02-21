package org.perscholas.easylife.dao;

import org.perscholas.easylife.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepoI extends JpaRepository<Users,Integer> {
}
