package com.chatapp.controller;

import com.chatapp.model.Message;
import com.chatapp.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // For WebSocket messaging

    /**
     * WebSocket endpoint to send a message.
     *
     * @param message The message to send.
     * @return The saved message.
     */
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/messages")
    public Message sendMessage(Message message) {
        // Save the message to the database
        Message savedMessage = messageService.sendMessage(
            message.getChatId(),
            message.getSender().getId(),
            message.getContent()
        );

        // Broadcast the message to all subscribers of /topic/messages
        messagingTemplate.convertAndSend("/topic/messages", savedMessage);

        return savedMessage;
    }

    /**
     * REST endpoint to retrieve messages in a chat.
     *
     * @param chatId The ID of the chat.
     * @param userId The ID of the user.
     * @return The list of messages in the chat.
     */
    @GetMapping("/{chatId}/messages")
    public List<Message> getMessages(@PathVariable Long chatId, @RequestParam Long userId) {
        return messageService.getMessages(chatId, userId);
    }
}