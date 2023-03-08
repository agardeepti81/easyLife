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

    @PostMapping("/additem/{userId}/{cId}")
    public RedirectView addItem(@PathVariable(name = "userId") int uid, @PathVariable(name = "cId") int cid, @ModelAttribute("newItem") Items newItem, RedirectAttributes attributes) throws Exception {
        itemsRepoI.save(newItem);
        itemsServices.addUserAndCategorytoItem(usersRepoI.findById(uid).get(),categoriesRepoI.findById(cid).get(),newItem);
        attributes.addAttribute("userId",uid);
        attributes.addAttribute("msg",newItem.getItemName()+" successfully added to "+categoriesRepoI.findById(cid).get().getCategoryName());
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
        List<Items> items = (itemsRepoI.findByUserAndCategory(usersRepoI.findById(uid).get(),categoriesRepoI.findById(cid).get()));
        model.addAttribute("name",usersRepoI.findById(uid).get().getName());
        model.addAttribute("userId",uid);
        model.addAttribute("category",usersRepoI.findById(uid).get().getCategories());
        model.addAttribute("Items",items);
        model.addAttribute("cName",categoriesRepoI.findById(cid).get().getCategoryName());
        return new ModelAndView("view_items");
    }

    @GetMapping("/viewAll/{userId}")
    public ModelAndView viewAllItemsForUser(@PathVariable(name = "userId") int uid, Model model){
        List<Items> items = (itemsRepoI.findByUser(usersRepoI.findById(uid).get()));
        model.addAttribute("name",usersRepoI.findById(uid).get().getName());
        model.addAttribute("userId",uid);
        model.addAttribute("category",usersRepoI.findById(uid).get().getCategories());
        model.addAttribute("Items",items);

        return new ModelAndView("view_items");
    }

    @GetMapping("/edit/{userId}")
    public String editItems(@PathVariable(name = "userId") int id, Model model) throws Exception {
        model.addAttribute("name",usersRepoI.findById(id).get().getName());
        model.addAttribute("userId",id);
        model.addAttribute("category",usersRepoI.findById(id).get().getCategories());
        return "edit_items";
    }

    @GetMapping("/edit/{userId}/{cId}")
    public ModelAndView editItemsByCategory(@PathVariable(name = "userId") int uid, @PathVariable(name = "cId") int cid, Model model){
        List<Items> items = (itemsRepoI.findByUserAndCategory(usersRepoI.findById(uid).get(),categoriesRepoI.findById(cid).get()));
        model.addAttribute("name",usersRepoI.findById(uid).get().getName());
        model.addAttribute("userId",uid);
        model.addAttribute("category",usersRepoI.findById(uid).get().getCategories());
        model.addAttribute("Items",items);
        model.addAttribute("cId",cid);
        model.addAttribute("cName",categoriesRepoI.findById(cid).get().getCategoryName());
        return new ModelAndView("edit_items");
    }

    @PostMapping("/edititem/{itemId}")
    public RedirectView editItems(@PathVariable(name = "itemId") int itemId, @ModelAttribute("editItem") Items editItem, RedirectAttributes attributes) throws Exception {
        int userId = itemsRepoI.findById(itemId).get().getUser().getUid();
        itemsServices.editItem(itemId,editItem);
        attributes.addAttribute("userId", userId);
        log.warn("Item Edited");
        return new RedirectView("/action/edit/{userId}",true);
    }

    @GetMapping("/delete/{itemId}")
    public RedirectView deleteItems(@PathVariable(name = "itemId") int itemId, RedirectAttributes attributes) throws Exception {
        int userId = itemsRepoI.findById(itemId).get().getUser().getUid();
        itemsServices.deleteItem(userId,itemId);
        log.warn("Item Deleted");
        attributes.addAttribute("userId", userId);
        return new RedirectView("/action/edit/{userId}",true);
    }

}
