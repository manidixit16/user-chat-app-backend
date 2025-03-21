package com.chatapp.repository;

import com.chatapp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Finds all messages in a chat.
     *
     * @param chatId The ID of the chat.
     * @return The list of messages in the chat.
     */
    List<Message> findByChatId(Long chatId);

    /**
     * Marks messages as read for a user in a chat.
     *
     * @param chatId The ID of the chat.
     * @param userId The ID of the user.
     */
    @Modifying
    @Query("UPDATE Message m SET m.isRead = true WHERE m.chatId = :chatId AND m.sender.id <> :userId")
    void markMessagesAsRead(Long chatId, Long userId);

    /**
     * Deletes all messages sent by a user.
     *
     * @param senderId The ID of the sender.
     */
    @Modifying
    @Query("DELETE FROM Message m WHERE m.sender.id = :senderId")
    void deleteBySenderId(@Param("senderId") Long senderId);
}