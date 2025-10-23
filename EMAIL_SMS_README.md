# Email and SMS Notification System

This document explains how to use the newly implemented Email and SMS notification system in the Village Management Auth Service.

## 🚀 Features

- **📧 Beautiful Email Templates**: HTML email templates with responsive design
- **📱 SMS Notifications**: SMS support with priority indicators
- **🔄 Bulk Notifications**: Send to all entity subscribers at once
- **🎯 Smart Templates**: Different templates for different notification types
- **📊 Comprehensive Logging**: Detailed logging for monitoring and debugging
- **⚙️ Configurable**: Easy configuration via application properties

## 📋 Setup Instructions

### 1. Dependencies Added

The following dependencies have been added to `pom.xml`:

```xml
<!-- Email Support -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>

<!-- Template Engine -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### 2. Configuration

Add the following properties to `src/main/resources/application.properties`:

```properties
# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true

# From email address
app.email.from=noreply@villagemanagement.com
app.email.from-name=Village Management System

# SMS Configuration (Twilio example)
app.sms.enabled=false
app.sms.twilio.account-sid=your-twilio-account-sid
app.sms.twilio.auth-token=your-twilio-auth-token
app.sms.twilio.from-phone=+1234567890
```

### 3. Gmail Setup

For Gmail SMTP:
1. Enable 2-Factor Authentication
2. Generate an App Password
3. Use the App Password in `spring.mail.password`
4. Use your Gmail address in `spring.mail.username`

## 📧 Email Templates

Five beautiful HTML email templates are available:

### 1. General Notification (`general-notification.html`)
- Standard notifications
- Clean, professional design
- Blue accent color

### 2. Emergency Notification (`emergency-notification.html`)
- 🚨 Urgent alerts
- Red accent color
- Animated pulse effect
- Immediate attention callout

### 3. Update Notification (`update-notification.html`)
- 📢 Information updates
- Yellow accent color
- Update badge

### 4. Maintenance Notification (`maintenance-notification.html`)
- 🔧 Scheduled maintenance
- Teal accent color
- Maintenance schedule info

### 5. Status Change Notification (`status-change-notification.html`)
- 📊 Status updates
- Purple accent color
- Status indicators

### 6. Welcome Notification (`welcome-notification.html`)
- 🎉 New subscriber welcome
- Green accent color
- Subscription confirmation

## 📱 SMS Features

- **Priority Emojis**: 🚨 Emergency, 🔧 Maintenance, 📢 Updates, 📊 Status, ℹ️ General
- **Configurable**: Enable/disable via `app.sms.enabled`
- **Rate Limiting**: Built-in delays to prevent overwhelming SMS providers
- **Twilio Ready**: Commented code for easy Twilio integration

## 🔧 API Endpoints

### Send Email & SMS to All Entity Subscribers

```http
POST /api/notifications/entity/{entityId}/broadcast-with-email-sms
Content-Type: application/x-www-form-urlencoded

title=Water Supply Update
message=Water supply will be interrupted from 2 PM to 4 PM for maintenance
notificationType=MAINTENANCE
priority=HIGH
```

**Parameters:**
- `title` (required): Notification title
- `message` (required): Notification message
- `notificationType` (optional): GENERAL, EMERGENCY, UPDATE, MAINTENANCE, STATUS_CHANGE
- `priority` (optional): LOW, NORMAL, HIGH, URGENT

**Response:**
```json
"Email and SMS notifications sent to all subscribers successfully"
```

### Other Existing Endpoints

```http
# Get user's notifications
GET /api/notifications/my-notifications?userId={userId}

# Get entity notifications
GET /api/notifications/entity/{entityId}

# Send to specific user only
POST /api/notifications/user/{userId}/send?entityId={entityId}&title=...&message=...
```

## 💻 Code Usage

### Using in Service Layer

```java
@Autowired
private EntityNotificationService notificationService;

// Send to all subscribers of an entity
notificationService.sendSMSAndEmailToSubscribers(
    entityId,
    "Maintenance Notice",
    "Water supply will be interrupted for 2 hours",
    "MAINTENANCE",
    "HIGH"
);
```

### Using Email Service Directly

```java
@Autowired
private EmailService emailService;

@Autowired
private SMSService smsService;

// Send single email
emailService.sendEntityNotificationEmail(user, entity, title, message, type);

// Send single SMS
smsService.sendEntityNotificationSMS(user, entity, title, message, type);

// Send bulk notifications
emailService.sendBulkEmailToSubscribers(subscribers, entity, title, message, type);
smsService.sendBulkSMSToSubscribers(subscribers, entity, title, message, type);
```

## 📊 Logging

Comprehensive logging is implemented:

- **Email Service**: Logs successful sends and failures
- **SMS Service**: Logs delivery status and errors
- **Bulk Operations**: Logs batch statistics (success/fail counts)

Check application logs for:
```
INFO  - Email sent successfully to: user@example.com
INFO  - Bulk email completed: 15 successful, 2 failed
INFO  - SMS sent successfully to: +1234567890
```

## 🔐 Security Considerations

1. **Email Credentials**: Store in environment variables, not in code
2. **Rate Limiting**: Built-in delays prevent overwhelming services
3. **Input Validation**: All inputs are validated before processing
4. **Error Handling**: Graceful handling of failures without exposing sensitive data

## 🚀 Production Deployment

### Enable SMS (Twilio Integration)

1. Add Twilio dependencies to `pom.xml`:
```xml
<dependency>
    <groupId>com.twilio.sdk</groupId>
    <artifactId>twilio</artifactId>
    <version>9.14.1</version>
</dependency>
```

2. Uncomment Twilio code in `SMSService.java`:
```java
// Remove comment markers from these imports:
// import com.twilio.Twilio;
// import com.twilio.rest.api.v2010.account.Message;
// import com.twilio.type.PhoneNumber;
```

3. Set SMS configuration:
```properties
app.sms.enabled=true
app.sms.twilio.account-sid=YOUR_ACCOUNT_SID
app.sms.twilio.auth-token=YOUR_AUTH_TOKEN
app.sms.twilio.from-phone=YOUR_TWILIO_NUMBER
```

### Alternative SMS Providers

The SMS service is designed to be provider-agnostic. You can easily integrate:

- **AWS SNS**
- **TextLocal**
- **MessageBird**
- **Custom SMS Gateway**

## 🧪 Testing

### Test Email Functionality

1. Set up a test Gmail account
2. Configure SMTP settings
3. Use the API endpoint to send test notifications
4. Check email delivery

### Test SMS Functionality

1. Enable SMS in configuration
2. Use the API endpoint to send test notifications
3. Check console logs for SMS content (when disabled)
4. Integrate Twilio for real SMS delivery

## 📈 Monitoring

Monitor the notification system through:

1. **Application Logs**: Check for success/failure messages
2. **Database Records**: EntityNotification table tracks all notifications
3. **Email Delivery**: Monitor SMTP server logs
4. **SMS Delivery**: Monitor provider dashboards (Twilio, etc.)

## 🔄 Future Enhancements

- [ ] Email template customization interface
- [ ] SMS template management
- [ ] Delivery tracking and retry logic
- [ ] User preference management (email/SMS opt-in/opt-out)
- [ ] Scheduled notifications
- [ ] Notification analytics dashboard
- [ ] Multiple SMS provider support

## 📞 Support

For issues or questions:
1. Check application logs for error messages
2. Verify configuration settings
3. Test with simple notifications first
4. Enable debug logging for detailed troubleshooting

---

**🎉 Happy Notifying!** The email and SMS notification system is now ready for production use.
