//Controller class for handling request mapping to add items, view items

package org.deeptiagarwal.easylife.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.deeptiagarwal.easylife.dao.CategoriesRepoI;
import org.deeptiagarwal.easylife.dao.ItemsRepoI;
import org.deeptiagarwal.easylife.services.ItemsServices;
import org.deeptiagarwal.easylife.dao.UsersRepoI;
import org.deeptiagarwal.easylife.models.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
        return "add_items";
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
    public String viewItems(@PathVariable(name = "userId") int id, Model model) throws Exception {
        model.addAttribute("name",usersRepoI.findById(id).get().getName());
        model.addAttribute("userId",id);
        model.addAttribute("category",usersRepoI.findById(id).get().getCategories());
        return "view_items";
    }

    @GetMapping("/view/{userId}/{cId}")
    public ModelAndView viewItemsByCategory(@PathVariable(name = "userId") int uid, @PathVariable(name = "cId") int cid, Model model){
        List<Items> test = (itemsRepoI.findByUserAndCategory(usersRepoI.findById(uid).get(),categoriesRepoI.findById(cid)));
//        for (int i = 0; i <test.size() ; i++) {
//            log.warn(test.get(i).getItemName());
//            log.warn(test.get(i).getMeasuringUnit());
//            log.warn(String.valueOf(test.get(i).getQuantity()));
//        }
        model.addAttribute("name",usersRepoI.findById(uid).get().getName());
        model.addAttribute("userId",uid);
        model.addAttribute("category",usersRepoI.findById(uid).get().getCategories());
        model.addAttribute("Items",test);
        model.addAttribute("cName",categoriesRepoI.findById(cid).getCategoryName());
        return new ModelAndView("view_items");
    }


}
