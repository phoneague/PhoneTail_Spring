package com.himedia.phonetail_spring.service;

import com.himedia.phonetail_spring.dao.*;
import com.himedia.phonetail_spring.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MypageService {
    @Autowired
    IQuestionDAO qdao;
    @Autowired
    IReportDAO rdao;
    @Autowired
    IProductDAO pdao;
    @Autowired
    IWantDAO wdao;
    @Autowired
    IMemberDAO mdao;
    @Autowired
    IChatDAO cdao;

    public HashMap<String, Object> getMyQnaList(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        MemberDTO mdto = (MemberDTO) session.getAttribute("login");
        String myid = mdto.getUserid();
        // paging 객체 작업
        int page =1;
        if(request.getParameter("page")!=null){
            page=Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page",page);
        }else if(session.getAttribute("page")!=null){
            page = (Integer)session.getAttribute("page");
        }else{
            session.removeAttribute("page");
        }
        // key
        String key="";
        if(request.getParameter("key")!=null){
            key=request.getParameter("key");
            session.setAttribute("key",key);
        }else if(session.getAttribute("key")!=null){
            key = (String)session.getAttribute("key");
        }else{
            session.removeAttribute("key");
        }
        Paging paging = new Paging();
        paging.setPage(page);
        int count = qdao.getMyAllCount("question",myid,"title",key);
        paging.setTotalCount(count);
        paging.calPaing();
        paging.setStartNum(paging.getStartNum()-1);
        List<QuestionDTO> questionList = qdao.getMyQnaList(paging,key,myid);
        result.put("questionList",questionList);
        result.put("paging",paging);
        result.put("key",key);
        return result;
    }

    public HashMap<String, Object> getMyReportList(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        MemberDTO mdto = (MemberDTO) session.getAttribute("login");
        String myid = mdto.getUserid();
        // paging 객체 작업
        int page =1;
        if(request.getParameter("page")!=null){
            page=Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page",page);
        }else if(session.getAttribute("page")!=null){
            page = (Integer)session.getAttribute("page");
        }else{
            session.removeAttribute("page");
        }
        Paging paging = new Paging();
        paging.setPage(page);
        int count = rdao.getMyAllCount("report",myid);
        paging.setTotalCount(count);
        paging.calPaing();
        paging.setStartNum(paging.getStartNum()-1);
        List<ReportDTO> reportList = rdao.getMyReportList(paging,myid);
        result.put("reportList",reportList);
        result.put("paging",paging);

        return result;
    }

    public HashMap<String, Object> getMyProductList(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        MemberDTO mdto = (MemberDTO) session.getAttribute("login");
        String myid = mdto.getUserid();
        // paging 객체 작업
        int page =1;
        if(request.getParameter("page")!=null){
            page=Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page",page);
        }else if(session.getAttribute("page")!=null){
            page = (Integer)session.getAttribute("page");
        }else{
            session.removeAttribute("page");
        }
        // key
        String key="";
        if(request.getParameter("key")!=null){
            key=request.getParameter("key");
            session.setAttribute("key",key);
        }else if(session.getAttribute("key")!=null){
            key = (String)session.getAttribute("key");
        }else{
            session.removeAttribute("key");
        }
        Paging paging = new Paging();
        paging.setPage(page);
        int count = pdao.getMyAllCount("product","userid",myid);
        paging.setTotalCount(count);
        paging.calPaing();
        paging.setStartNum(paging.getStartNum()-1);
        List<ProductDTO> productList = pdao.getMyProductList(paging,myid);
        result.put("productList",productList);
        result.put("paging",paging);
        return result;
    }

    public HashMap<String, Object> getMyWantList(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        MemberDTO mdto = (MemberDTO) session.getAttribute("login");
        String myid = mdto.getUserid();
        // paging 객체 작업
        int page =1;
        if(request.getParameter("page")!=null){
            page=Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page",page);
        }else if(session.getAttribute("page")!=null){
            page = (Integer)session.getAttribute("page");
        }else{
            session.removeAttribute("page");
        }

        Paging paging = new Paging();
        paging.setPage(page);
        int count = pdao.getMyAllCount("wantlist_view","wuserid", myid);
       // int count = wdao.getMyAllCount("want",myid,key);
//        int count = qdao.getMyAllCount("question",myid,"title",key);
        paging.setTotalCount(count);
        paging.calPaing();
        paging.setStartNum(paging.getStartNum()-1);

        List<ProductDTO> productList = pdao.myWantProductList(paging,myid);
        //List<QuestionDTO> questionList = qdao.getMyQnaList(paging,key,myid);

        Map<String, String> userStates = new HashMap<>();

        for(ProductDTO productDTO : productList){
            MemberDTO member = mdao.getMember(productDTO.getUserid());
            if(member!=null){
                userStates.put(member.getUserid(),member.getUserstate());
            }
        }
        Map<Integer,Integer> productChatList = new HashMap<>();
        for(ProductDTO productDTO : productList){
            int chatCount = cdao.getProductChatList(productDTO.getPseq());
            if(chatCount!=0){
                productChatList.put(productDTO.getPseq(),chatCount);
            }
        }
        result.put("productList",productList);
        result.put("paging",paging);
        result.put("userStates",userStates);
        result.put("productChatList",productChatList);
        return result;
    }
}
