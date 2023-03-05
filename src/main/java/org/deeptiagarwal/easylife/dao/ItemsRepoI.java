package org.deeptiagarwal.easylife.dao;

import org.deeptiagarwal.easylife.models.Categories;
import org.deeptiagarwal.easylife.models.Items;
import org.deeptiagarwal.easylife.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepoI extends JpaRepository<Items,Integer> {
   <Optional> Items findById(int id);

  <Optional> List<Items> findByUserAndCategory(Users u, Categories c);

}
