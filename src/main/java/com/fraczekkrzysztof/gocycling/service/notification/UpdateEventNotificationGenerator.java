package com.fraczekkrzysztof.gocycling.service.notification;

import com.fraczekkrzysztof.gocycling.dao.ConfirmationRepository;
import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dao.NotificationRepository;
import com.fraczekkrzysztof.gocycling.entity.Confirmation;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateEventNotificationGenerator implements NotificationGenerator {

    private static final Logger logger = LoggerFactory.getLogger(UpdateEventNotificationGenerator.class);
    private static final String TITLE = "Event was updated";
    private static final String CONTENT = "Event {0} you confirmed was updated. Check details.";
    private List<Long> eventsList = new ArrayList<>();

    private EventRepository eventRepository;
    private NotificationRepository notificationRepository;
    private ConfirmationRepository confirmationRepository;

    @Autowired
    public UpdateEventNotificationGenerator(EventRepository eventRepository, NotificationRepository notificationRepository, ConfirmationRepository confirmationRepository){
        this.eventRepository = eventRepository;
        this.notificationRepository = notificationRepository;
        this.confirmationRepository = confirmationRepository;
    }

    @Override
    public void addEventId(long id) {
        logger.info("Retrieve new event. Add to list");
        eventsList.add(id);
    }

    @Override
    @Scheduled(initialDelay = 1000, fixedRate = 10000)
    public void generateNotification() {
        if (eventsList.isEmpty()){
            logger.info("Events list is empty. There is nothing to do");
            return;
        }
        logger.info("Starting generating notification");
        List<Long> eventIdsToGenerateNotification = eventsList;
        List<Event> eventsToGenerateNotification = eventRepository.findAllById(eventsList);
        List<Notification> notificationToSave = new ArrayList<>();
        eventsToGenerateNotification.stream().forEach(e -> {
            for (Confirmation c : confirmationRepository.findByEventId(e.getId())){
                notificationToSave.add(generateSingleNotification(c,e));
            }
        });
        notificationRepository.saveAll(notificationToSave);
        eventsList.removeAll(eventIdsToGenerateNotification);
        logger.info("Generating finished");
    }

    private Notification generateSingleNotification(Confirmation c, Event e){
        return new Notification.Builder()
                .setUserUid(c.getUserUid())
                .setTitle(TITLE)
                .setContent(MessageFormat.format(CONTENT,e.getName()))
                .setEvent(e)
                .setCreated(LocalDateTime.now())
                .setRead(false)
                .build();
    }
}
