package com.fraczekkrzysztof.gocycling.service.notification;

import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dao.NotificationRepository;
import com.fraczekkrzysztof.gocycling.entity.Confirmation;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.Notification;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;

@Service
public class NewConversationNotificationGeneratorForConfirmation extends EventNotificationGeneratorForConfirmations {
    private static final String TITLE = "New message";
    private static final String CONTENT = "Someone leave the message for event {0} you confirmed.";

    @Autowired
    public NewConversationNotificationGeneratorForConfirmation(EventRepository eventRepository, NotificationRepository notificationRepository) {
        super(eventRepository, notificationRepository);
        this.logger = LoggerFactory.getLogger(NewConversationNotificationGeneratorForConfirmation.class);
    }

    @Override
    Notification generateSingleNotification(Confirmation c, Event e) {
        return Notification.builder()
                .userUid(c.getUserUid())
                .title(TITLE)
                .content(MessageFormat.format(CONTENT, e.getName()))
                .event(e)
                .created(LocalDateTime.now())
                .read(false).build();
    }
}
