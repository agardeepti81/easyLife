//Item Repository for creating Item beans when needed and storing the data in the database.
//Custom queries to find Items by user and category, and find item by id

package org.deeptiagarwal.easylife.dao;

import org.deeptiagarwal.easylife.models.Categories;
import org.deeptiagarwal.easylife.models.Items;
import org.deeptiagarwal.easylife.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepoI extends JpaRepository<Items,Integer> {

   List<Items> findByUserAndCategory(Users u, Categories c);

   List<Items> findByUser(Users u);

}
