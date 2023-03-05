package org.deeptiagarwal.easylife.dao;

import org.deeptiagarwal.easylife.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepoI extends JpaRepository<Categories,Integer> {
    Categories findById(int id);
}
