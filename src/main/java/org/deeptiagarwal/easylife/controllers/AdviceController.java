//AdviceController class for handling Exceptions
package org.deeptiagarwal.easylife.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
@Slf4j
public class AdviceController {

    @ExceptionHandler({Exception.class})
    public ModelAndView exceptionHandle(Exception ex, Model model){
        log.debug("There is some error");
        ex.printStackTrace();
        model.addAttribute("error",ex.getMessage());
        return new ModelAndView("error");
    }

}
