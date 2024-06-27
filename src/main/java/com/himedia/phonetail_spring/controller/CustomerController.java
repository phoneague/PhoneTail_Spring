package com.himedia.phonetail_spring.controller;

import com.himedia.phonetail_spring.dto.MemberDTO;
import com.himedia.phonetail_spring.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class CustomerController {
    @Autowired
    CustomerService cs;

    @GetMapping("/qnaList")
    public ModelAndView qnaList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "key", defaultValue = "") String key,
            HttpServletRequest request,
            Model model) {

        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();

        String url = "customer/qnaList";
        MemberDTO mdto = (MemberDTO) session.getAttribute("login");

        MemberDTO loginUser = (MemberDTO) session.getAttribute("login");
        if (mdto == null) {
            mav.setViewName("member/loginForm");
        } else {
            HashMap<String, Object> result = cs.getQuestionList(request);
            mav.addObject("questionList", result.get("questionList"));
            mav.addObject("paging", result.get("paging"));
            mav.addObject("key", result.get("key"));
            mav.setViewName("customer/qnaList");
        }
        return mav;
    }

    @GetMapping("/qnaView")
    public String qnaView(@RequestParam("qseq") int qseq, Model model) {
        cs.updateReadCount(qseq);
        model.addAttribute("QuestionDTO", cs.getQna(qseq));
        return "customer/qnaView";
    }


    @GetMapping ("/writeQnaForm")
    public String writeQnaForm(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        MemberDTO login = (MemberDTO) session.getAttribute("login");

        if (login == null) {
            return "redirect:/loginForm";
        } else {
            return "customer/writeQnaForm";
        }
    }


    @PostMapping("/writeQna")
    public String writeQna(
            HttpServletRequest request,
            @RequestParam(value = "secret", required = false, defaultValue = "false") String secretStr,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            Model model) {

        HttpSession session = request.getSession();
        MemberDTO login = (MemberDTO) session.getAttribute("login");
        String url = "customer/writeQnaForm";

        if (login == null) {
            return "redirect:/loginForm";
        } else {
            if (title == null || title.isEmpty()) {
                model.addAttribute("message", "제목을 입력하세요");
                return url;
            } else if (content == null || content.isEmpty()) {
                model.addAttribute("message", "내용을 입력하세요");
                return url;
            } else {
                boolean secret = Boolean.parseBoolean(secretStr); // 문자열을 boolean으로 변환
                url = "redirect:/qnaList";
                cs.writeQna(login.getUserid(), title, content, secret);
            }
        }
        return url;
    }


    @GetMapping("/deleteQna")
    public String deleteQna(@RequestParam("qseq") int qseq) {
        cs.deleteQna(qseq);
        return "redirect:/qnaList";
    }
}
