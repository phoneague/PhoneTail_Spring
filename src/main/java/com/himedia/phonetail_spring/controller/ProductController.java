package com.himedia.phonetail_spring.controller;

import com.himedia.phonetail_spring.dto.MemberDTO;
import com.himedia.phonetail_spring.dto.ProductDTO;
import com.himedia.phonetail_spring.service.ProductService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

@Controller
public class ProductController {

    @Autowired
    ProductService ps;

    @GetMapping("/productList")
    public ModelAndView productList(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();

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
        HttpSession session = request.getSession();
        MemberDTO mdto =(MemberDTO)session.getAttribute("login");
        String[] brandList = {"Samsung","Apple","LG", "기타"};
        String url = "member/loginForm";
        if(mdto != null){
            model.addAttribute("loginUser", mdto.getUserid());
            model.addAttribute("brandList", brandList);
            url = "product/productInsert";
        } else{
            model.addAttribute("message", "로그인을 해주십시오");
        }
        return url;
    }

    @Autowired
    ServletContext context;

    @PostMapping("/fileup")
    @ResponseBody       //자신을 호출하는 곳으로 '리턴되는 데이터'를 갖고 이동하여 페이지에 표시하라는 뜻
    public HashMap<String, Object> fileup(
            @RequestParam("fileimage") MultipartFile file,
            HttpServletRequest request, Model model
    ){

        String path = context.getRealPath("/product_images");
        Calendar today = Calendar.getInstance();
        long t = today.getTimeInMillis();
        String filename = file.getOriginalFilename();
        String fn1 = filename.substring(0, filename.indexOf("."));  //파일이름과 확장자 분리
        String fn2 = filename.substring(filename.indexOf("."));
        String uploadPath = path + "/" + fn1 + t + fn2;
        System.out.println("파일저장 경로 : " + uploadPath);
        HashMap<String, Object> result = new HashMap<String, Object>();
        try {
            file.transferTo(new File(uploadPath));      //파일이 업로드 및 저장되는 명령
            result.put("STATUS", 1);            //파일 전송 상태
            result.put("IMAGE", fn1 + t + fn2);
            result.put("SAVEIMAGEFILE", fn1 + t + fn2);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            result.put("STATUS", 0);
        } catch (IOException e) {
            e.printStackTrace();
            result.put("STATUS", 0);
        }
        return result;
    }

    @PostMapping("/productInsert")
    public String productInsert(@ModelAttribute("dto") @Valid ProductDTO productdto,
                                    BindingResult result, Model model,
                                RedirectAttributes redirectAttributes){
        String url = "redirect:/productInsertForm";
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            model.addAttribute("message", errorMessage);
            redirectAttributes.addFlashAttribute("message", errorMessage);
            redirectAttributes.addFlashAttribute("dto", productdto);
            return url;
        } else{
            ps.insertProduct(productdto);
            url = "redirect:/productList";


            return url;
        }
    }


    @GetMapping("/productUpdateForm")
    public String productUpdateForm(HttpServletRequest request, Model model){
        int pseq = Integer.parseInt(request.getParameter("pseq"));
        HttpSession session = request.getSession();
        MemberDTO mdto =(MemberDTO)session.getAttribute("login");
        String[] brandList = {"Samsung","Apple","LG", "기타"};

        model.addAttribute("ProductDTO", ps.getProduct(pseq));
        model.addAttribute("login", mdto);
        model.addAttribute("brandList", brandList);

        return "product/productUpdate";
    }

    @PostMapping("/productUpdate")
    public String productUpdate(@ModelAttribute("dto") @Valid ProductDTO productdto,
                                BindingResult result, Model model, RedirectAttributes redirectAttributes){
        String url = "redirect:/productUpdateForm";
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            model.addAttribute("message", errorMessage);
            redirectAttributes.addFlashAttribute("message", errorMessage);
            redirectAttributes.addFlashAttribute("dto", productdto);
            return url;
        } else{
            ps.updateProduct(productdto);
            url = "redirect:/productList";

            return url;
        }
    }

    @GetMapping("/productDelete")
    public String productDelete(@RequestParam("pseq") int pseq){
        ps.deleteProduct(pseq);
        return "redirect:/productList";
    }




}
