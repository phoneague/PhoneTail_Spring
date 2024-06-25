package com.himedia.phonetail_spring.controller;

import com.himedia.phonetail_spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class MainController {

    @Autowired
    ProductService ps;

    @GetMapping("/")
    public ModelAndView main() {
        ModelAndView mav = new ModelAndView();
        HashMap<String, Object> result = ps.getNewList();

        mav.addObject("newList", result.get("newList"));
        mav.setViewName("main");
        return mav;
    }
}
