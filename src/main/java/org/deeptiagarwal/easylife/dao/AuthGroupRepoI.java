//AuthGroup Repository for creating AuthGroup beans when needed and storing the data in the database.

package org.deeptiagarwal.easylife.dao;

import org.deeptiagarwal.easylife.models.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthGroupRepoI extends JpaRepository<AuthGroup,Integer> {
}