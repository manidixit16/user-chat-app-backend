package com.chatapp.services;

import com.chatapp.model.Chat;
import com.chatapp.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public Chat createChat(Long user1Id, Long user2Id) {
        Chat chat = new Chat();
        chat.setUser1Id(user1Id);
        chat.setUser2Id(user2Id);
        return chatRepository.save(chat);
    }

    public void deleteChat(Long chatId) {
        chatRepository.deleteById(chatId);
    }
}