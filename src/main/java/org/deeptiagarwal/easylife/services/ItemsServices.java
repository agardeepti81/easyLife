//This is a service class to add user and category to Items
package org.deeptiagarwal.easylife.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.deeptiagarwal.easylife.dao.ItemsRepoI;
import org.deeptiagarwal.easylife.dao.CategoriesRepoI;
import org.deeptiagarwal.easylife.dao.UsersRepoI;
import org.deeptiagarwal.easylife.models.Categories;
import org.deeptiagarwal.easylife.models.Items;
import org.deeptiagarwal.easylife.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional(rollbackFor = {Exception.class})
    public void editItem(int itemId, Items editedItem) throws Exception {
        if(itemsRepoI.findById(itemId) != null) {
            Items item = itemsRepoI.findById(itemId);
            item.setItemName(editedItem.getItemName());
            item.setQuantity(editedItem.getQuantity());
            item.setMeasuringUnit(editedItem.getMeasuringUnit());
            itemsRepoI.save(item);
        }else{
            throw new Exception();
        }
    }



}
