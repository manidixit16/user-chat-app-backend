package com.chatapp.repository;

import com.chatapp.model.UnreadMessage;
import com.chatapp.model.UnreadMessageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UnreadMessageRepository extends JpaRepository<UnreadMessage, UnreadMessageId> {

    /**
     * Increments the unread message count for a user in a chat.
     *
     * @param chatId   The ID of the chat.
     * @param userId   The ID of the user.
     */
    @Modifying
    @Transactional
    @Query("UPDATE UnreadMessage u SET u.unreadCount = u.unreadCount + 1 WHERE u.id.chatId = :chatId AND u.id.userId = :userId")
    void incrementUnreadCount(@Param("chatId") Long chatId, @Param("userId") Long userId);

    /**
     * Resets the unread message count for a user in a chat.
     *
     * @param chatId   The ID of the chat.
     * @param userId   The ID of the user.
     */
    @Modifying
    @Transactional
    @Query("UPDATE UnreadMessage u SET u.unreadCount = 0 WHERE u.id.chatId = :chatId AND u.id.userId = :userId")
    void resetUnreadCount(@Param("chatId") Long chatId, @Param("userId") Long userId);

    /**
     * Deletes all unread messages associated with a user.
     *
     * @param userId The ID of the user.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM UnreadMessage u WHERE u.id.userId = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}