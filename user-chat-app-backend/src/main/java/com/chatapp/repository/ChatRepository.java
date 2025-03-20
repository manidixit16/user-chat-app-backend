package com.chatapp.repository;

import com.chatapp.model.Chat; // Import the Chat class
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    /**
     * Deletes all chats where the user is either user1 or user2.
     *
     * @param user1Id The ID of user1.
     * @param user2Id The ID of user2.
     */
    void deleteByUser1IdOrUser2Id(Long user1Id, Long user2Id);
}