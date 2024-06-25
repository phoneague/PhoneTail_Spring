package com.himedia.phonetail_spring.controller;

import com.himedia.phonetail_spring.dto.AdminDTO;
import com.himedia.phonetail_spring.dto.MemberDTO;
import com.himedia.phonetail_spring.service.MypageService;
import com.himedia.phonetail_spring.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class MypageController {

    @Autowired
    MypageService ms;

    @GetMapping("/mypage")
    public String mypage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String url = "admin/adminLogin";
        MemberDTO mdto = (MemberDTO) session.getAttribute("login");
        if (mdto == null) {
            url = "member/loginForm";
        } else {
            url = "mypage/mypage";
        }
        return url;
    }

    @GetMapping("/myQnaList")
    public ModelAndView myQnaList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        String url = "member/loginForm";
        MemberDTO mdto = (MemberDTO) session.getAttribute("login");
        if (mdto == null) {
            mav.setViewName("member/loginForm");
        } else {
            HashMap<String, Object> result = ms.getMyQnaList(request);
            mav.addObject("questionList",result.get("questionList"));
            mav.addObject("paging",result.get("paging"));
            mav.addObject("key",result.get("key"));
            mav.setViewName("mypage/myQnaList");
        }
        return mav;
    }

    @GetMapping("/myReportList")
    public ModelAndView myReportList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        String url = "member/loginForm";
        MemberDTO mdto = (MemberDTO) session.getAttribute("login");
        if (mdto == null) {
            mav.setViewName("member/loginForm");
        } else {
            HashMap<String, Object> result = ms.getMyReportList(request);
            mav.addObject("reportList",result.get("reportList"));
            mav.addObject("paging",result.get("paging"));
            mav.setViewName("mypage/myReportList");
        }
        return mav;
    }

    @GetMapping("/myProductList")
    public ModelAndView myProductList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        String url = "member/loginForm";
        MemberDTO mdto = (MemberDTO) session.getAttribute("login");
        if (mdto == null) {
            mav.setViewName("member/loginForm");
        } else {
            HashMap<String, Object> result = ms.getMyProductList(request);
            mav.addObject("productList",result.get("productList"));
            mav.addObject("paging",result.get("paging"));
            mav.setViewName("mypage/myProductList");
        }
        return mav;
    }

    @GetMapping("myWantList")
    public ModelAndView myWantList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        String url = "member/loginForm";
        MemberDTO mdto = (MemberDTO) session.getAttribute("login");
        if (mdto == null) {
            mav.setViewName("member/loginForm");
        } else {
            HashMap<String, Object> result = ms.getMyWantList(request);
            mav.addObject("wantList",result.get("wantList"));
            mav.addObject("paging",result.get("paging"));
            mav.setViewName("mypage/myWantList");
        }
        return mav;
    }


}
