package org.deeptiagarwal.easylife.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@SessionAttributes(value = {"userName","userEmail","userId"})
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

    //display admin dashboard to authorized admin users
    @GetMapping("/adminDashboard")
    public String adminDashboard(Model model, HttpServletRequest http){
        HttpSession session = http.getSession();
        String sessionUserName = session.getAttribute("userName").toString();
        String sessionUserEmail = session.getAttribute("userEmail").toString();
        int sessionUserID = Integer.parseInt(session.getAttribute("userId").toString());

        List<Users> allUsers = usersRepoI.findAll();
        List<AuthGroup> allAuthGroupUsers = authGroupRepoI.findAll();
        List<Categories> allCategories = categoriesRepoI.findAll();
        List<Items> allItems = itemsRepoI.findAll();
        String name = usersRepoI.findById(sessionUserID).get().getName();
        Set<String> distinctEmail = new HashSet<>();
        for (int i = 0; i < allAuthGroupUsers.size(); i++) {
            distinctEmail.add(allAuthGroupUsers.get(i).getEmail());
        }

        model.addAttribute("name",sessionUserName);
        model.addAttribute("users",allUsers);
        model.addAttribute("authUsers",allAuthGroupUsers);
        model.addAttribute("categories",allCategories);
        model.addAttribute("items",allItems);
        model.addAttribute("userId",sessionUserID);
        model.addAttribute("distinctEmail",distinctEmail);
        return "admin";
    }

    //Admin can add a new role to an authGroup User
    @PostMapping("/newAuthGroup")
    public RedirectView addNewAuthGroup(@ModelAttribute("authGroup") AuthGroup newAuthGroup, RedirectAttributes attributes){
        authGroupRepoI.save(newAuthGroup);
        log.warn("New authGroup added.");
        return new RedirectView("/admin/adminDashboard",true);
    }

    //Admin can add new category into the database using this controller
    @PostMapping("/newCategory")
    public RedirectView addNewCategory(@RequestParam("Category") String category, RedirectAttributes attributes){
        Categories newCategory = new Categories(category);
        categoriesRepoI.save(newCategory);
        log.warn("new category added "+newCategory);
        return new RedirectView("/admin/adminDashboard",true);
    }
}
