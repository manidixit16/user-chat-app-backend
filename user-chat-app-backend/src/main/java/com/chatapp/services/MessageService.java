package com.chatapp.services;

import com.chatapp.model.Chat;
import com.chatapp.model.Message;
import com.chatapp.model.User;
import com.chatapp.repository.ChatRepository;
import com.chatapp.repository.MessageRepository;
import com.chatapp.repository.UnreadMessageRepository;
import com.chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UnreadMessageRepository unreadMessageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Sends a message in a chat.
     *
     * @param chatId   The ID of the chat.
     * @param senderId The ID of the sender.
     * @param content  The content of the message.
     * @return The saved message.
     */
    @Transactional
    public Message sendMessage(Long chatId, Long senderId, String content) {
        // Fetch the User object for the sender
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + senderId));

        // Create and save the message
        Message message = new Message();
        message.setChatId(chatId);
        message.setSender(sender); // Set the User object as the sender
        message.setContent(content);
        message.setRead(false); // Mark the message as unread initially
        messageRepository.save(message);

        // Increment unread count for the recipient
        Long recipientId = getRecipientId(chatId, senderId); // Helper method to get recipient ID
        unreadMessageRepository.incrementUnreadCount(chatId, recipientId);

        return message;
    }

    /**
     * Retrieves messages in a chat and marks them as read for the user.
     *
     * @param chatId The ID of the chat.
     * @param userId The ID of the user.
     * @return The list of messages in the chat.
     */
    @Transactional
    public List<Message> getMessages(Long chatId, Long userId) {
        // Fetch messages in the chat
        List<Message> messages = messageRepository.findByChatId(chatId);

        // Mark messages as read
        messageRepository.markMessagesAsRead(chatId, userId);

        // Reset unread count for this chat and user
        unreadMessageRepository.resetUnreadCount(chatId, userId);

        return messages;
    }

    /**
     * Helper method to get the recipient ID in a chat.
     *
     * @param chatId   The ID of the chat.
     * @param senderId The ID of the sender.
     * @return The ID of the recipient.
     */
    private Long getRecipientId(Long chatId, Long senderId) {
        // Fetch chat and determine recipient ID
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found with ID: " + chatId));
        return chat.getUser1Id().equals(senderId) ? chat.getUser2Id() : chat.getUser1Id();
    }
}