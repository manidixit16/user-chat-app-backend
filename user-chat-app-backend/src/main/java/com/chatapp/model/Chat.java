package com.chatapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user1_id", nullable = false)
    private Long user1Id; // ID of the first user

    @Column(name = "user2_id", nullable = false)
    private Long user2Id; // ID of the second user

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // Timestamp when the chat was created

    // Default constructor (required by JPA)
    public Chat() {
        this.createdAt = LocalDateTime.now(); // Set the creation timestamp automatically
    }

    // Parameterized constructor (optional, for convenience)
    public Chat(Long user1Id, Long user2Id) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.createdAt = LocalDateTime.now(); // Set the creation timestamp automatically
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(Long user1Id) {
        this.user1Id = user1Id;
    }

    public Long getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(Long user2Id) {
        this.user2Id = user2Id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}