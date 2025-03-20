package com.chatapp.services;

import com.chatapp.model.User;
import com.chatapp.repository.ChatRepository;
import com.chatapp.repository.MessageRepository;
import com.chatapp.repository.UnreadMessageRepository;
import com.chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UnreadMessageRepository unreadMessageRepository;

    /**
     * Registers a new user.
     *
     * @param user The user to register.
     * @return The registered user.
     */
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username to search for.
     * @return The user if found, otherwise null.
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    /**
     * Validates a user's password.
     *
     * @param user        The user to validate.
     * @param rawPassword The raw password to validate.
     * @return True if the password is valid, otherwise false.
     */
    public boolean validatePassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    /**
     * Loads a user by their username for Spring Security.
     *
     * @param username The username to load.
     * @return The UserDetails object.
     * @throws UsernameNotFoundException If the user is not found.
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(new ArrayList<>())
                .build();
    }

    /**
     * Deletes a user by their ID.
     * Also deletes all associated data (chats, messages, unread messages).
     *
     * @param userId The ID of the user to delete.
     */
    @Transactional
    public void deleteUser(Long userId) {
        // Delete all unread messages associated with the user
        unreadMessageRepository.deleteByUserId(userId);

        // Delete all messages sent by the user
        messageRepository.deleteBySenderId(userId);

        // Delete all chats where the user is either user1 or user2
        chatRepository.deleteByUser1IdOrUser2Id(userId, userId);

        // Finally, delete the user
        userRepository.deleteById(userId);
    }
}