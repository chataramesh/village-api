package com.example.demo.controller.chat;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.chat.MessageRepository;

@RequestMapping("/api/messages")
@RestController
@CrossOrigin(origins = "*")
public class MessageController {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@PostMapping("/send")
	public ResponseEntity<Message> sendMessage(@RequestBody Map<String, Object> messageData,
			@RequestHeader("Authorization") String token) {

		System.out.println(messageData);
		UUID senderId = UUID.fromString((String) messageData.get("senderId"));
		UUID receiverId = UUID.fromString((String) messageData.get("receiverId"));
		String content = (String) messageData.get("content");
		token = token.substring(7);
//        String mediaUrl = (String) messageData.get("mediaUrl");
//        String mediaType = (String) messageData.get("mediaType");

		User sender = userRepository.findById(senderId).orElse(null);
		System.out.println("senderId=" + senderId);
		User receiver = userRepository.findById(receiverId).orElse(null);
		// Avoid NPE: don't dereference receiver before null-check

		if (sender == null || receiver == null) {
			return ResponseEntity.badRequest().build();
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

		// Send notification to receiver
		messagingTemplate.convertAndSendToUser(receiverId.toString(), "/queue/notifications", message);

		return ResponseEntity.ok(message);
	}

	@GetMapping("/conversations/{receiverId}/{senderId}")
	public ResponseEntity<List<Message>> getConversations(@PathVariable UUID receiverId, @PathVariable UUID senderId) {
		// Get all messages where user is sender or receiver
		List<Message> messages = messageRepository.findConversationBetweenUsers(receiverId, senderId);
		return ResponseEntity.ok(messages);
	}
}
