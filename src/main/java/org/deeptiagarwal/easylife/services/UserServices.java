//This is service class to add categories to user
package org.deeptiagarwal.easylife.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.deeptiagarwal.easylife.dao.CategoriesRepoI;
import org.deeptiagarwal.easylife.dao.UsersRepoI;
import org.deeptiagarwal.easylife.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServices {

    UsersRepoI usersRepoI;
    CategoriesRepoI categoriesRepoI;

    @Autowired
    public UserServices(UsersRepoI usersRepoI, CategoriesRepoI categoriesRepoI) {
        this.usersRepoI = usersRepoI;
        this.categoriesRepoI = categoriesRepoI;
    }

    //Add categories to user
    @Transactional(rollbackFor = {Exception.class})
    public void addCategoriesToUser(Users user) throws Exception {
        if(usersRepoI.findById(user.getUid()).isPresent()){
            user = usersRepoI.findById(user.getUid()).get();
            user.addCategory(categoriesRepoI.findById(1));
            user.addCategory(categoriesRepoI.findById(2));
            user.addCategory(categoriesRepoI.findById(3));
            user.addCategory(categoriesRepoI.findById(4));
            usersRepoI.save(user);
            log.warn("Initial categories added to user "+user.getName());
        }else{
            throw new Exception();
        }
    }

}
