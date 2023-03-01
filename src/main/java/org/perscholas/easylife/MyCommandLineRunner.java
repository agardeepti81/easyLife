package org.perscholas.easylife;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.easylife.dao.AuthGroupRepoI;
import org.perscholas.easylife.dao.CategoriesRepoI;
import org.perscholas.easylife.dao.ItemsRepoI;
import org.perscholas.easylife.dao.UsersRepoI;
import org.perscholas.easylife.models.AuthGroup;
import org.perscholas.easylife.models.Categories;
import org.perscholas.easylife.models.Items;
import org.perscholas.easylife.models.Users;
import org.perscholas.easylife.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyCommandLineRunner implements CommandLineRunner {
    ItemsRepoI itemsRepoI;
    UsersRepoI usersRepoI;
    CategoriesRepoI categoriesRepoI;

    AuthGroupRepoI authGroupRepoI;

    UserServices userServices;
    @Autowired
    public MyCommandLineRunner(ItemsRepoI itemsRepoI, UsersRepoI usersRepoI, CategoriesRepoI categoriesRepoI, AuthGroupRepoI authGroupRepoI, UserServices userServices) {
        this.itemsRepoI = itemsRepoI;
        this.usersRepoI = usersRepoI;
        this.categoriesRepoI = categoriesRepoI;
        this.authGroupRepoI = authGroupRepoI;
        this.userServices = userServices;
    }

    @PostConstruct
    void created(){
        log.warn("MyCommandLineRunner created");
    }
    @Override
    public void run(String... args) throws Exception {

        Users user1 = new Users("Deepti Agarwal","deeptiag@gmail.com","passWORD@12");
        Users user2 = new Users("John Doe","johndoe@gmail.com","passWORD@12");
        Users user3 = new Users("Ron Weasley","rweasley@yahoo.com","passWORD@12");

        usersRepoI.save(user1);
        usersRepoI.save(user2);
        usersRepoI.save(user3);

        AuthGroup authUser1 = new AuthGroup("deeptiag@gmail.com","ROLE_ADMIN");
        AuthGroup authUser2 = new AuthGroup("deeptiag@gmail.com","ROLE_USER");
        AuthGroup authUser3 = new AuthGroup("johndoe@gmail.com","ROLE_USER");
        AuthGroup authUser4 = new AuthGroup("rweasley@yahoo.com","ROLE_USER");

        authGroupRepoI.save(authUser1);
        authGroupRepoI.save(authUser2);
        authGroupRepoI.save(authUser3);
        authGroupRepoI.save(authUser4);

        Categories category1 = new Categories("Grocery");
        Categories category2 = new Categories("Electrical");
        Categories category3 = new Categories("Lawn and Garden");
        Categories category4 = new Categories("Kitchen and Dining");

        categoriesRepoI.saveAndFlush(category1);
        categoriesRepoI.saveAndFlush(category2);
        categoriesRepoI.saveAndFlush(category3);
        categoriesRepoI.saveAndFlush(category4);

        List<Categories> categories = new ArrayList<>();
        categories.add(categoriesRepoI.findById(category1.getCid()));
        categories.add(categoriesRepoI.findById(category2.getCid()));
        categories.add(categoriesRepoI.findById(category3.getCid()));
        categories.add(categoriesRepoI.findById(category4.getCid()));

//        for (int i = 0; i < categories.size(); i++) {
//            user2.addCategory(categories.get(i));
//        }
//        usersRepoI.save(user2);
        userServices.addCategoriesToUser(user2);
        userServices.addCategoriesToUser(user3);
   }
}



