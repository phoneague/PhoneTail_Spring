package com.himedia.phonetail_spring.controller;

import com.himedia.phonetail_spring.dto.MemberDTO;
import com.himedia.phonetail_spring.dto.Paging;
import com.himedia.phonetail_spring.dto.QuestionDTO;
import com.himedia.phonetail_spring.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    CustomerService cs;

    @GetMapping("/qnaList")
    public String qnaList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "key", defaultValue = "") String key,
            HttpSession session,
            Model model) {

        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/loginForm";
        }

        if (!key.isEmpty()) {
            session.setAttribute("key", key);
        } else if (session.getAttribute("key") != null) {
            key = (String) session.getAttribute("key");
        } else {
            session.removeAttribute("key");
        }

        Paging paging = new Paging();
        paging.setPage(page);

        int count = cs.getAllCount("question", "userid", key);
        paging.setTotalCount(count);

        List<QuestionDTO> questionList = cs.getAllQuestions(paging, key);

        model.addAttribute("paging", paging);
        model.addAttribute("questionList", questionList);

        return "customer/qnaList";
    }

    @GetMapping("qnaView")
    public String qnaView(@RequestParam("qseq")int qseq, Model model) {
        cs.updateReadCount(qseq);
        model.addAttribute("QuestionDTO", cs.getQna(qseq));
        return "customer/qnaView";
    }

    @GetMapping("/writeQnaForm")
    public String writeQnaForm(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        MemberDTO loginUser = (MemberDTO) session.getAttribute("login");

        if (loginUser == null) {
            return "redirect:/loginForm";
        } else {
            model.addAttribute("loginUser", loginUser.getUserid());
            return "customer/writeQnaForm";
        }
    }


    @PostMapping("/writeQna")
    public String writeQna(
            HttpServletRequest request,
            @RequestParam(value = "secret", required = false, defaultValue = "N") boolean secret,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            Model model) {

        HttpSession session = request.getSession();
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/loginForm";
        }

        if (title == null || title.isEmpty()) {
            model.addAttribute("message", "제목을 입력하세요");
            return "customer/writeQnaForm";
        } else if (content == null || content.isEmpty()) {
            model.addAttribute("message", "내용을 입력하세요");
            return "customer/writeQnaForm";
        } else {
            QuestionDTO qdto = new QuestionDTO();
            qdto.setUserid(loginUser.getUserid());
            qdto.setTitle(title);
            qdto.setContent(content);
            qdto.setSecret(secret);
            cs.insertQna(qdto);
            return "redirect:/qnaList";
        }
    }

    @PostMapping("/deleteQna")
    public String deleteQna(@RequestParam("qseq") int qseq) {
        cs.deleteQna(qseq);
        return "redirect:/qnaList";
    }
}
