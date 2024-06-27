package com.himedia.phonetail_spring.service;

import com.himedia.phonetail_spring.dao.IChatDAO;
import com.himedia.phonetail_spring.dao.IProductDAO;
import com.himedia.phonetail_spring.dto.ChatListDTO;
import com.himedia.phonetail_spring.dto.ChatingDTO;
import com.himedia.phonetail_spring.dto.MemberDTO;
import com.himedia.phonetail_spring.dto.ProductDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    IChatDAO cdao;
    @Autowired
    IProductDAO pdao;


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
        result.put("lseq", lseq);
        return result;

    }


    public void insertChat(ChatingDTO chatingdto) {

        cdao.insertChat(chatingdto);
    }

    public boolean insertChatList(HttpSession session, int pseq) {
        MemberDTO mdto = (MemberDTO) session.getAttribute("login");
        ProductDTO pdto = pdao.getProduct(pseq);
        ChatListDTO fdto = cdao.filter(pdto.getPseq(), mdto.getUserid());
        System.out.println(fdto);
        if ( fdto == null ) {
            ChatListDTO cdto = new ChatListDTO();
            cdto.setBid(mdto.getUserid());
            cdto.setPseq(pseq);
            cdto.setPrice(pdto.getPrice());
            cdto.setModel(pdto.getModel());
            cdto.setSid(pdto.getUserid());
            cdto.setContent(null);
            java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
            cdto.setIndate(now);
            // ChatListDTO를 DB에 추가하는 DAO 호출
            cdao.insertChatList(cdto);
            int lseq = cdao.getlseq();
            cdao.Chatingroom(lseq, mdto.getUserid());
            return true;
        } else {
            return false;
        }
    }


    public ChatListDTO filter(int pseq, String userid) {
        return cdao.filter(pseq, userid);
    }


    public ChatListDTO getChatListByLseq(int lseq) {
        return cdao.getChatList(lseq).get(0); // MyBatis의 경우 단일 객체를 반환하므로 첫 번째 요소를 가져옴
    }

    public List<ChatingDTO> getChatingByLseq(int lseq) {
        return cdao.getChating(lseq);
    }
}
