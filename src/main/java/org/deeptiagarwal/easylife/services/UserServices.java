//This is service class to add categories to user
package org.deeptiagarwal.easylife.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.deeptiagarwal.easylife.dao.AuthGroupRepoI;
import org.deeptiagarwal.easylife.dao.CategoriesRepoI;
import org.deeptiagarwal.easylife.dao.UsersRepoI;
import org.deeptiagarwal.easylife.models.AuthGroup;
import org.deeptiagarwal.easylife.models.Categories;
import org.deeptiagarwal.easylife.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServices {

    UsersRepoI usersRepoI;
    CategoriesRepoI categoriesRepoI;

    AuthGroupRepoI authGroupRepoI;

    @Autowired
    public UserServices(UsersRepoI usersRepoI, CategoriesRepoI categoriesRepoI, AuthGroupRepoI authGroupRepoI) {
        this.usersRepoI = usersRepoI;
        this.categoriesRepoI = categoriesRepoI;
        this.authGroupRepoI = authGroupRepoI;
    }

    //Add categories to user
    @Transactional(rollbackFor = {Exception.class})
    public void addCategoriesToUser(Users user) throws Exception {
        if(usersRepoI.findById(user.getUid()).isPresent()){
            user = usersRepoI.findById(user.getUid()).get();
            user.addCategory(categoriesRepoI.findById(1).get());
            user.addCategory(categoriesRepoI.findById(2).get());
            user.addCategory(categoriesRepoI.findById(3).get());
            user.addCategory(categoriesRepoI.findById(4).get());
            usersRepoI.save(user);
            log.warn("Initial categories added to user "+user.getName());
        }else{
            throw new Exception("Initial Categories cannot be added to "+user.getName());
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void addCategoryToUser(int userId, int cId) throws Exception {
        if(usersRepoI.findById(userId).isPresent()){
            Users user = usersRepoI.findById(userId).get();
            user.addCategory(categoriesRepoI.findById(cId).get());
            usersRepoI.save(user);
            log.warn(categoriesRepoI.findById(cId).get().getCategoryName()+" Category added to user "+user.getName());
        }else{
            throw new Exception(categoriesRepoI.findById(cId).get().getCategoryName()+" category cannot be added!!");
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void addRolesToUser(Users user) throws Exception {
        if(usersRepoI.findById(user.getUid()).isPresent()){
            user = usersRepoI.findById(user.getUid()).get();
            AuthGroup authUser = new AuthGroup(user.getEmail(),"ROLE_USER");
            authGroupRepoI.save(authUser);
            log.debug("Initial Role added to user "+ authUser.getEmail()+authUser.getRole());
        }else{
            throw new Exception("Role_USER can't be added to "+user.getName());
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public List<Categories> getCategories(Users user) throws Exception {
        if(usersRepoI.findById(user.getUid()).isPresent()){
            List<Categories> allCategories = categoriesRepoI.findAll();
            List<Categories> userCategories = usersRepoI.findById(user.getUid()).get().getCategories();
            allCategories.removeAll(userCategories);
            return allCategories;
        }else{
            throw new Exception("Role_USER can't be added to "+user.getName());
        }
    }

}
