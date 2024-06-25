package com.himedia.phonetail_spring.controller;

import com.himedia.phonetail_spring.dto.AdminDTO;
import com.himedia.phonetail_spring.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class AdminController {

    @Autowired
    AdminService as;

    @GetMapping("/admin")
    public String admin(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        String url = "redirect:/adminReportList";
        AdminDTO adto = (AdminDTO) session.getAttribute("adminUser");
        if (adto == null) {
            url = "admin/adminLogin";
        } else {
            url = "redirect:/adminReportList";
        }
        return url;
    }

    @PostMapping("adminLogin")
    public String adminLogin(@ModelAttribute("dto") @Valid AdminDTO admindto, BindingResult bindingResult, HttpServletRequest request, Model model) throws IOException {
        String url = "admin/adminLogin";
        if (bindingResult.getFieldError("adminid") != null) {
            model.addAttribute("message", "아이디를 입력하세요");
        } else if (bindingResult.getFieldError("pwd") != null) {
            model.addAttribute("message", "패스워드를 입력하세요");
        } else {
            AdminDTO adto = as.getAdmin(admindto.getAdminid());
            if(adto ==null){
                model.addAttribute("message","아이디/패스워드를 확인하세요");
            }else if (!adto.getPwd().equals(admindto.getPwd())) {
                model.addAttribute("message", "아이디/패스워드를 확인하세요");
            } else if (adto.getPwd().equals(admindto.getPwd())) {
                HttpSession session = request.getSession();
                session.setAttribute("adminUser", adto);
                url = "redirect:/adminReportList";
            }
        }
        return url;
    }

    @GetMapping("/adminLogout")
    public String adminLogout(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("adminUser");
        return "redirect:/";
    }

    @GetMapping("/adminReportList")
    public ModelAndView adminReportList(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("adminUser") == null) {
            mav.setViewName("admin/adminLogin");
        } else {
            HashMap<String, Object> result = as.getAdminReportList(request);
            mav.addObject("reportList",result.get("reportList"));
            mav.addObject("paging",result.get("paging"));
            mav.addObject("key",result.get("key"));
            mav.setViewName("admin/report/adminReportList");
        }
        return mav;
    }

    @GetMapping("adminQnaList")
    public ModelAndView adminQnaList(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("adminUser") == null) {
            mav.setViewName("admin/adminLogin");
        } else {
            HashMap<String, Object> result = as.getAdminQnaList(request);
            mav.addObject("qnaList",result.get("qnaList"));
            mav.addObject("paging",result.get("paging"));
            mav.addObject("key",result.get("key"));
            mav.setViewName("admin/qna/adminQnaList");
        }
        return mav;
    }

    @GetMapping("/adminMemberList")
    public ModelAndView adminMemberList(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("adminUser") == null) {
            mav.setViewName("admin/adminLogin");
        } else {
            HashMap<String, Object> result = as.getAdminMemberList(request);
            mav.addObject("memberList",result.get("memberList"));
            mav.addObject("paging",result.get("paging"));
            mav.addObject("key",result.get("key"));
            mav.setViewName("admin/member/adminMemberList");
        }
        return mav;
    }



}
