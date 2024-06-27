package com.himedia.phonetail_spring.controller;

import com.himedia.phonetail_spring.dto.ChatListDTO;
import com.himedia.phonetail_spring.dto.ChatingDTO;
import com.himedia.phonetail_spring.dto.MemberDTO;
import com.himedia.phonetail_spring.dto.ProductDTO;
import com.himedia.phonetail_spring.service.ChatService;
import jakarta.servlet.ServletException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class ChatController {

    @Autowired
    ChatService cs;

    @GetMapping("/chatList")
    public ModelAndView chatList(HttpServletRequest request) throws ServletException, IOException {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("login") == null){
            mav.setViewName("member/loginForm");
        }else{
            HashMap<String, Object> result = cs.chatList(request);
            mav.addObject("loginUser", result.get("loginUser"));
            mav.addObject("chatList", result.get("chatList"));
            mav.setViewName("chat/ChatList");

        }
        return mav;
    }

    @GetMapping("/chating")
    public ModelAndView chating(@RequestParam("lseq") int lseq, HttpServletRequest request) throws ServletException, IOException{
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("login") == null){
            mav.setViewName("member/loginForm");
        }else{
            HashMap<String, Object> result = cs.getChatList(lseq, request);
            mav.addObject("loginUser", result.get("loginUser"));
            mav.addObject("chatList", result.get("chatList"));
            mav.addObject("chatingList", cs.getChating(lseq));
            mav.addObject("lseq", lseq);
            mav.setViewName("chat/Chating");
        }
        return mav;
    }

    @PostMapping("/insertChat")
    public String InsertChat(@ModelAttribute("dto") @Valid ChatingDTO chatingdto,
                             BindingResult result, Model model,
                             RedirectAttributes redirectAttributes
    /*, @RequestParam("lseq") int lseq, @RequestParam("userid") String userid, @RequestParam("content") String content */ ){
        String url = "redirect:/chating?lseq=" + chatingdto.getLseq();
        cs.insertChat(chatingdto);
        return url;
    }


    @GetMapping("/insertChatList")
    public String insertChatList(HttpSession session, @RequestParam("pseq") int pseq, RedirectAttributes redirectAttributes) throws IOException {
        boolean success = cs.insertChatList(session, pseq);
        if (success) {
            return "redirect:/gochatList";
        } else {
            // Assuming 'filter' method returns a valid lseq
            ChatListDTO fdto = cs.filter(pseq, ((MemberDTO) session.getAttribute("login")).getUserid());
            redirectAttributes.addAttribute("lseq", fdto.getLseq());
            return "redirect:/chating?lseq=" + fdto.getLseq();

        }
    }

}
