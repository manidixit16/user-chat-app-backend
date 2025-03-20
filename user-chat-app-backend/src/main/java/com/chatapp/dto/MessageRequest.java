package com.chatapp.dto;

public class MessageRequest {
    
    private Long senderId;
    private String content;

    // Getters and Setters
    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
