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
public class CancelEventNotificationGeneratorForConfirmation extends EventNotificationGeneratorForConfirmations {

    private static final String TITLE = "Event is canceled";
    private static final String CONTENT = "Event {0} you confirmed is canceled. Check details.";

    //TODO make it working after full refactor
    @Autowired
    public CancelEventNotificationGeneratorForConfirmation(EventRepository eventRepository, NotificationRepository notificationRepository) {
        super(eventRepository, notificationRepository);
        this.logger = LoggerFactory.getLogger(CancelEventNotificationGeneratorForConfirmation.class);
    }

    @Override
    Notification generateSingleNotification(Confirmation c, Event e) {
        return Notification.builder()
                .userUid(c.getUser().getId())
                .title(TITLE)
                .content(MessageFormat.format(CONTENT, e.getName()))
                .event(e)
                .created(LocalDateTime.now())
                .read(false).build();
    }
}
