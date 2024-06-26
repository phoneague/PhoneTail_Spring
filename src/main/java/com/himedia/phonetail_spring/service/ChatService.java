package com.himedia.phonetail_spring.service;

import com.himedia.phonetail_spring.dao.IChatDAO;
import com.himedia.phonetail_spring.dto.ChatListDTO;
import com.himedia.phonetail_spring.dto.ChatingDTO;
import com.himedia.phonetail_spring.dto.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    IChatDAO cdao;


    public HashMap<String, Object> chatList(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        MemberDTO mdto = (MemberDTO)session.getAttribute("login");
        String key="";
        if(request.getParameter("key")!=null) {
            key=request.getParameter("key");
            session.setAttribute("key",key);
        }else if(session.getAttribute("key") !=null) {
            key = (String)session.getAttribute("key");
        }else {
            session.removeAttribute("key");
        }

        List<ChatListDTO> clist = cdao.chatList( key, mdto.getUserid(), mdto.getUserid());
        result.put("loginUser", mdto.getUserid());
        result.put("chatList", clist);
        return result;
    }

    public List<ChatingDTO> getChating(int lseq) {
        List<ChatingDTO> chatingList = cdao.getChating(lseq); // cdao는 ChatingDAO 인스턴스일 것으로 가정합니다.
        return chatingList;

    }

    public HashMap<String, Object> getChatList(int lseq, HttpServletRequest request ) {
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        MemberDTO mdto = (MemberDTO)session.getAttribute("login");
        List<ChatListDTO> chatList = cdao.getChatList(lseq);
        result.put("loginUser", mdto.getUserid());
        result.put("chatList", chatList);
        return result;

    }


}
