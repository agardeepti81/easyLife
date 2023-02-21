package org.perscholas.easylife.dao;

import org.perscholas.easylife.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepoI extends JpaRepository<Categories,Integer> {
}
