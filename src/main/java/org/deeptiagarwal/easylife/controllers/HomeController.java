//Controller class for handling request mapping to homepage, login, user registration and error

package org.deeptiagarwal.easylife.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.deeptiagarwal.easylife.services.UserServices;
import org.deeptiagarwal.easylife.dao.CategoriesRepoI;
import org.deeptiagarwal.easylife.dao.ItemsRepoI;
import org.deeptiagarwal.easylife.dao.UsersRepoI;
import org.deeptiagarwal.easylife.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = {"/", "index"})
    public String homePage(){
        log.info("Return Homepage");
        return "index";
    }

    @PostMapping("/user_login")
    public String userLogin(@RequestParam("email") String email, @RequestParam("password") String password, Model model){
        Users existinguser = new Users();
        existinguser = usersRepoI.findByEmail(email);
        if(existinguser == null)
        {
            model.addAttribute("notExist","A User with this email does not exist");
            return "index";
        }
        else if(existinguser != null)
        {
            if(usersRepoI.findByEmailAndPassword(email, password) == null)
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
    public String userRegistration(@ModelAttribute("newUser") Users newUser) throws Exception {
        log.warn("User registration method" + newUser);
        log.warn(newUser.toString());
        usersRepoI.save(newUser);
        userServices.addCategoriesToUser(newUser);
        userServices.addRolesToUser(newUser);
        return "index";
    }

    @GetMapping("/error")
    public String errorPage(){
        return "error";
    }

}
