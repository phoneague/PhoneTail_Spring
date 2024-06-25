package com.himedia.phonetail_spring.controller;

import com.google.gson.Gson;
import com.himedia.phonetail_spring.dao.IMemberDAO;
import com.himedia.phonetail_spring.dto.KakaoProfile;
import com.himedia.phonetail_spring.dto.MemberDTO;
import com.himedia.phonetail_spring.dto.OAuthToken;
import com.himedia.phonetail_spring.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class MemberController {

    @Autowired
    MemberService ms;

    @PostMapping("/login")
    public String login(HttpServletRequest request, @ModelAttribute("dto") MemberDTO dto, Model model) {
        String userid = request.getParameter("userid");
        String pwd = request.getParameter("pwd");

        MemberDTO mdto = ms.getMember(userid);

        String url = "member/loginForm";

        if (mdto == null)
            request.setAttribute("message", "아이디가 없습니다");
        else if (!mdto.getPwd().equals(pwd))
            request.setAttribute("message", "패스워드가 틀립니다");
        else if (mdto.getUserstate().equals("N"))
            request.setAttribute("message", "해당 계정은 휴면상태이거나 탈퇴상태입니다. 관리자에게 문의하세요");
        else if (mdto.getPwd().equals(pwd)) {
            url = "redirect:/";
            HttpSession session = request.getSession();
            session.setAttribute("login", mdto);
        } else {
            request.setAttribute("message", "관리자에게 문의하세요");
        }
        return url;
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "member/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "member/joinForm";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("login");
        return "redirect:/";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute("dto") @Valid MemberDTO mdto, BindingResult result,
                       @RequestParam(value = "reid", required = false) String reid,
                       @RequestParam(value = "pwdCheck", required = false) String pwdCheck, Model model) {
        String url = "member/joinForm";
        model.addAttribute("reid", reid);
        if (result.getFieldError("userid") != null)
            model.addAttribute("message", result.getFieldError("userid").getDefaultMessage());
        else if (result.getFieldError("pwd") != null)
            model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
        else if (result.getFieldError("name") != null)
            model.addAttribute("message", result.getFieldError("name").getDefaultMessage());
        else if (result.getFieldError("phone") != null)
            model.addAttribute("message", result.getFieldError("phone").getDefaultMessage());
        else if (result.getFieldError("email") != null)
            model.addAttribute("message", result.getFieldError("email").getDefaultMessage());
        else if (reid == null || (!reid.equals(mdto.getUserid())))
            model.addAttribute("message", "아이디 중복체크를 완료하세요");
        else if (pwdCheck == null || !pwdCheck.equals(mdto.getPwd()))
            model.addAttribute("message", "비밀번호 확인이 일치하지 않습니다");
        else {
            url = "member/loginForm";
            model.addAttribute("message", "회원가입이 완료되었습니다. 로그인하세요");
            ms.insertMember(mdto);
        }
        return url;
    }

    @GetMapping("/kakaostart")
    public @ResponseBody String kakaostart() {
        String a = "<script type='text/javascript'>"
                + "location.href='https://kauth.kakao.com/oauth/authorize?"
                + "client_id=4a1c4e85369e717805bad43ac3f2fe8e"
                + "&redirect_uri=http://localhost:8070/kakaoLogin"
                + "&response_type=code'"
                + "</script>";
        return a;
    }

    @GetMapping("/kakaoLogin")
    public String login(HttpServletRequest request) throws IOException {
        String code = request.getParameter("code");
        System.out.println(code);  // 토큰 출력

        // 실제 User info 를 요청할 url 과 전달인수 설정
        String endpoint = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(endpoint);  //import java.net.URL;   예외처리  add throws 로 처리
        String bodyData = "grant_type=authorization_code";
        bodyData += "&client_id=4a1c4e85369e717805bad43ac3f2fe8e";
        bodyData += "&redirect_uri=http://localhost:8070/kakaoLogin";
        bodyData += "&code=" + code;

        //Stream 연결  // import - java.net.HttpURLConnection;
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //http header 값 넣기(요청 내용에 헤더 추가)
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        conn.setDoOutput(true);

        //인증절차를 완료하고 User info 요청을 위한 정보를 요청 및 수신합니다
        BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(conn.getOutputStream(), "UTF-8")
        );
        bw.write(bodyData);
        bw.flush();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "UTF-8")
        );

        String input = "";
        StringBuilder sb = new StringBuilder();  // 조각난 String 을 조립하기위한 객체
        while ((input = br.readLine()) != null) {
            sb.append(input);
            System.out.println(input);
        }

        // 사용자가 실제로 동의한 항목들만 요청하고 받을 수 있도록 권한을 갖고 있는 새로운 토큰으로 구성
        // {"access_token":"tEbj6Ck2JLTV65c8BscVS-3h0SCluSHnAAAAAQo9dJgAAAGQLmafSwGXonZVdqHq","token_type":"bearer","refresh_token":"CyWRQ79bVdveeRjJVhFdFDxMGVL7BpjSAAAAAgo9dJgAAAGQLmafRwGXonZVdqHq","expires_in":21599,"scope":"account_email profile_nickname","refresh_token_expires_in":5183999}

        // 수신내용을  GSon 으로 변경(파싱)하고 준비된 객체에 옮겨 담습니다
        Gson gson = new Gson();
        OAuthToken oAuthToken = gson.fromJson(sb.toString(), OAuthToken.class);
        // oAuthToken <- sb{"access_token":"HCqlu2GvtRSqZxYLVfvI_hS5UWBqR ....
        // sb 내용을 항목에 맞춰서  OAuthToken 객체에 옮겨 담습니다


        // 실제 정보 요청 및 수신
        String endpoint2 = "https://kapi.kakao.com/v2/user/me";
        URL url2 = new URL(endpoint2);
        // import java.net.HttpURLConnection
        HttpsURLConnection conn2 = (HttpsURLConnection) url2.openConnection();
        //header 값 넣기
        conn2.setRequestProperty("Authorization", "Bearer " + oAuthToken.getAccess_token());
        conn2.setDoOutput(true);
        // UserInfo 수신
        BufferedReader br2 = new BufferedReader(
                new InputStreamReader(conn2.getInputStream(), "UTF-8")
        );
        String input2 = "";
        StringBuilder sb2 = new StringBuilder();
        while ((input2 = br2.readLine()) != null) {
            sb2.append(input2);
            System.out.println(input2);
        }
        // 수신내용
        // {"id":2844973154, "connected_at":"2023-06-15T12:52:20Z", "properties":{"nickname":"강희준"}, "kakao_account":{"profile_nickname_needs_agreement":false, "profile":{"nickname":"강희준","is_default_nickname":false}, "has_email":true, "email_needs_agreement":false,"is_email_valid":true,"is_email_verified":true,"email":"heejoon73@daum.net"}}

        Gson gson2 = new Gson();
        KakaoProfile kakaoProfile = gson2.fromJson(sb2.toString(), KakaoProfile.class);

        System.out.println(kakaoProfile.getId());
        KakaoProfile.KakaoAccount ac = kakaoProfile.getAccount();
        System.out.println(ac.getEmail());
        KakaoProfile.KakaoAccount.Profile pf = ac.getProfile();
        System.out.println(pf.getNickname());

        MemberDTO mdto = ms.getMember(kakaoProfile.getId());
        if (mdto == null) {
            mdto = new MemberDTO();
            mdto.setUserid(kakaoProfile.getId());
            mdto.setEmail(ac.getEmail());
            // mdto.setEmail( "kakao" );
            mdto.setName(pf.getNickname());
            mdto.setProvider("kakao");
            mdto.setPwd("kakao");
            mdto.setPhone("");
            ms.insertMember(mdto);
        }
        HttpSession session = request.getSession();
        session.setAttribute("login", mdto);
        return "redirect:main";

    }

    @GetMapping("/idcheckForm")
    public ModelAndView idcheckForm(@RequestParam("userid") String userid) {
        ModelAndView mav = new ModelAndView();
        MemberDTO mdto = ms.getMember(userid);
        if (mdto == null)
            mav.addObject("result", -1);
        else
            mav.addObject("result", 1);
        mav.addObject("userid", userid);
        mav.setViewName("member/idcheck");
        return mav;
    }

    @PostMapping("/updateMemberForm")
    public ModelAndView updateMemberForm(@RequestParam("userid") String userid, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        MemberDTO mdto = (MemberDTO) session.getAttribute("login");
        mav.setViewName("member/updateMemberForm");
        if (mdto == null) {
            request.setAttribute("message", "회원정보 수정을 하려면 먼저 로그인해야 합니다");

        } 
        return mav;
    }

    @PostMapping("/updateMember")
    public ModelAndView updateMember(@ModelAttribute("dto") MemberDTO mdto, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/");

        // 직접 request에서 userid 가져오기
        String userid = request.getParameter("userid");
        mdto.setUserid(userid);

        int result = ms.updateMember(mdto);

        if (result == 1) {
            HttpSession session = request.getSession();
            session.setAttribute("login", mdto);
        }

        return mav;
    }

    @GetMapping("/deleteMember")
    public ModelAndView deleteMember(@RequestParam("userid") String userid, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/"); // 회원 삭제 후 메인 페이지로 리다이렉트

        // 회원 삭제 수행
        int result = ms.deleteMember(userid);

        if (result == 1) {
            // 회원 삭제 성공 시 세션 무효화
            session.invalidate();
            // 리다이렉트 시 메시지 전달
            mav.addObject("message", "회원정보가 정상적으로 삭제되었습니다");
        } else {
            // 회원 삭제 실패 시 메시지 전달
            mav.addObject("message", "회원탈퇴가 정상적으로 완료되지 못했습니다. 다음에 다시 시도하세요");
        }

        return mav;
    }




}
