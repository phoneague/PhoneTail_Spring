package com.himedia.phonetail_spring.controller;

import com.himedia.phonetail_spring.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @Autowired
    ChatService cs;

    
}
