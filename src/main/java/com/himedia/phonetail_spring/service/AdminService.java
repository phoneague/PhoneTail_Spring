package com.himedia.phonetail_spring.service;

import com.himedia.phonetail_spring.dao.IAdminDAO;
import com.himedia.phonetail_spring.dto.AdminDTO;
import com.himedia.phonetail_spring.dto.Paging;
import com.himedia.phonetail_spring.dto.QuestionDTO;
import com.himedia.phonetail_spring.dto.ReportDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    IAdminDAO adao;

    public AdminDTO getAdmin(String adminid) {
        return adao.getAdmin(adminid);
    }

    public HashMap<String, Object> getAdminReportList(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
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
        int count = adao.getAllCount("report","userid",key);
        paging.setTotalCount(count);
        paging.calPaing();
        paging.setStartNum(paging.getStartNum()-1);
        List<ReportDTO> reportList = adao.getReportList(paging,key);
        result.put("reportList",reportList);
        result.put("paging",paging);
        result.put("key",key);
        return result;
    }

    public HashMap<String, Object> getAdminQnaList(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
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
        int count = adao.getAllCount("question","userid",key);
        paging.setTotalCount(count);
        paging.calPaing();
        paging.setStartNum(paging.getStartNum()-1);
        List<QuestionDTO> qnaList = adao.getQnaList(paging,key);
        result.put("qnaList",qnaList);
        result.put("paging",paging);
        result.put("key",key);

        return result;
    }
}
