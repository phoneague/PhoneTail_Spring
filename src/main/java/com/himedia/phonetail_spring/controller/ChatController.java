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
import java.util.List;

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

            return "redirect:/goChatList";
        } else {
            // Assuming 'filter' method returns a valid lseq
            ChatListDTO fdto = cs.filter(pseq, ((MemberDTO) session.getAttribute("login")).getUserid());
            redirectAttributes.addAttribute("lseq", fdto.getLseq());
            return "redirect:/chating?lseq=" + fdto.getLseq();

        }
    }

    @GetMapping("/goChatList")
    public String goChatList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        MemberDTO mdto = (MemberDTO) session.getAttribute("login");

        String key = ""; // 필요한 경우 key 값을 설정

        HashMap<String, Object> result = cs.chatList(request); // ChatService의 chatList 메서드 호출

        ArrayList<ChatListDTO> chatList = (ArrayList<ChatListDTO>) result.get("chatList");
        int maxLseq = Integer.MIN_VALUE;

        // chatList에서 최대 lseq 값을 찾기
        for (ChatListDTO chat : chatList) {
            int lseq = chat.getLseq();
            if (lseq > maxLseq) {
                maxLseq = lseq;
            }
        }

        // 최대 lseq에 해당하는 채팅방 정보 가져오기
        ChatListDTO chatListDTO = cs.getChatListByLseq(maxLseq);
        List<ChatingDTO> chatingList = cs.getChatingByLseq(maxLseq);

        model.addAttribute("loginUser", mdto.getUserid());
        model.addAttribute("chatingList", chatingList);
        model.addAttribute("chatList", chatListDTO);

        return "redirect:/chating?lseq=" + maxLseq;
    }


}
