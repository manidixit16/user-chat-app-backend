package com.chatapp.repository;

import com.chatapp.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    /**
     * Deletes all chats where the user is either user1 or user2.
     *
     * @param user1Id The ID of user1.
     * @param user2Id The ID of user2.
     */
    @Modifying
    @Query("DELETE FROM Chat c WHERE c.user1Id = :user1Id OR c.user2Id = :user2Id")
    void deleteByUser1IdOrUser2Id(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);
}