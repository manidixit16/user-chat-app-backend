package com.chatapp.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UnreadMessageId implements Serializable {

    private Long chatId; // ID of the chat
    private Long userId; // ID of the user

    // Default constructor (required by JPA)
    public UnreadMessageId() {}

    // Parameterized constructor
    public UnreadMessageId(Long chatId, Long userId) {
        this.chatId = chatId;
        this.userId = userId;
    }

    // Getters and Setters
    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Override equals and hashCode for composite key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnreadMessageId that = (UnreadMessageId) o;
        return Objects.equals(chatId, that.chatId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, userId);
    }
}