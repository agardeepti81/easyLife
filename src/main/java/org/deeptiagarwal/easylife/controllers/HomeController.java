//Controller class for handling request mapping to homepage, login, user registration and error

package org.deeptiagarwal.easylife.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.deeptiagarwal.easylife.dao.AuthGroupRepoI;
import org.deeptiagarwal.easylife.services.UserServices;
import org.deeptiagarwal.easylife.dao.CategoriesRepoI;
import org.deeptiagarwal.easylife.dao.ItemsRepoI;
import org.deeptiagarwal.easylife.dao.UsersRepoI;
import org.deeptiagarwal.easylife.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomeController {
    UsersRepoI usersRepoI;
    CategoriesRepoI categoriesRepoI;
    ItemsRepoI itemsRepoI;
    UserServices userServices;

    @Autowired
    public HomeController(UsersRepoI usersRepoI, CategoriesRepoI categoriesRepoI, ItemsRepoI itemsRepoI, UserServices userServices) {
        this.usersRepoI = usersRepoI;
        this.categoriesRepoI = categoriesRepoI;
        this.itemsRepoI = itemsRepoI;
        this.userServices = userServices;
    }


    //Display homepage, user can login or create a new account using this page
    @GetMapping(value = {"/","/index"})
    public String homePage(){
        log.info("Return Homepage");
        return "index";
    }

    //User login processing and returning of user dashboard
    @GetMapping("/index/dashboard")
    public String userLogin(Principal principal, Model model){
        String email = principal.getName();
        log.warn("Principal Name "+email);
        Users existingUser = usersRepoI.findByEmail(email);
        int userId = existingUser.getUid();
        String name = existingUser.getName();
        model.addAttribute("name", name);
        model.addAttribute("userId",userId);
        return "actions";
    }

    @PostMapping("/index/user_registration")
    public String userRegistration(@ModelAttribute("newUser") Users newUser) throws Exception {
        log.warn("User registration method" + newUser);
        log.warn(newUser.toString());
        usersRepoI.save(newUser);
        userServices.addCategoriesToUser(newUser);
        userServices.addRolesToUser(newUser);
        log.warn("New User Registration Done Successfully");
        return "index";
    }

    @GetMapping("/index/error")
    public String errorPage(){
        return "error";
    }

    @GetMapping("/index/logout")
    public String logoutPage(){
        return "logout";
    }

}
