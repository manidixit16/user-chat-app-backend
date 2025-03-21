package com.chatapp.model;

import jakarta.persistence.*;

@Entity
public class UnreadMessage {

    @EmbeddedId
    private UnreadMessageId id; // Composite key (chatId + userId)

    private int unreadCount; // Number of unread messages

    // Default constructor (required by JPA)
    public UnreadMessage() {}

    // Parameterized constructor
    public UnreadMessage(Long chatId, Long userId, int unreadCount) {
        this.id = new UnreadMessageId(chatId, userId);
        this.unreadCount = unreadCount;
    }

    // Getters and Setters
    public UnreadMessageId getId() {
        return id;
    }

    public void setId(UnreadMessageId id) {
        this.id = id;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}