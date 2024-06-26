package com.himedia.phonetail_spring.controller;

import com.himedia.phonetail_spring.service.ProductService;
import com.himedia.phonetail_spring.service.WantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WantController {

    @Autowired
    private WantService ws;

    @Autowired
    private ProductService ps;

    @PostMapping("/wantToggle")
    @ResponseBody
    public HashMap<String, Object> wantToggle(
            @RequestParam("pseq")Integer pseq, @RequestParam("userid")String userid) {
        HashMap<String,Object> result = new HashMap<>();
        System.out.println(pseq);
        int wseq = ws.checkWant(pseq, userid);
        if(wseq==0){
            ws.insertWant(pseq, userid);
            ps.plusWantcount(pseq);
            result.put("STATUS", "Y");
            result.put("message", "찜 성공");
        } else{
            ws.deleteWant(wseq);
            ps.minusWantcount(pseq);
            result.put("STATUS", "N");
            result.put("message", "찜 취소");
        }
        return result;

    }



}
