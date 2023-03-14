//Controller class for handling request mapping to add items, view items

package org.deeptiagarwal.easylife.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.deeptiagarwal.easylife.dao.CategoriesRepoI;
import org.deeptiagarwal.easylife.dao.ItemsRepoI;
import org.deeptiagarwal.easylife.models.Categories;
import org.deeptiagarwal.easylife.models.Users;
import org.deeptiagarwal.easylife.services.ItemsServices;
import org.deeptiagarwal.easylife.dao.UsersRepoI;
import org.deeptiagarwal.easylife.models.Items;
import org.deeptiagarwal.easylife.services.UserServices;
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
@SessionAttributes(value = {"userName","userEmail","userId"})
@RequestMapping("/action")

public class UserController {
    CategoriesRepoI categoriesRepoI;
    UsersRepoI usersRepoI;

    ItemsRepoI itemsRepoI;
    UserServices userServices;
    ItemsServices itemsServices;

    @Autowired
    public UserController(CategoriesRepoI categoriesRepoI, UsersRepoI usersRepoI, ItemsRepoI itemsRepoI, UserServices userServices, ItemsServices itemsServices, List<String> message) {
        this.categoriesRepoI = categoriesRepoI;
        this.usersRepoI = usersRepoI;
        this.itemsRepoI = itemsRepoI;
        this.userServices = userServices;
        this.itemsServices = itemsServices;
    }

    //mapping for add items page, shows users all the categories in which they can add an item
    @GetMapping("/add")
    public String getUserWithID(Model model, @RequestParam(name = "msg",defaultValue = "") String msg, HttpServletRequest http) throws Exception {
        HttpSession session = http.getSession();
        String sessionUserName = session.getAttribute("userName").toString();
        String sessionUserEmail = session.getAttribute("userEmail").toString();
        int sessionUserID = Integer.parseInt(session.getAttribute("userId").toString());

        Users user = usersRepoI.findByEmail(sessionUserEmail).get();
        List<Categories> categoryList = userServices.getCategories(user);
        model.addAttribute("name",sessionUserName);
        model.addAttribute("userId",sessionUserID);
        model.addAttribute("category",user.getCategories());
        model.addAttribute("cList",categoryList);
        model.addAttribute("msg",msg);
        return "add_items";
    }

    //Form processing, add the items entered by user into specific category
    @PostMapping("/additem/{cId}")
    public RedirectView addItem(@PathVariable(name = "cId") int cid, @ModelAttribute("newItem") Items newItem, RedirectAttributes attributes, HttpServletRequest http) throws Exception {
        itemsRepoI.save(newItem);

        HttpSession session = http.getSession();
        int sessionUserID = Integer.parseInt(session.getAttribute("userId").toString());

        itemsServices.addUserAndCategorytoItem(usersRepoI.findById(sessionUserID).get(),categoriesRepoI.findById(cid).get(),newItem);
        attributes.addAttribute("msg",newItem.getItemName()+" successfully added to "+categoriesRepoI.findById(cid).get().getCategoryName());
        return new RedirectView( "/action/add",true);
    }

    //User can choose new category to add to their account from the available categories
    @PostMapping("/addCategory")
    public RedirectView addCategoryToUser(@RequestParam("addCategoryName") int cId, HttpServletRequest http) throws Exception {
        HttpSession session = http.getSession();
        int sessionUserID = Integer.parseInt(session.getAttribute("userId").toString());

        log.warn(String.valueOf(cId));
        userServices.addCategoryToUser(sessionUserID,cId);
        return new RedirectView( "/action/add",true);
    }

    //Takes the user to view items page and display all categories as buttons and an option to view all
    @GetMapping("/view")
    public String viewItems(Model model, HttpServletRequest http) {
        HttpSession session = http.getSession();
        String sessionUserName = session.getAttribute("userName").toString();
        int sessionUserID = Integer.parseInt(session.getAttribute("userId").toString());

        model.addAttribute("name",sessionUserName);
        model.addAttribute("userId",sessionUserID);
        model.addAttribute("category",usersRepoI.findById(sessionUserID).get().getCategories());
        return "view_items";
    }

