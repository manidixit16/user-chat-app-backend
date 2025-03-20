package com.chatapp.controller;

import com.chatapp.dto.ChatRequest;  // Import the ChatRequest class
import com.chatapp.services.*;  // Import the ChatService class
import com.chatapp.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public ResponseEntity<?> createChat(@RequestBody ChatRequest chatRequest) {
        Chat chat = chatService.createChat(chatRequest.getUser1Id(), chatRequest.getUser2Id());
        return ResponseEntity.ok(chat);
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<?> deleteChat(@PathVariable Long chatId) {
        chatService.deleteChat(chatId);
        return ResponseEntity.ok("Chat deleted successfully");
    }
}