package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Entity_Item;
import com.example.demo.entity.User;

// Uncomment and add Twilio dependencies to pom.xml for full SMS integration
// import com.twilio.Twilio;
// import com.twilio.rest.api.v2010.account.Message;
// import com.twilio.type.PhoneNumber;

@Service
public class SMSService {

    private static final Logger logger = LoggerFactory.getLogger(SMSService.class);

    @Value("${app.sms.enabled:false}")
    private boolean smsEnabled;

    @Value("${app.sms.twilio.account-sid:}")
    private String twilioAccountSid;

    @Value("${app.sms.twilio.auth-token:}")
    private String twilioAuthToken;

    @Value("${app.sms.twilio.from-phone:+1234567890}")
    private String fromPhoneNumber;

    public void sendEntityNotificationSMS(User user, Entity_Item entity, String title, String message,
            String notificationType) {
        if (!smsEnabled || user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            return;
        }

        String smsContent = formatSMSContent(user, entity, title, message, notificationType);
        sendSMS(user.getPhone(), smsContent);
    }

    public void sendWelcomeSMS(User user, Entity_Item entity) {
        if (!smsEnabled || user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            return;
        }

        String smsContent = "Welcome " + user.getName() + "! You are now subscribed to notifications for " +
                          entity.getName() + " (" + entity.getType() + "). Stay updated!";
        sendSMS(user.getPhone(), smsContent);
    }

    private String formatSMSContent(User user, Entity_Item entity, String title, String message,
            String notificationType) {
        String priority = getPriorityEmoji(notificationType);
        return priority + " " + title + " - " + entity.getName() + ": " + message +
               " | Sent to: " + user.getName();
    }

    private String getPriorityEmoji(String notificationType) {
        switch (notificationType.toUpperCase()) {
            case "EMERGENCY":
                return "🚨";
            case "MAINTENANCE":
                return "🔧";
            case "UPDATE":
                return "📢";
            case "STATUS_CHANGE":
                return "📊";
            default:
                return "ℹ️";
        }
    }

    private void sendSMS(String phoneNumber, String content) {
        if (!smsEnabled) {
            logger.info("📱 SMS (DISABLED) to {}: {}", phoneNumber, content);
            return;
        }

        logger.info("📱 Sending SMS to {}: {}", phoneNumber, content);

        // TODO: Integrate with actual SMS provider (Twilio, AWS SNS, etc.)
        System.out.println("📱 SMS to " + phoneNumber + ": " + content);

        // Uncomment for Twilio integration:
        /*
        try {
            Twilio.init(twilioAccountSid, twilioAuthToken);
            Message message = Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(fromPhoneNumber),
                content
            ).create();
            logger.info("SMS sent successfully to {}: {}", phoneNumber, message.getSid());
        } catch (Exception e) {
            logger.error("Failed to send SMS to {}: {}", phoneNumber, e.getMessage());
            // Log to database or external monitoring system
        }
        */
    }

    public void sendBulkSMSToSubscribers(List<User> subscribers, Entity_Item entity, String title, String message,
            String notificationType) {
        logger.info("Sending bulk SMS to {} subscribers for entity: {}", subscribers.size(), entity.getName());

        int successCount = 0;
        int failCount = 0;

        for (User user : subscribers) {
            try {
                sendEntityNotificationSMS(user, entity, title, message, notificationType);
                successCount++;
                Thread.sleep(100); // Small delay to avoid rate limits
            } catch (Exception e) {
                logger.error("Failed to send SMS to {}: {}", user.getPhone(), e.getMessage());
                failCount++;
            }
        }

        logger.info("Bulk SMS completed: {} successful, {} failed", successCount, failCount);
    }
}
