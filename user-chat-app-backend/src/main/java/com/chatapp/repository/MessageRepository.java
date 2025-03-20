package com.chatapp.repository;

import com.chatapp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Finds all messages in a chat by chat ID.
     *
     * @param chatId The ID of the chat.
     * @return A list of messages in the chat.
     */
    List<Message> findByChatId(Long chatId);

    /**
     * Marks all messages in a chat as read for a specific user.
     *
     * @param chatId The ID of the chat.
     * @param userId The ID of the user.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.isRead = true WHERE m.chatId = :chatId AND m.sender.id != :userId")
    void markMessagesAsRead(@Param("chatId") Long chatId, @Param("userId") Long userId);

    /**
     * Deletes all messages sent by a user.
     *
     * @param senderId The ID of the sender.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Message m WHERE m.sender.id = :senderId")
    void deleteBySenderId(@Param("senderId") Long senderId);
}