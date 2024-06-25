package com.himedia.phonetail_spring.controller;

import com.himedia.phonetail_spring.dao.IMemberDAO;
import com.himedia.phonetail_spring.dto.MemberDTO;
import com.himedia.phonetail_spring.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @Autowired
    MemberService ms;

    @PostMapping("/login")
    public String login (HttpServletRequest request) {
        String userid = request.getParameter("userid");
        String pwd = request.getParameter("pwd");

        MemberDTO mdto = ms.getMember(userid);

        String url = "member/loginForm";

        if (mdto == null)
            request.setAttribute("message", "아이디가 없습니다");
        else if (!mdto.getPwd().equals(pwd))
            request.setAttribute("message", "패스워드가 틀립니다");
        else if (mdto.getUserstate().equals("N"))
            request.setAttribute("message", "해당 계정은 휴면상태이거나 탈퇴상태입니다. 관리자에게 문의하세요");
        else if (mdto.getPwd().equals(pwd)) {
            url = "redirect:/";
            HttpSession session = request.getSession();
            session.setAttribute("login", mdto);
        } else {
            request.setAttribute("message", "관리자에게 문의하세요");
        }
        return url;
    }

    @GetMapping("/loginForm")
    public String loginForm () {
        return "member/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm () {
        return "member/joinForm";
    }



}
