package com.himedia.phonetail_spring.dao;

import com.himedia.phonetail_spring.dto.ChatListDTO;
import com.himedia.phonetail_spring.dto.ChatingDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IChatDAO {
    List<ChatListDTO> chatList(String key, String userid1, String userid2);
    ChatingDTO getChating(int lseq);
    List<ChatListDTO> getChatList(int lseq);
}
