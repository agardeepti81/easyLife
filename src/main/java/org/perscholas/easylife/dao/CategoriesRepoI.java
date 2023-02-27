package org.perscholas.easylife.dao;

import org.perscholas.easylife.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepoI extends JpaRepository<Categories,Integer> {
    Categories findById(int id);
}
