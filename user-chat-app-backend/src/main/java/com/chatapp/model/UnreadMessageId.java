package com.chatapp.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UnreadMessageId implements Serializable {

    private Long userId;
    private Long chatId;

    // Default constructor (required by JPA)
    public UnreadMessageId() {
    }

    // Parameterized constructor for easier object creation
    public UnreadMessageId(Long userId, Long chatId) {
        this.userId = userId;
        this.chatId = chatId;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    // Equals and HashCode methods (required for composite keys)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnreadMessageId that = (UnreadMessageId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(chatId, that.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, chatId);
    }

    // toString method for debugging and logging
    @Override
    public String toString() {
        return "UnreadMessageId{" +
                "userId=" + userId +
                ", chatId=" + chatId +
                '}';
    }
}