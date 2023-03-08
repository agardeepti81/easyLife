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
import org.springframework.boot.web.servlet.server.Session;
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
            throw new Exception("Adding category and user to Item Failed");
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void editItem(int itemId, Items editedItem) throws Exception {
        if(itemsRepoI.findById(itemId) != null) {
            Items item = itemsRepoI.findById(itemId).get();
            item.setItemName(editedItem.getItemName());
            item.setQuantity(editedItem.getQuantity());
            item.setMeasuringUnit(editedItem.getMeasuringUnit());
            itemsRepoI.saveAndFlush(item);
            log.debug("Item Edited Successfully");
        }else{
            throw new Exception("Editing the item failed!!");
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteItem(int userId, int itemId) throws Exception {
        if(itemsRepoI.findById(itemId) != null) {
            Items item = itemsRepoI.findById(itemId).get();
            int cId = item.getCategory().getCid();

            Users user = usersRepoI.findById(userId).get();
            user.deleteItems(item);
            usersRepoI.save(user);

            Categories category = categoriesRepoI.findById(cId).get();
            category.deleteItems(item);
            categoriesRepoI.save(category);

            itemsRepoI.delete(item);
            log.warn("Item Successfully deleted!!");
        }else{
            throw new Exception("Deleting item failed!!");
        }
    }
}
