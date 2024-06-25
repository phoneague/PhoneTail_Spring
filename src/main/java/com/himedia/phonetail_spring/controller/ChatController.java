package com.himedia.phonetail_spring.controller;

import com.himedia.phonetail_spring.service.ChatService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class ChatController {

    @Autowired
    ChatService cs;

    @GetMapping("/chatList")
    public ModelAndView chat(HttpServletRequest request) throws ServletException, IOException {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("login") == null){
            mav.setViewName("member/loginForm");
        }else{
            HashMap<String, Object> result = cs.getChatList(request);
            mav.addObject("loginUser", result.get("paging"));
            mav.addObject("chatList", result.get("chatList"));
            mav.setViewName("chat/ChatList");
        }
        return mav;
    }
}
