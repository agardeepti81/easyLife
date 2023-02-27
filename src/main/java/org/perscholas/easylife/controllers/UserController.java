package org.perscholas.easylife.controllers;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpClient;
import java.util.List;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserController {
    CategoriesRepoI categoriesRepoI;
    UsersRepoI usersRepoI;

    ItemsRepoI itemsRepoI;
    @Autowired
    public UserController(UsersRepoI usersRepoI,
                          CategoriesRepoI categoriesRepoI,ItemsRepoI itemsRepoI) {
        this.usersRepoI = usersRepoI;
        this.categoriesRepoI = categoriesRepoI;
        this.itemsRepoI = itemsRepoI;
    }

    @GetMapping("/add/{userId}")
    public String getUserWithID(@PathVariable(name = "userId") int id, Model model){

        model.addAttribute("name",usersRepoI.findById(id).get().getName());
        model.addAttribute("userId",id);
        model.addAttribute("category",usersRepoI.findById(id).get().getCategories().stream().distinct());
        return "addItems";
    }

    @PostMapping("/additem/{uId}/{cId}")
    public String addItem(@PathVariable(name = "uId") int uid,@PathVariable(name = "cId") int cid, @ModelAttribute("newItem") Items newItem, Model model)
    {
        itemsRepoI.save(newItem);
        Users testuser = usersRepoI.findById(uid).get();
        testuser.addItems(newItem);
        usersRepoI.save(testuser);

        Categories ctest = categoriesRepoI.findById(cid);
        ctest.addItems(newItem);
        categoriesRepoI.save(ctest);

        model.addAttribute("itemName",newItem.getItemName());
        model.addAttribute("userId",testuser.getUid());
        return "addItems";
    }



}
