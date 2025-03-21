document.addEventListener('DOMContentLoaded', () => {
  const messageInput = document.getElementById('message-input');
  const sendButton = document.getElementById('send-button');
  const messagesDiv = document.getElementById('messages');

  // WebSocket connection
  const socket = new WebSocket('ws://localhost:8080/ws');

  // Fetch chat history when the page loads
  const chatId = 1; // Replace with the actual chat ID
  const userId = 1; // Replace with the actual user ID
  fetch(`/api/chats/${chatId}/messages?userId=${userId}`)
      .then((response) => response.json())
      .then((messages) => {
          messages.forEach((message) => {
              const messageElement = document.createElement('div');
              messageElement.textContent = `${message.sender.username}: ${message.content}`;
              messagesDiv.appendChild(messageElement);
          });
          messagesDiv.scrollTop = messagesDiv.scrollHeight; // Auto-scroll to the latest message
      });

  // Handle incoming WebSocket messages
  socket.onmessage = (event) => {
      const message = JSON.parse(event.data);

      // Display the message
      const messageElement = document.createElement('div');
      messageElement.textContent = `${message.sender.username}: ${message.content}`;
      messagesDiv.appendChild(messageElement);
      messagesDiv.scrollTop = messagesDiv.scrollHeight; // Auto-scroll to the latest message
  };

  // Send message
  sendButton.addEventListener('click', () => {
      const message = messageInput.value.trim();
      if (message) {
          const chatId = 1; // Replace with the actual chat ID
          const senderId = 1; // Replace with the actual sender ID

          const messageData = {
              chatId: chatId,
              sender: { id: senderId },
              content: message,
          };

          // Send the message via WebSocket
          socket.send(JSON.stringify(messageData));
          messageInput.value = '';
      }
  });

  // Send message on Enter key
  messageInput.addEventListener('keypress', (e) => {
      if (e.key === 'Enter') {
          sendButton.click();
      }
  });
});