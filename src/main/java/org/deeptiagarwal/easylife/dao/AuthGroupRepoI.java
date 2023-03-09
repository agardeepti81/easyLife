//AuthGroup Repository for creating AuthGroup beans when needed and storing the data in the database.

package org.deeptiagarwal.easylife.dao;

import org.deeptiagarwal.easylife.models.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthGroupRepoI extends JpaRepository<AuthGroup,Integer> {
    List<AuthGroup> findByEmail(String username);
}
