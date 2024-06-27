package com.himedia.phonetail_spring.controller;

import com.himedia.phonetail_spring.dto.AdminDTO;
import com.himedia.phonetail_spring.dto.MemberDTO;
import com.himedia.phonetail_spring.dto.QuestionDTO;
import com.himedia.phonetail_spring.service.AdminService;
import com.himedia.phonetail_spring.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class AdminController {

    @Autowired
    AdminService as;

    @GetMapping("/admin")
    public String admin(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        String url = "redirect:/adminReportList";
        AdminDTO adto = (AdminDTO) session.getAttribute("adminUser");
        if (adto == null) {
            url = "admin/adminLogin";
        } else {
            url = "redirect:/adminReportList";
        }
        return url;
    }

    @PostMapping("/adminLogin")
    public String adminLogin(@ModelAttribute("dto") @Valid AdminDTO admindto, BindingResult bindingResult, HttpServletRequest request, Model model) throws IOException {
        String url = "admin/adminLogin";
        if (bindingResult.getFieldError("adminid") != null) {
            model.addAttribute("message", "아이디를 입력하세요");
        } else if (bindingResult.getFieldError("pwd") != null) {
            model.addAttribute("message", "패스워드를 입력하세요");
        } else {
            AdminDTO adto = as.getAdmin(admindto.getAdminid());
            if (adto == null) {
                model.addAttribute("message", "아이디/패스워드를 확인하세요");
            } else if (!adto.getPwd().equals(admindto.getPwd())) {
                model.addAttribute("message", "아이디/패스워드를 확인하세요");
            } else if (adto.getPwd().equals(admindto.getPwd())) {
                HttpSession session = request.getSession();
                session.setAttribute("adminUser", adto);
                url = "redirect:/adminReportList?page=1&key=";
            }
        }
        return url;
    }

    @GetMapping("/adminLogout")
    public String adminLogout(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("adminUser");
        return "redirect:/";
    }

    @GetMapping("/adminReportList")
    public ModelAndView adminReportList(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("adminUser") == null) {
            mav.setViewName("admin/adminLogin");
        } else {
            HashMap<String, Object> result = as.getAdminReportList(request);
            mav.addObject("reportList", result.get("reportList"));
            mav.addObject("paging", result.get("paging"));
            mav.addObject("key", result.get("key"));
            mav.setViewName("admin/report/adminReportList");
        }
        return mav;
    }

    @GetMapping("/reportView")
    public String reportView(@RequestParam("reseq") int reseq, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String url = "admin/adminLogin";
        if (session.getAttribute("adminUser") == null) {
            url = "admin/adminLogin";
        } else {
            model.addAttribute("reportDTO", as.getReportView(reseq));
            url = "report/reportView";
        }
        return url;
    }

    @PostMapping("/processReport")
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ResponseBody
    public HashMap<String, Object> processReport(@RequestParam("newRestate") String newRestate, @RequestParam("oldRestate") String oldRestate, @RequestParam("reseq") int reseq, @RequestParam("pid") String pid) {
        HashMap<String, Object> result = new HashMap<>();
        int status = as.processReport(newRestate, oldRestate, reseq, pid);
        result.put("status", status);
        System.out.println("status : " + status);
        return result;
    }

    @GetMapping("/adminQnaList")
    public ModelAndView adminQnaList(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("adminUser") == null) {
            mav.setViewName("admin/adminLogin");
        } else {
            HashMap<String, Object> result = as.getAdminQnaList(request);
            mav.addObject("qnaList", result.get("qnaList"));
            mav.addObject("paging", result.get("paging"));
            mav.addObject("key", result.get("key"));
            mav.setViewName("admin/qna/adminQnaList");
        }
        return mav;
    }

    @GetMapping("/adminMemberList")
    public ModelAndView adminMemberList(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("adminUser") == null) {
            mav.setViewName("admin/adminLogin");
        } else {
            String key = request.getParameter("key");
            String userstate = request.getParameter("userstate");
            HashMap<String, Object> result = as.getAdminMemberList(request, key, userstate); // key와 userstate 파라미터 전달
            mav.addObject("memberList", result.get("memberList"));
            mav.addObject("paging", result.get("paging"));
            mav.addObject("key", key);
            mav.addObject("userstate", userstate);
            mav.setViewName("admin/member/adminMemberList");
        }
        return mav;
    }


    @GetMapping("/adminUserStateChangeBtoY")
    public ModelAndView adminUserStateChangeBtoY(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("adminUser") == null) {
            mav.setViewName("admin/adminLogin");
        } else {
            String[] userstate = request.getParameterValues("userstate");
            for (String userid : userstate) {
                as.stateChangeBtoY(userid);
            }
            mav.setViewName("admin/member/adminMemberList");
            return mav;
        }
        return mav;
    }

    @GetMapping("/adminUserStateChangeNtoY")
    public ModelAndView adminUserStateChangeNtoY(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("adminUser") == null) {
            mav.setViewName("admin/adminLogin");
        } else {
            String[] userstate = request.getParameterValues("userstate");
            for (String userid : userstate) {
                as.stateChangeNtoY(userid);
            }
            mav.setViewName("admin/member/adminMemberList");
            return mav;
        }
        return mav;
    }

    @GetMapping("/adminUserStateChangeYtoB")
    public ModelAndView adminUserStateChangeYtoB(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("adminUser") == null) {
            mav.setViewName("admin/adminLogin");
        } else {
            String[] userstate = request.getParameterValues("userstate");
            for (String userid : userstate) {
                as.stateChangeYtoB(userid);
            }
            mav.setViewName("admin/member/adminMemberList");
            return mav;
        }
        return mav;
    }

    @Autowired
    CustomerService cs;

    @GetMapping("/adminQnaReplyForm")
    public String adminQnaReplyForm(@RequestParam("qseq") int qseq, Model model) {
        QuestionDTO qdto = cs.getQna(qseq);
        model.addAttribute("QuestionDTO", qdto);
        return "admin/qna/adminQnaReplyForm";
    }

    @PostMapping("/adminQnaReply")
    public String adminQnaReply(
            @RequestParam("qseq") int qseq,
            @RequestParam("qreply") String qreply,
            HttpSession session) {

        AdminDTO adminUser = (AdminDTO) session.getAttribute("adminUser");

        if (adminUser == null) {
            return "redirect:/adminLogin";
        } else {
            cs.writeQnaReply(qseq, qreply);
            return "redirect:/adminQnaList";
        }
    }
}
