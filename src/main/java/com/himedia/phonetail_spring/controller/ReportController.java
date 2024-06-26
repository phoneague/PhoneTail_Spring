package com.himedia.phonetail_spring.controller;

import com.himedia.phonetail_spring.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class ReportController {
    @Autowired
    ReportService rs;

    @PostMapping("/report")
    @ResponseBody       //자신을 호출하는 곳으로 '리턴되는 데이터'를 갖고 이동하여 페이지에 표시하라는 뜻
    public HashMap<String, Object> reportForm(
            @RequestParam("pseq")int pseq,@RequestParam("userid")String userid, @RequestParam("retype")int retype, @RequestParam("recontent") String recontent
    ) {
        HashMap<String,Object> result = new HashMap<>();
        if(retype==-1){
            result.put("status",1);
        }else if(recontent==null||(recontent.replaceAll(" ","")).equals("")){
            result.put("status",2);
        }else{
            rs.insertReport(pseq,userid,retype,recontent);
            result.put("status",3);
        }
        return result;
    }
}
