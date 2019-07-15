package it.polito.ai.demo.Controller;


import it.polito.ai.demo.DTO.NotificaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    @Autowired
    private SimpMessagingTemplate template;

    // Initialize Notifications
    private NotificaDTO notifications = NotificaDTO.builder().count(0).msg("prova").build();

    @GetMapping("/notify")
    public String getNotification() {

        // Increment Notification by one
        notifications.increment();

        // Push notifications to front-end
        template.convertAndSend("/topic/notification", notifications);

        return "Notifications successfully sent to Angular !";
    }
}
