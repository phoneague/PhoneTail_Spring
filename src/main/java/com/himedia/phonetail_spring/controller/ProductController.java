package com.himedia.phonetail_spring.controller;

import com.himedia.phonetail_spring.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

    @Autowired
    ProductService ps;

    @GetMapping("/productList")
    public ModelAndView productList(@RequestParam String brand){
        ModelAndView mav = new ModelAndView();
        mav.addObject("productList", ps.getBrandList(brand));
        mav.setViewName("product/productList");

        return mav;
    }

    @GetMapping("/productDetail")
    public ModelAndView productDetail(@RequestParam("pseq") int pseq){
        ModelAndView mav = new ModelAndView();
        mav.addObject("productDTO", ps.getProduct(pseq));
        mav.setViewName("product/productDetail");

        return mav;
    }



    @GetMapping("/productInsertForm")
    public String productInsertForm(HttpServletRequest request, Model model){
        return "product/productInsert";
    }

    @GetMapping("/productUpdateForm")
    public String productUpdateForm(HttpServletRequest request, Model model){
        return "product/productUpdate";
    }





}
