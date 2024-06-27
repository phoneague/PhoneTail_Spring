package com.himedia.phonetail_spring.dao;

import com.himedia.phonetail_spring.dto.ChatListDTO;
import com.himedia.phonetail_spring.dto.ChatingDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IChatDAO {
    List<ChatListDTO> chatList(String key, String userid1, String userid2);
    List<ChatingDTO> getChating(int lseq);
    List<ChatListDTO> getChatList(int lseq);
    void insertChat(ChatingDTO chatingdto);
    ChatListDTO filter(int pseq, String userid);
    void insertChatList(ChatListDTO cdto);
    int getProductChatList(int pseq);
    void Chatingroom(int lseq , String userid  );
    int getlseq();
}
