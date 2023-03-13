//AdviceController class for handling Exceptions
package org.deeptiagarwal.easylife.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.deeptiagarwal.easylife.dao.UsersRepoI;
import org.deeptiagarwal.easylife.models.Users;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class AdviceController {
    private final UsersRepoI usersRepoI;

    public AdviceController(UsersRepoI usersRepoI) {
        this.usersRepoI = usersRepoI;
    }

    //Handles all exception and redirects user to exception page
    @ExceptionHandler({Exception.class})
    public ModelAndView exceptionHandle(Exception ex, Model model){
        ex.printStackTrace();
        log.debug(ex.getMessage());
        model.addAttribute("error",ex.getMessage());
        return new ModelAndView("error");
    }

    //Take the values for current user who has logged in and set those values as session variable
    @ModelAttribute
    public void currentUser(Model model, HttpServletRequest request, HttpSession http){
        Principal principal = request.getUserPrincipal();
        Users user = null;
        if(principal != null){
            user =  usersRepoI.findByEmail(principal.getName()).get();
            http.setAttribute("userName", user.getName());
            http.setAttribute("userEmail", user.getEmail());
            http.setAttribute("userId", user.getUid());
        }
    }
}