    //Display the items by category
    @GetMapping("/view/{cId}")
    public ModelAndView viewItemsByCategory(@PathVariable(name = "cId") int cid, Model model, HttpServletRequest http){
        HttpSession session = http.getSession();
        String sessionUserName = session.getAttribute("userName").toString();
        int sessionUserID = Integer.parseInt(session.getAttribute("userId").toString());

        List<Items> items = (itemsRepoI.findByUserAndCategory(usersRepoI.findById(sessionUserID).get(),categoriesRepoI.findById(cid).get()));
        model.addAttribute("name",sessionUserName);
        model.addAttribute("userId",sessionUserID);
        model.addAttribute("category",usersRepoI.findById(sessionUserID).get().getCategories());
        model.addAttribute("Items",items);
        model.addAttribute("cName",categoriesRepoI.findById(cid).get().getCategoryName());
        return new ModelAndView("view_items");
    }

    //Display all items
    @GetMapping("/viewAll")
    public ModelAndView viewAllItemsForUser(Model model, HttpServletRequest http){
        HttpSession session = http.getSession();
        String sessionUserName = session.getAttribute("userName").toString();
        int sessionUserID = Integer.parseInt(session.getAttribute("userId").toString());

        List<Items> items = (itemsRepoI.findByUser(usersRepoI.findById(sessionUserID).get()));
        model.addAttribute("name",sessionUserName);
        model.addAttribute("userId",sessionUserID);
        model.addAttribute("category",usersRepoI.findById(sessionUserID).get().getCategories());
        model.addAttribute("Items",items);
        model.addAttribute("cName","View All Items");
        return new ModelAndView("view_items");
    }

    //Takes the user to update items page and display all categories as buttons
    @GetMapping("/edit")
    public String editItems(Model model, HttpServletRequest http){
        HttpSession session = http.getSession();
        String sessionUserName = session.getAttribute("userName").toString();
        int sessionUserID = Integer.parseInt(session.getAttribute("userId").toString());

        model.addAttribute("name",sessionUserName);
        model.addAttribute("userId",sessionUserID);
        model.addAttribute("category",usersRepoI.findById(sessionUserID).get().getCategories());
        return "edit_items";
    }

    // onclick of category buttons the items appear along with an edit and delete button
    @GetMapping("/edit/{cId}")
    public ModelAndView editItemsByCategory(@PathVariable(name = "cId") int cid, Model model, HttpServletRequest http){
        HttpSession session = http.getSession();
        String sessionUserName = session.getAttribute("userName").toString();
        int sessionUserID = Integer.parseInt(session.getAttribute("userId").toString());

        List<Items> items = (itemsRepoI.findByUserAndCategory(usersRepoI.findById(sessionUserID).get(),categoriesRepoI.findById(cid).get()));
        model.addAttribute("name",sessionUserName);
        model.addAttribute("userId",sessionUserID);
        model.addAttribute("category",usersRepoI.findById(sessionUserID).get().getCategories());
        model.addAttribute("Items",items);
        model.addAttribute("cId",cid);
        model.addAttribute("cName",categoriesRepoI.findById(cid).get().getCategoryName());
        return new ModelAndView("edit_items");
    }

    //Edits the selected item
    @PostMapping("/edititem/{itemId}")
    public RedirectView editItems(@PathVariable(name = "itemId") int itemId, @ModelAttribute("editItem") Items editItem, RedirectAttributes attributes) throws Exception {
        itemsServices.editItem(itemId,editItem);
        log.warn("Item Edited");
        return new RedirectView("/action/edit",true);
    }

    //Deletes the selected item
    @GetMapping("/delete/{itemId}")
    public RedirectView deleteItems(@PathVariable(name = "itemId") int itemId, RedirectAttributes attributes) throws Exception {
        int userId = itemsRepoI.findById(itemId).get().getUser().getUid();
        itemsServices.deleteItem(userId,itemId);
        log.warn("Item Deleted");
        return new RedirectView("/action/edit",true);
    }


}
