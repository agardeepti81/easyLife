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

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyCommandLineRunner implements CommandLineRunner {
    ItemsRepoI itemsRepoI;
    UsersRepoI usersRepoI;
    CategoriesRepoI categoriesRepoI;
    UserServices userServices;
    AuthGroupRepoI authGroupRepoI;

    @Autowired
    public MyCommandLineRunner(ItemsRepoI items, UsersRepoI users, CategoriesRepoI categories, UserServices userServices, AuthGroupRepoI authGroupRepoI) {
        this.itemsRepoI = items;
        this.usersRepoI = users;
        this.categoriesRepoI = categories;
        this.userServices = userServices;
        this.authGroupRepoI = authGroupRepoI;
    }

    @PostConstruct
    void created(){
        log.warn("MyCommandLineRunner created");
    }
    @Override
    public void run(String... args) throws Exception {

        Users user1 = new Users("Deepti Agarwal","deeptiag@gmail.com","passWORD@12");
        Users user2 = new Users("John Doe","johndoe@gmail.com","passWORD@12");
        Users user3 = new Users("Natalie Bennet","nbennet@msn.com","passWORD@12");
        Users user4 = new Users("Ron Weasley","rweasley@yahoo.com","passWORD@12");

        usersRepoI.save(user1);
        usersRepoI.save(user2);
        usersRepoI.save(user3);
        usersRepoI.save(user4);

        AuthGroup authUser1 = new AuthGroup("deeptiag@gmail.com","ROLE_ADMIN");
        AuthGroup authUser2 = new AuthGroup("deeptiag@gmail.com","ROLE_USER");
        AuthGroup authUser3 = new AuthGroup("johndoe@gmail.com","ROLE_USER");
        AuthGroup authUser4 = new AuthGroup("nbennet@msn.com","ROLE_USER");
        AuthGroup authUser5 = new AuthGroup("nbennet@msn.com","ROLE_USER");

        authGroupRepoI.save(authUser1);
        authGroupRepoI.save(authUser2);
        authGroupRepoI.save(authUser3);
        authGroupRepoI.save(authUser4);
        authGroupRepoI.save(authUser5);

        Categories category1 = new Categories("Grocery");
        Categories category2 = new Categories("Electrical");
        Categories category3 = new Categories("Lawn and Garden");
        Categories category4 = new Categories("Kitchen and Dining");

        categoriesRepoI.save(category1);
        categoriesRepoI.save(category2);
        categoriesRepoI.save(category3);
        categoriesRepoI.save(category4);

        Items item1 = new Items("Rice",20,"lbs");
        Items item2 = new Items("Sugar",5,"lbs");
        Items item3 = new Items("LED soft white Bulb 6ct",2,"pkt");
        Items item4 = new Items("HDMI cable 6 ft",1,"unit");
        Items item5 = new Items("Garden soil",10,"lbs");
        Items item6 = new Items("Fertilizer",20,"lbs");
        Items item7 = new Items("Serving bowls",6,"unit");
        Items item8 = new Items("Table Mats",4,"unit");
        Items item9 = new Items("Rice",15,"lbs");

        itemsRepoI.save(item1);
        itemsRepoI.save(item2);
        itemsRepoI.save(item3);
        itemsRepoI.save(item4);
        itemsRepoI.save(item5);
        itemsRepoI.save(item6);
        itemsRepoI.save(item7);
        itemsRepoI.save(item8);
        itemsRepoI.save(item9);

        userServices.addCategoriesToUser(user2);
        userServices.addCategoriesToUser(user3);
        userServices.addCategoriesToUser(user4);
   }
}



