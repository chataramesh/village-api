package com.example.demo.controller.chat;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.demo.dto.request.chat.MessageRequest;
import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.chat.MessageRepository;

@Controller
public class WebSocketController {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@MessageMapping("chat.sendMessage") // Maps messages sent to "chat.sendMessage" WebSocket destination
	@SendTo("/topic/public") // Specifies that the return message will be sent to "/topic/public"
	public Message sendMessage(@Payload MessageRequest messageRequest) {
		// Log the sender and content of the message for debugging
		if (messageRequest == null) {
			System.out.println("Received null message");
			return null;
		}

		System.out.println("Received message: " + messageRequest.getToken());
		UUID senderId = messageRequest.getSenderId();
		UUID receiverId = messageRequest.getReceiverId();
		String content = messageRequest.getContent();
		User sender = userRepository.findById(senderId).orElse(null);
		System.out.println("senderId=" + senderId);
		User receiver = userRepository.findById(receiverId).orElse(null);
		// Avoid NPE: don't dereference receiver before null-check

		if (sender == null || receiver == null) {
			return null;
		}
		System.out.println("receiverId=" + receiverId);

		Message message = new Message();// .builder().senderId(senderId).receiverId(receiverId).content(content).build();
		// Persist only UUID references
		message.setSenderId(senderId);
		message.setReceiverId(receiverId);
		message.setContent(content);
//        message.setMediaUrl(mediaUrl);
//        message.setMediaType(mediaType);

		// Hydrate transient users for response
		messageRepository.save(message);
		messagingTemplate.convertAndSendToUser(receiverId.toString(), // user session destination
				"/queue/messages", // destination prefix after /user/
				message);
		System.out
				.println("Message received from " + messageRequest.getSenderId() + ": " + messageRequest.getContent());
		// Broadcast the message to all subscribers on the "/topic/public" topic
		return message;
	}

	@MessageMapping("chat.addUser") // Maps messages sent to "chat.addUser" WebSocket destination
	@SendTo("/topic/chat") // Specifies that the return message will be sent to "/topic/chat"
	public Message addUser(@Payload Message msg, SimpMessageHeaderAccessor headerAccessor) {
		// Store the username in the WebSocket session attributes
		headerAccessor.getSessionAttributes().put("username", msg.getSenderId());

		// Log when a user joins the chat
		System.out.println("User joined: " + msg.getSenderId());

		// Broadcast the user join event to all subscribers on the "/topic/chat" topic
		return msg;
	}

//    @MessageMapping("/chat.sendMessage")
//    public void sendMessage(@Payload Message message) {
//        // Save the message if not already saved
//        if (message.getId() == null) {
//            messageRepository.save(message);
//        }
//
//        // Send to receiver
//        messagingTemplate.convertAndSendToUser(
//            message.getReceiverId().toString(),
//            "/queue/messages",
//            message
//        );
//    }

	@MessageMapping("/chat.markAsRead")
	public void markAsRead(@Payload Map<String, String> payload) {
		try {
			UUID messageId = UUID.fromString(payload.get("messageId"));
			Message message = messageRepository.findById(messageId).orElse(null);
			if (message != null) {
				message.setRead(true);
				messageRepository.save(message);
			}
		} catch (Exception e) {
			// Handle error
		}
	}
}
