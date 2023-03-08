//Class to add dummy data in the database
package org.deeptiagarwal.easylife;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.deeptiagarwal.easylife.dao.AuthGroupRepoI;
import org.deeptiagarwal.easylife.services.UserServices;
import org.deeptiagarwal.easylife.dao.CategoriesRepoI;
import org.deeptiagarwal.easylife.dao.ItemsRepoI;
import org.deeptiagarwal.easylife.dao.UsersRepoI;
import org.deeptiagarwal.easylife.models.AuthGroup;
import org.deeptiagarwal.easylife.models.Categories;
import org.deeptiagarwal.easylife.models.Items;
import org.deeptiagarwal.easylife.models.Users;
import org.deeptiagarwal.easylife.services.ItemsServices;
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
    UserServices userServices;
    AuthGroupRepoI authGroupRepoI;

    ItemsServices itemsServices;

    public MyCommandLineRunner(ItemsRepoI itemsRepoI, UsersRepoI usersRepoI, CategoriesRepoI categoriesRepoI, UserServices userServices, AuthGroupRepoI authGroupRepoI, ItemsServices itemsServices) {
        this.itemsRepoI = itemsRepoI;
        this.usersRepoI = usersRepoI;
        this.categoriesRepoI = categoriesRepoI;
        this.userServices = userServices;
        this.authGroupRepoI = authGroupRepoI;
        this.itemsServices = itemsServices;
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
        AuthGroup authUser5 = new AuthGroup("rweasley@yahoo.com","ROLE_USER");

        authGroupRepoI.save(authUser1);
        authGroupRepoI.save(authUser2);
        authGroupRepoI.save(authUser3);
        authGroupRepoI.save(authUser4);
        authGroupRepoI.save(authUser5);

        Categories category1 = new Categories("Grocery");
        Categories category2 = new Categories("Cleaning Supplies");
        Categories category3 = new Categories("Beauty");
        Categories category4 = new Categories("Patio and Garden");

        categoriesRepoI.save(category1);
        categoriesRepoI.save(category2);
        categoriesRepoI.save(category3);
        categoriesRepoI.save(category4);

        Items item1 = new Items("Ritz Crackers",2,"pkt");
        Items item2 = new Items("Organic Milk 2%",2,"cans");
        Items item3 = new Items("Maple Syrup 28 oz",2,"bottle");
        Items item4 = new Items("Nutella 26.5 oz",2,"jar");
        Items item5 = new Items("Brown Rice 10 lb",1,"pkt");
        Items item6 = new Items("Sugar 5 lb",3,"pkt");
        Items item7 = new Items("All Purpose Flour 10 lb",2,"pkt");
        Items item8 = new Items("Baking Soda",2,"pkt");
        Items item9 = new Items("Sugar 5 lb",3,"pkt");

        Items item10 = new Items("Mr. Clean Magic Eraser",3,"pkt");
        Items item11 = new Items("Lysol all purpose cleaner",3,"bottle");
        Items item12 = new Items("Clorox wipes",3,"pkt");
        Items item13 = new Items("Lysol all purpose cleaner",2,"bottle");
        Items item14 = new Items("Lysol wipes",5,"pkt");
        Items item15 = new Items("Dawn Dishwahing Liquid",2,"bottle");
        Items item16 = new Items("Clorox wipes",2,"pkt");
        Items item17 = new Items("Dawn Dishwahing Liquid",2,"bottle");
        Items item18 = new Items("Mr. Clean Magic Eraser",2,"pkt");

        Items item19 = new Items("Shampoo",1,"bottle");
        Items item20 = new Items("Conditioner",1,"bottle");
        Items item21 = new Items("Cerave",1,"jar");
        Items item22 = new Items("Face Wash",2,"qty");
        Items item23 = new Items("Cetaphil",1,"jar");
        Items item24 = new Items("Shampoo",2,"pkt");
        Items item25 = new Items("Conditioner",2,"bottle");
        Items item26 = new Items("Face Pack",2,"count");
        Items item27 = new Items("Shaving Foam",2,"qty");

        Items item28 = new Items("Miracle grow plant food",2,"pkt");
        Items item29 = new Items("Oldcastle Mulch 2cu feet red",1,"pkt");
        Items item30 = new Items("Planter",2,"count");
        Items item31 = new Items("Tree and shrub plant food",2,"pkt");
        Items item32 = new Items("Oldcastle Mulch 2cu feet red",1,"jar");
        Items item33 = new Items("Grass Seed Mix",2,"pkt");
        Items item34 = new Items("Plant Fertilizer",3,"count");
        Items item35 = new Items("Oldcastle Mulch 2cu feet red",2,"count");
        Items item36 = new Items("Grass Seed Mix",3,"qty");

        itemsRepoI.save(item1);
        itemsRepoI.save(item2);
        itemsRepoI.save(item3);
        itemsRepoI.save(item4);
        itemsRepoI.save(item5);
        itemsRepoI.save(item6);
        itemsRepoI.save(item7);
        itemsRepoI.save(item8);
        itemsRepoI.save(item9);
        itemsRepoI.save(item10);
        itemsRepoI.save(item11);
        itemsRepoI.save(item12);
        itemsRepoI.save(item13);
        itemsRepoI.save(item14);
        itemsRepoI.save(item15);
        itemsRepoI.save(item16);
        itemsRepoI.save(item17);
        itemsRepoI.save(item18);
        itemsRepoI.save(item19);
        itemsRepoI.save(item20);
        itemsRepoI.save(item21);
        itemsRepoI.save(item22);
        itemsRepoI.save(item23);
        itemsRepoI.save(item24);
        itemsRepoI.save(item25);
        itemsRepoI.save(item26);
        itemsRepoI.save(item27);
        itemsRepoI.save(item28);
        itemsRepoI.save(item29);
        itemsRepoI.save(item30);
        itemsRepoI.save(item31);
        itemsRepoI.save(item32);
        itemsRepoI.save(item33);
        itemsRepoI.save(item34);
        itemsRepoI.save(item35);
        itemsRepoI.save(item36);

        userServices.addCategoriesToUser(user2);
        userServices.addCategoriesToUser(user3);
        userServices.addCategoriesToUser(user4);

        itemsServices.addUserAndCategorytoItem(user2,category1,item1);
        itemsServices.addUserAndCategorytoItem(user2,category1,item2);
        itemsServices.addUserAndCategorytoItem(user2,category1,item3);
        itemsServices.addUserAndCategorytoItem(user2,category2,item10);
        itemsServices.addUserAndCategorytoItem(user2,category2,item11);
        itemsServices.addUserAndCategorytoItem(user2,category2,item12);
        itemsServices.addUserAndCategorytoItem(user2,category3,item19);
        itemsServices.addUserAndCategorytoItem(user2,category3,item20);
        itemsServices.addUserAndCategorytoItem(user2,category3,item21);
        itemsServices.addUserAndCategorytoItem(user2,category4,item28);
        itemsServices.addUserAndCategorytoItem(user2,category4,item29);
        itemsServices.addUserAndCategorytoItem(user2,category4,item30);

        itemsServices.addUserAndCategorytoItem(user3,category1,item4);
        itemsServices.addUserAndCategorytoItem(user3,category1,item5);
        itemsServices.addUserAndCategorytoItem(user3,category1,item6);
        itemsServices.addUserAndCategorytoItem(user3,category2,item13);
        itemsServices.addUserAndCategorytoItem(user3,category2,item14);
        itemsServices.addUserAndCategorytoItem(user3,category2,item15);
        itemsServices.addUserAndCategorytoItem(user3,category3,item22);
        itemsServices.addUserAndCategorytoItem(user3,category3,item23);
        itemsServices.addUserAndCategorytoItem(user3,category3,item24);
        itemsServices.addUserAndCategorytoItem(user3,category4,item31);
        itemsServices.addUserAndCategorytoItem(user3,category4,item32);
        itemsServices.addUserAndCategorytoItem(user3,category4,item33);

        itemsServices.addUserAndCategorytoItem(user4,category1,item7);
        itemsServices.addUserAndCategorytoItem(user4,category1,item8);
        itemsServices.addUserAndCategorytoItem(user4,category1,item9);
        itemsServices.addUserAndCategorytoItem(user4,category2,item16);
        itemsServices.addUserAndCategorytoItem(user4,category2,item17);
        itemsServices.addUserAndCategorytoItem(user4,category2,item18);
        itemsServices.addUserAndCategorytoItem(user4,category3,item25);
        itemsServices.addUserAndCategorytoItem(user4,category3,item26);
        itemsServices.addUserAndCategorytoItem(user4,category3,item27);
        itemsServices.addUserAndCategorytoItem(user4,category4,item34);
        itemsServices.addUserAndCategorytoItem(user4,category4,item35);
        itemsServices.addUserAndCategorytoItem(user4,category4,item36);
   }
}



