package com.himedia.phonetail_spring.service;

import com.himedia.phonetail_spring.dao.IProductDAO;
import com.himedia.phonetail_spring.dao.IQuestionDAO;
import com.himedia.phonetail_spring.dao.IReportDAO;
import com.himedia.phonetail_spring.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MypageService {
    @Autowired
    IQuestionDAO qdao;
    @Autowired
    IReportDAO rdao;
    @Autowired
    IProductDAO pdao;


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
        Paging paging = new Paging();
        paging.setPage(page);
        int count = pdao.getMyAllCount("product",myid);
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
        int count = pdao.getMyAllCount("product",myid);
        paging.setTotalCount(count);
        paging.calPaing();
        paging.setStartNum(paging.getStartNum()-1);
        List<ProductDTO> wantList = pdao.myWantProductList(paging,myid);
        result.put("wantList",wantList);
        result.put("paging",paging);
        return result;
    }
}
