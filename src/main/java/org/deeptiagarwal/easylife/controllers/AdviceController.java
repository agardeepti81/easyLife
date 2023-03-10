//AdviceController class for handling Exceptions
package org.deeptiagarwal.easylife.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
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
    @ExceptionHandler({Exception.class})
    public ModelAndView exceptionHandle(Exception ex, Model model){
        ex.printStackTrace();
        log.debug(ex.getMessage());
        model.addAttribute("error",ex.getMessage());
        return new ModelAndView("error");
    }

}
