package org.perscholas.easylife.dao;

import org.perscholas.easylife.models.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepoI extends JpaRepository<Items,Integer> {
}
