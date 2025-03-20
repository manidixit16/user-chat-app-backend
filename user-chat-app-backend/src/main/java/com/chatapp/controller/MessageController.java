package com.chatapp.controller;

import org.springframework.web.bind.annotation.RestController;
import com.chatapp.model.Message;
import com.chatapp.services.*;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import com.chatapp.dto.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/chats/{chatId}/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<?> sendMessage(@PathVariable Long chatId, @RequestBody MessageRequest messageRequest) {
        Message message = messageService.sendMessage(chatId, messageRequest.getSenderId(), messageRequest.getContent());
        return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<?> getMessages(@PathVariable Long chatId, @RequestParam Long userId) {
        List<Message> messages = messageService.getMessages(chatId, userId);
        return ResponseEntity.ok(messages);
    }
}