package org.perscholas.easylife.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.easylife.dao.CategoriesRepoI;
import org.perscholas.easylife.dao.ItemsRepoI;
import org.perscholas.easylife.dao.UsersRepoI;
import org.perscholas.easylife.models.Categories;
import org.perscholas.easylife.models.Items;
import org.perscholas.easylife.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemsServices {

    UsersRepoI usersRepoI;
    CategoriesRepoI categoriesRepoI;
    ItemsRepoI itemsRepoI;

    @Autowired
    public ItemsServices(UsersRepoI usersRepoI, CategoriesRepoI categoriesRepoI, ItemsRepoI itemsRepoI) {
        this.usersRepoI = usersRepoI;
        this.categoriesRepoI = categoriesRepoI;
        this.itemsRepoI = itemsRepoI;
    }

    //Add categories to user
    @Transactional(rollbackFor = {Exception.class})
    public void addUserAndCategorytoItem(Users user, Categories category, Items item) throws Exception {
        if(usersRepoI.findById(user.getUid()).isPresent()) {
            user.addItems(item);
            usersRepoI.save(user);

            category.addItems(item);
            categoriesRepoI.save(category);
            log.warn("Category and User added to item.");
        }else{
            throw new Exception();
        }
    }
}
