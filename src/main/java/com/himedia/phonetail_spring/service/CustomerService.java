package com.himedia.phonetail_spring.service;

import com.himedia.phonetail_spring.dao.IQuestionDAO;
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
public class CustomerService {
    @Autowired
    IQuestionDAO qdao;

    public void updateReadCount(int qseq) {
        qdao.updateReadCount(qseq);
    }

    public Object getQna(int qseq) {
        return qdao.getQna(qseq);
    }

    public void deleteQna(int qseq) {
        qdao.deleteQna(qseq);
    }

    public HashMap<String, Object> getQuestionList(HttpServletRequest request) {

        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();
        // page 구분
        int page =1;
        if(request.getParameter("page")!=null){
            page=Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page",page);
        }else if(session.getAttribute("page")!=null){
            page = (Integer)session.getAttribute("page");
        }else{
            session.removeAttribute("page");
        }

        // 검색어 구분(model)
        String key="";
        if(request.getParameter("key")!=null){
            key=request.getParameter("key");
            session.setAttribute("key",key);
        }else if(session.getAttribute("key")!=null){
            key = (String)session.getAttribute("key");
        }else{
            session.removeAttribute("key");
        }

        // paging을 위한 상품전체 갯수 세기 + 현재 페이지 표시
        Paging paging = new Paging();
        paging.setPage(page);
        int count = qdao.getAllCount("userid", key);
        paging.setTotalCount(count);
        paging.calPaing();
        paging.setStartNum(paging.getStartNum()-1);
        List<QuestionDTO> questionList = qdao.getAllQuestions(paging, key, "userid");
        result.put("questionList", questionList);
        result.put("paging", paging);
        result.put("key", key);
        return result;

    }


    public void writeQna(String userid, String title, String content, boolean secret) {
        qdao.writeQna(userid, title, content, secret);
    }
}
