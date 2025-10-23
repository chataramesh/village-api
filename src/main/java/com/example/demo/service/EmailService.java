package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.demo.entity.Entity_Item;
import com.example.demo.entity.User;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine templateEngine;
	@Value("${app.email.from}")
	private String fromEmail;

	@Value("${app.email.from-name:Village Management System}")
	private String fromName;

	public void sendWelcomeEmail(User user, Entity_Item entity) throws MessagingException {
		Map<String, Object> templateVariables = new HashMap<>();
		templateVariables.put("userName", user.getName());
		templateVariables.put("entityName", entity.getName());
		templateVariables.put("entityType", entity.getType());
		templateVariables.put("subscribedDate", java.time.LocalDateTime.now().toLocalDate());

		// Add entity operational information
		templateVariables.put("openingTime", entity.getOpeningTime());
		templateVariables.put("closingTime", entity.getClosingTime());
		templateVariables.put("entityStatus", entity.getStatus());
		templateVariables.put("contactNumber", entity.getContactNumber());
		templateVariables.put("address", entity.getAddress());
		templateVariables.put("currentYear", java.time.Year.now().getValue());

		String htmlContent = generateWelcomeTemplate(templateVariables);

		sendEmail(user.getEmail(), "Welcome to " + entity.getName() + " Notifications!", htmlContent);
	}

	private String generateEmailTemplate(Map<String, Object> variables, String notificationType) {
		Context context = new Context();
		context.setVariables(variables);
		switch (notificationType.toUpperCase()) {
		case "EMERGENCY":
			return templateEngine.process("email/emergency-notification", context);
		case "UPDATE":
			return templateEngine.process("email/update-notification", context);
		case "MAINTENANCE":
			return templateEngine.process("email/maintenance-notification", context);
		case "STATUS_CHANGE":
			return templateEngine.process("email/status-change-notification", context);
		default:
			return templateEngine.process("email/general-notification", context);
		}
	}

	private String generateWelcomeTemplate(Map<String, Object> variables) {
		Context context = new Context();
		context.setVariables(variables);
		return templateEngine.process("email/welcome-notification", context);
	}

	private void sendEmail(String to, String subject, String htmlContent) throws MessagingException {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setFrom(fromEmail, fromName);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlContent, true); // true indicates HTML

			mailSender.send(message);
			logger.info("Email sent successfully to: {}", to);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed to send email to {}: {}", to, e.getMessage());

		}
	}

	public void sendBulkEmailToSubscribers(java.util.List<User> subscribers, Entity_Item entity, String title,
			String message, String notificationType) throws MessagingException {

		logger.info("Sending bulk emails to {} subscribers for entity: {}", subscribers.size(), entity.getName());

		int successCount = 0;
		int failCount = 0;

		for (User user : subscribers) {
			try {
				sendEntityNotificationEmail(user, entity, title, message, notificationType);
				successCount++;
				Thread.sleep(100); // Small delay to avoid overwhelming the mail server
			} catch (Exception e) {
				logger.error("Failed to send email to {}: {}", user.getEmail(), e.getMessage());
				failCount++;
			}

			logger.info("Bulk email completed: {} successful, {} failed", successCount, failCount);
		}
	}

	private void sendEntityNotificationEmail(User user, Entity_Item entity, String title, String message,
			String notificationType) throws MessagingException {

		Map<String, Object> templateVariables = new HashMap<>();
		templateVariables.put("userName", user.getName());
		templateVariables.put("entityName", entity.getName());
		templateVariables.put("entityType", entity.getType());
		templateVariables.put("title", title);
		templateVariables.put("message", message);
		templateVariables.put("notificationType", notificationType);
		templateVariables.put("currentYear", java.time.Year.now().getValue());

		// Add entity operational information
		templateVariables.put("openingTime", entity.getOpeningTime());
		templateVariables.put("closingTime", entity.getClosingTime());
		templateVariables.put("entityStatus", entity.getStatus());
		templateVariables.put("contactNumber", entity.getContactNumber());
		templateVariables.put("address", entity.getAddress());

		String htmlContent = generateEmailTemplate(templateVariables, notificationType);

		sendEmail(user.getEmail(), "Entity Notification: " + title, htmlContent);
	}
}
