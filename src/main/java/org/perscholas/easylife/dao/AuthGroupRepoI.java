package org.perscholas.easylife.dao;

import org.perscholas.easylife.models.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthGroupRepoI extends JpaRepository<AuthGroup,Integer> {
}
