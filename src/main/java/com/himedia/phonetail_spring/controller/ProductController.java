package com.himedia.phonetail_spring.controller;

import com.himedia.phonetail_spring.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class ProductController {

    @Autowired
    ProductService ps;

    @GetMapping("/productList")
    public ModelAndView productList(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        session.setAttribute("brand",request.getParameter("brand"));


        HashMap<String, Object> result = ps.getProductList(request);
        mav.addObject("productList", result.get("productList"));
        mav.addObject("paging", result.get("paging"));
        System.out.println(result.get("paging"));
        mav.addObject("brand", result.get("brand"));
        mav.addObject("key", result.get("key"));
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
