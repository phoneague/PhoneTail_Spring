package com.himedia.phonetail_spring.controller;

import com.himedia.phonetail_spring.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {
    @Autowired
    CustomerService cs;

    @GetMapping("qnaView")
    public String qnaView(@RequestParam("qseq")int qseq, Model model) {
        cs.updateReadCount(qseq);
        model.addAttribute("QuestionDTO", cs.getQna(qseq));
        return "customer/qnaView";
    }
}
