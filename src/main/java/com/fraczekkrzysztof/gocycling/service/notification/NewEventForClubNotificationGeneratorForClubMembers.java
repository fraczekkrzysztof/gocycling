package com.fraczekkrzysztof.gocycling.service.notification;

import com.fraczekkrzysztof.gocycling.dao.ClubRepository;
import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dao.NotificationRepository;
import com.fraczekkrzysztof.gocycling.entity.Club;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.Member;
import com.fraczekkrzysztof.gocycling.entity.Notification;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;

@Service
public class NewEventForClubNotificationGeneratorForClubMembers extends EventNotificationGeneratorForClubMembers {

    private static final String TITLE = "New Event";
    private static final String CONTENT = "There is new Event in club {0}";

    @Autowired
    public NewEventForClubNotificationGeneratorForClubMembers(EventRepository eventRepository, NotificationRepository notificationRepository, ClubRepository clubRepository) {
        super(eventRepository, notificationRepository, clubRepository);
        this.logger = LoggerFactory.getLogger(NewEventForClubNotificationGeneratorForClubMembers.class);
    }

    @Override
    Notification generateSingleNotification(Club c, Member m, Event e) {
        return Notification.builder()
                .userUid(m.getUser().getId())
                .title(TITLE)
                .content(MessageFormat.format(CONTENT, c.getName()))
                .event(e)
                .created(LocalDateTime.now())
                .read(false).build();
    }
}