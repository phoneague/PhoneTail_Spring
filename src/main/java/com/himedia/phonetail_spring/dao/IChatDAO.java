package com.himedia.phonetail_spring.dao;

import com.himedia.phonetail_spring.dto.ChatListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IChatDAO {
    List<ChatListDTO> getChatList(String key, String userid1, String userid2);
}
