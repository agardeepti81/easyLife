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
import org.perscholas.easylife.services.ItemsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/action")
public class UserController {
    CategoriesRepoI categoriesRepoI;
    UsersRepoI usersRepoI;

    ItemsRepoI itemsRepoI;
    ItemsServices itemsServices;

    @Autowired
    public UserController(CategoriesRepoI categoriesRepoI, UsersRepoI usersRepoI, ItemsRepoI itemsRepoI, ItemsServices itemsServices) {
        this.categoriesRepoI = categoriesRepoI;
        this.usersRepoI = usersRepoI;
        this.itemsRepoI = itemsRepoI;
        this.itemsServices = itemsServices;
    }

    @GetMapping("/add/{userId}")
    public String getUserWithID(@PathVariable(name = "userId") int id, Model model){

        model.addAttribute("name",usersRepoI.findById(id).get().getName());
        model.addAttribute("userId",id);
        model.addAttribute("category",usersRepoI.findById(id).get().getCategories());
        return "addItems";
    }

    @PostMapping("/additem/{uId}/{cId}")
    public RedirectView addItem(@PathVariable(name = "uId") int uid, @PathVariable(name = "cId") int cid, @ModelAttribute("newItem") Items newItem, RedirectAttributes attributes) throws Exception {
        itemsRepoI.save(newItem);
        itemsServices.addUserAndCategorytoItem(usersRepoI.findById(uid).get(),categoriesRepoI.findById(cid),newItem);
        attributes.addAttribute("userId",uid);
        return new RedirectView( "/action/add/{userId}");
    }



}
