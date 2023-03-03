package org.perscholas.easylife.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.perscholas.easylife.dao.CategoriesRepoI;
import org.perscholas.easylife.dao.ItemsRepoI;
import org.perscholas.easylife.dao.UsersRepoI;
import org.perscholas.easylife.models.Items;
import org.perscholas.easylife.services.ItemsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/action")
public class UserController {
    CategoriesRepoI categoriesRepoI;
    UsersRepoI usersRepoI;

    ItemsRepoI itemsRepoI;
    ItemsServices itemsServices;

    List<String> message = new ArrayList<>();

    @Autowired
    public UserController(CategoriesRepoI categoriesRepoI, UsersRepoI usersRepoI, ItemsRepoI itemsRepoI, ItemsServices itemsServices) {
        this.categoriesRepoI = categoriesRepoI;
        this.usersRepoI = usersRepoI;
        this.itemsRepoI = itemsRepoI;
        this.itemsServices = itemsServices;
    }

    @GetMapping("/add/{userId}")
    public String getUserWithID(@PathVariable(name = "userId") int id, Model model, @RequestParam(name = "msg",defaultValue = "") String msg){
        message.add(msg);
        model.addAttribute("name",usersRepoI.findById(id).get().getName());
        model.addAttribute("userId",id);
        model.addAttribute("category",usersRepoI.findById(id).get().getCategories());
        model.addAttribute("msg",message);
        return "addItems";
    }

    @PostMapping("/additem/{uId}/{cId}")
    public RedirectView addItem(@PathVariable(name = "uId") int uid, @PathVariable(name = "cId") int cid, @ModelAttribute("newItem") Items newItem, RedirectAttributes attributes) throws Exception {
        itemsRepoI.save(newItem);
        itemsServices.addUserAndCategorytoItem(usersRepoI.findById(uid).get(),categoriesRepoI.findById(cid),newItem);
        attributes.addAttribute("userId",uid);
        attributes.addAttribute("msg",newItem.getItemName()+" successfully added to "+categoriesRepoI.findById(cid).getCategoryName());
        return new RedirectView( "/action/add/{userId}",true);
    }

    @GetMapping("/view/{userId}")
    public String viewAllItems(@PathVariable(name = "userId") int id, Model model) throws Exception {
        model.addAttribute("name",usersRepoI.findById(id).get().getName());
        model.addAttribute("userId",id);
        model.addAttribute("category",usersRepoI.findById(id).get().getCategories());

        List<Items> test = (itemsRepoI.findByUserAndCategory(usersRepoI.findById(2).get(),categoriesRepoI.findById(3)));
        for (int i = 0; i <test.size() ; i++) {
            log.warn(test.get(i).getItemName());
            log.warn(test.get(i).getMeasuringUnit());
            log.warn(String.valueOf(test.get(i).getQuantity()));
        }
        return "viewItems";
    }

}
