package org.perscholas.easylife.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

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

    @PostMapping("/user_login")
    public String userLogin(@ModelAttribute("isUserAvailable") Users isUserAvailable, Model model){
        Users existinguser = new Users();
        existinguser = user.findUserByEmailAndPassword(isUserAvailable.getEmail(), isUserAvailable.getPassword());
        if(existinguser == null)
        {
            model.addAttribute("notExist","User does not exist");
            return "index";
        }
        else {
            model.addAttribute("userName", existinguser.getName());
            return "actions";
        }
    }

    @PostMapping("/user_registration")
    public String userRegistration(@ModelAttribute("newUser") Users newUser){
        log.warn("User registration method" + newUser);
        log.warn(newUser.toString());
        user.save(newUser);
        return "index";
    }
}
