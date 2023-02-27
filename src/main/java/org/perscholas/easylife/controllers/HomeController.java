package org.perscholas.easylife.controllers;

import lombok.extern.slf4j.Slf4j;
import org.perscholas.easylife.dao.CategoriesRepoI;
import org.perscholas.easylife.dao.ItemsRepoI;
import org.perscholas.easylife.dao.UsersRepoI;
import org.perscholas.easylife.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
//
//    @GetMapping(value = {"action"})
//    public String actions(Model model){
//        log.info("i am in the index controller method");
//        return "actions";
//    }

//    @PostMapping("/user_login")
//    public String userLogin(@ModelAttribute("isUserAvailable") Users isUserAvailable, Model model){
//        Users existinguser = new Users();
//        existinguser = user.findUserByEmailAndPassword(isUserAvailable.getEmail(), isUserAvailable.getPassword());
//        if(existinguser == null)
//        {
//            model.addAttribute("notExist","User does not exist");
//            return "index";
//        }
//        else {
//            model.addAttribute("userName", existinguser.getName());
//            return "actions";
//        }
//    }

    @PostMapping("/user_login")
    public String userLogin(@RequestParam("email") String email, @RequestParam("password") String password, Model model){
        Users existinguser = new Users();
        existinguser = user.findByEmail(email);
        if(existinguser == null)
        {
            model.addAttribute("notExist","A User with this email does not exist");
            return "index";
        }
        else if(existinguser != null)
        {
            if(user.findByEmailAndPassword(email, password) == null)
            {
                model.addAttribute("notExist","Please enter the correct password.");
                return "index";
            }
        }
        model.addAttribute("userName", existinguser.getName());
        model.addAttribute("userId",existinguser.getUid());
        return "actions";
    }

    @PostMapping("/user_registration")
    public String userRegistration(@ModelAttribute("newUser") Users newUser){
        log.warn("User registration method" + newUser);
        log.warn(newUser.toString());
        user.save(newUser);
        newUser.addCategory(category.findById(1));
        newUser.addCategory(category.findById(2));
        user.save(newUser);
        return "index";
    }


}
