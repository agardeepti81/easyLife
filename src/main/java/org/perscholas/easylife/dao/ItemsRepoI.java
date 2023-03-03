package org.perscholas.easylife.dao;

import org.perscholas.easylife.models.Categories;
import org.perscholas.easylife.models.Items;
import org.perscholas.easylife.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemsRepoI extends JpaRepository<Items,Integer> {
    Items findById(int id);

    List<Items> findAllItemsByUserAndCategory(Users u,Categories c);

}
