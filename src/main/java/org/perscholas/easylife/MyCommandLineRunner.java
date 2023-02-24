package org.perscholas.easylife;

import jakarta.annotation.PostConstruct;
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
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyCommandLineRunner implements CommandLineRunner {
    UsersRepoI users;
    CategoriesRepoI categories;
    ItemsRepoI items;

    @Autowired
    public MyCommandLineRunner(UsersRepoI users, CategoriesRepoI categories, ItemsRepoI items) {
        this.users = users;
        this.categories = categories;
        this.items = items;
    }

    @PostConstruct
    void created(){
        log.warn("MyCommandLineRunner created");
    }
    @Override
    public void run(String... args) throws Exception {
        Users user1 = new Users("John Doe","johndoe@gmail.com","passWORD@12");
        Users user2 = new Users("Natalie Bennet","nbennet@msn.com","passWORD@12");
        Users user3 = new Users("Ron Weasley","rweasley@yahoo.com","passWORD@12");

        users.saveAndFlush(user1);
        users.saveAndFlush(user2);
        users.saveAndFlush(user3);
//
//        Categories category1 = new Categories(1,"Grocery");
//        Categories category2 = new Categories(2,"Electrical");
//        Categories category3 = new Categories(3,"Lawn and Garden");
//        Categories category4 = new Categories(4,"Kitchen and Dining");
//
//        categories.saveAndFlush(category1);
//        categories.saveAndFlush(category2);
//        categories.saveAndFlush(category3);
//        categories.saveAndFlush(category4);
//
//        Items item1 = new Items(1,"Rice",20,"lbs");
//        Items item2 = new Items(2,"Sugar",5,"lbs");
//        Items item3 = new Items(3,"LED soft white Bulb 6ct",2,"pkt");
//        Items item4 = new Items(4,"HDMI cable 6 ft",1,"unit");
//        Items item5 = new Items(5,"Garden soil",10,"lbs");
//        Items item6 = new Items(6,"Fertilizer",20,"lbs");
//        Items item7 = new Items(7,"Serving bowls",6,"unit");
//        Items item8 = new Items(8,"Table Mats",4,"unit");
//        Items item9 = new Items(9,"Rice",15,"lbs");
//
//        items.save(item1);
//        items.save(item2);
//        items.save(item3);
//        items.save(item4);
//        items.save(item5);
//        items.save(item6);
//        items.save(item7);
//        items.save(item8);
//        items.save(item9);
//
//       //User1 item and category adding process
//       user1.addCategory(category1);
//       user1.addCategory(category2);
//       user1.addItems(item1);
//       user1.addItems(item2);
//       user1.addItems(item3);
//       users.saveAndFlush(user1);
//       category1.addItems(item1);
//       category1.addItems(item2);
//       category2.addItems(item3);
//       categories.save(category1);
//       categories.save(category2);
//
//       //User2 Item and Category adding process
//        user2.addCategory(category1);
//        user2.addCategory(category2);
//        user2.addCategory(category3);
//        user2.addItems(item9);
//        user2.addItems(item4);
//        user2.addItems(item5);
//        users.save(user2);
//        category1.addItems(item9);
//        category2.addItems(item4);
//        category3.addItems(item5);
//        categories.save(category1);
//        categories.save(category2);
//        categories.save(category3);
//
//        //User Item and Category adding process
//        user3.addCategory(category3);
//        user3.addCategory(category4);
//        user3.addItems(item6);
//        user3.addItems(item7);
//        user3.addItems(item8);
//        users.save(user3);
//        category3.addItems(item6);
//        category4.addItems(item7);
//        category4.addItems(item8);
//        categories.save(category3);
//        categories.save(category4);
   }
}
