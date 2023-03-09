package org.deeptiagarwal.easylife.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.deeptiagarwal.easylife.dao.AuthGroupRepoI;
import org.deeptiagarwal.easylife.dao.CategoriesRepoI;
import org.deeptiagarwal.easylife.dao.ItemsRepoI;
import org.deeptiagarwal.easylife.dao.UsersRepoI;
import org.deeptiagarwal.easylife.models.AuthGroup;
import org.deeptiagarwal.easylife.models.Categories;
import org.deeptiagarwal.easylife.models.Items;
import org.deeptiagarwal.easylife.models.Users;
import org.deeptiagarwal.easylife.services.ItemsServices;
import org.deeptiagarwal.easylife.services.UserServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/admin")
public class AdminController {
    UsersRepoI usersRepoI;
    CategoriesRepoI categoriesRepoI;
    ItemsRepoI itemsRepoI;
    AuthGroupRepoI authGroupRepoI;
    UserServices userServices;
    ItemsServices itemsServices;


    public AdminController(UsersRepoI usersRepoI, CategoriesRepoI categoriesRepoI, ItemsRepoI itemsRepoI, AuthGroupRepoI authGroupRepoI, UserServices userServices, ItemsServices itemsServices) {
        this.usersRepoI = usersRepoI;
        this.categoriesRepoI = categoriesRepoI;
        this.itemsRepoI = itemsRepoI;
        this.authGroupRepoI = authGroupRepoI;
        this.userServices = userServices;
        this.itemsServices = itemsServices;
    }

//    Display admin dashboard
    @GetMapping("/{userId}")
    public String adminDashboard(@PathVariable(name = "userId") int userId, Model model){
        List<Users> allUsers = usersRepoI.findAll();
        List<AuthGroup> allAuthGroupUsers = authGroupRepoI.findAll();
        List<Categories> allCategories = categoriesRepoI.findAll();
        List<Items> allItems = itemsRepoI.findAll();
        String name = usersRepoI.findById(userId).get().getName();
        model.addAttribute("name",name);
        model.addAttribute("users",allUsers);
        model.addAttribute("authUsers",allAuthGroupUsers);
        model.addAttribute("categories",allCategories);
        model.addAttribute("items",allItems);
        model.addAttribute("userId",userId);
        return "admin";
    }

    //Add new role to an authGroup User
    @PostMapping("/newAuthGroup/{userId}")
    public RedirectView addNewAuthGroup(@ModelAttribute("authGroup") AuthGroup newAuthGroup, @PathVariable(name="userId") int userId, RedirectAttributes attributes){
        authGroupRepoI.save(newAuthGroup);
        attributes.addAttribute("userId",userId);
        return new RedirectView("/admin/{userId}",true);
    }

    //Admin can add new category into the database using this controller
    @PostMapping("/newCategory/{userId}")
    public RedirectView addNewCategory(@RequestParam("Category") String category, @PathVariable(name="userId") int userId, RedirectAttributes attributes){
        Categories newCategory = new Categories(category);
        categoriesRepoI.save(newCategory);
        attributes.addAttribute("userId",userId);
        return new RedirectView("/admin/{userId}",true);
    }
}
