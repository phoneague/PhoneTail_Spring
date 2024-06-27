package com.himedia.phonetail_spring.service;

import com.himedia.phonetail_spring.dao.IAdminDAO;
import com.himedia.phonetail_spring.dao.IMemberDAO;
import com.himedia.phonetail_spring.dao.IReportDAO;
import com.himedia.phonetail_spring.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    IAdminDAO adao;

    @Autowired
    IMemberDAO mdao;

    @Autowired
    IReportDAO rdao;

    public AdminDTO getAdmin(String adminid) {
        return adao.getAdmin(adminid);
    }

    public HashMap<String, Object> getAdminReportList(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        // paging 객체 작업
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page", page);
        } else if (session.getAttribute("page") != null) {
            page = (Integer) session.getAttribute("page");
        } else {
            session.removeAttribute("page");
        }
        // key
        String key = "";
        if (request.getParameter("key") != null) {
            key = request.getParameter("key");
            session.setAttribute("key", key);
        } else if (session.getAttribute("key") != null) {
            key = (String) session.getAttribute("key");
        } else {
            session.removeAttribute("key");
        }
        Paging paging = new Paging();
        paging.setPage(page);
        int count = adao.getAllCount("report", "userid", key);
        paging.setTotalCount(count);
        paging.calPaing();
        paging.setStartNum(paging.getStartNum() - 1);
        List<ReportDTO> reportList = adao.getReportList(paging, key);
        result.put("reportList", reportList);
        result.put("paging", paging);
        result.put("key", key);
        return result;
    }

    public HashMap<String, Object> getAdminQnaList(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        // paging 객체 작업
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page", page);
        } else if (session.getAttribute("page") != null) {
            page = (Integer) session.getAttribute("page");
        } else {
            session.removeAttribute("page");
        }
        // key
        String key = "";
        if (request.getParameter("key") != null) {
            key = request.getParameter("key");
            session.setAttribute("key", key);
        } else if (session.getAttribute("key") != null) {
            key = (String) session.getAttribute("key");
        } else {
            session.removeAttribute("key");
        }
        Paging paging = new Paging();
        paging.setPage(page);
        int count = adao.getAllCount("question", "userid", key);
        paging.setTotalCount(count);
        paging.calPaing();
        paging.setStartNum(paging.getStartNum() - 1);
        List<QuestionDTO> qnaList = adao.getQnaList(paging, key);
        result.put("qnaList", qnaList);
        result.put("paging", paging);
        result.put("key", key);

        return result;
    }

    public HashMap<String, Object> getAdminMemberList(HttpServletRequest request, @RequestParam("key") String key, @RequestParam("userstate") String userstate) {
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        // paging 객체 작업
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page", page);
        } else if (session.getAttribute("page") != null) {
            page = (Integer) session.getAttribute("page");
        } else {
            session.removeAttribute("page");
        }
        // key
        if (request.getParameter("key") != null) {
            key = request.getParameter("key");
            session.setAttribute("key", key);
        } else if (session.getAttribute("key") != null) {
            key = (String) session.getAttribute("key");
        } else {
            session.removeAttribute("key");
        }

        if (request.getParameter("userstate") != null) {
            userstate = request.getParameter("userstate");
            session.setAttribute("userstate", userstate);
        } else if (session.getAttribute("userstate") != null) {
            userstate = (String) session.getAttribute("userstate");
        } else {
            userstate = "";
            session.removeAttribute("userstate");
        }
        Paging paging = new Paging();
        paging.setPage(page);
        int count = adao.getMemberAllCount("member", "name", key, userstate);
        /*int count = adao.getAllCount("member","name",key);*/
        paging.setTotalCount(count);
        paging.calPaing();
        paging.setStartNum(paging.getStartNum() - 1);
        List<MemberDTO> memberList = adao.getMemberList(paging, key, userstate);
        result.put("memberList", memberList);
        result.put("paging", paging);
        result.put("key", key);
        result.put("userstate", userstate);
        return result;
    }

    public void stateChangeBtoY(String userid) {
        mdao.stateChangeBtoY(userid);
    }

    public void stateChangeNtoY(String userid) {
        mdao.stateChangeNtoY(userid);
    }

    public void stateChangeYtoB(String userid) {
        mdao.stateChangeYtoB(userid);
    }



    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public int processReport(String newRestate, String oldRestate, int reseq, String pid) {
        if (newRestate.equals(oldRestate)) {
            return 0;
        } else {
            rdao.updateReport(newRestate, reseq);
            if (newRestate.equals("Y")) {
                mdao.stateChangeYtoB(pid);
            }
            return 1;
        }
    }

    public ReportDTO getReportView(int reseq) {
        return rdao.getReportView(reseq);
    }

}
