//Controller class for handling request mapping to homepage, login, user registration and error

package org.deeptiagarwal.easylife.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
import java.util.Optional;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@SessionAttributes(value = {"userName","userEmail","userId"})
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


    //Display homepage, user can log in or create a new account using this page
    @GetMapping(value = {"/","/index"})
    public String homePage(){
        log.info("Return Homepage");
        return "index";
    }

    //User login processing and returning of user dashboard
    @GetMapping("/index/dashboard")
    public String userLogin(Model model, HttpServletRequest http){
        HttpSession session = http.getSession();
        log.warn("Session Id "+session.getId());
        String sessionUserName = session.getAttribute("userName").toString();
        String sessionUserEmail = session.getAttribute("userEmail").toString();
        int sessionUserID = Integer.parseInt(session.getAttribute("userId").toString());
        model.addAttribute("name", sessionUserName);
        model.addAttribute("userId",sessionUserID);
        return "actions";
    }

    //Success page after User Registration
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

    //Mapping for error Page
    @GetMapping("/index/error")
    public String errorPage(){
        return "error";
    }

    //Mapping for logout Page
    @GetMapping("/index/logout")
    public String logoutPage(){
        return "logout";
    }

    //Mapping for access denied Page
    @GetMapping("/403")
    public String accessDenied(){
        return "403";
    }

}
