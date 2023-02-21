package org.perscholas.easylife.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.easylife.dao.CategoriesRepoI;
import org.perscholas.easylife.dao.ItemsRepoI;
import org.perscholas.easylife.dao.UsersRepoI;
import org.perscholas.easylife.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j

public class HomeController {
    UsersRepoI user;
    CategoriesRepoI category;
    ItemsRepoI items;

    @Autowired
    public HomeController(UsersRepoI user, CategoriesRepoI category, ItemsRepoI items) {
        this.user = user;
        this.category = category;
        this.items = items;
    }

    @GetMapping(value = {"/", "index"})
    public String homePage(){
        log.info("i am in the index controller method");
        return "index";
    }

    @GetMapping(value = {"action"})
    public String actions(Model model){
        log.info("i am in the index controller method");
        return "actions";
    }

    @PostMapping("/user_registration")
    public String userRegistration(@ModelAttribute("newUser") Users newUser){
        log.warn("User registration method" + newUser);
        log.warn(newUser.toString());
        user.save(newUser);
        return "actions";
    }
}
