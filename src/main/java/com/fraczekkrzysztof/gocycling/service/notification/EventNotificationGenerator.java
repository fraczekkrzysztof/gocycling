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
public class EventNotificationGenerator implements NotificationGenerator {

    private static final Logger logger = LoggerFactory.getLogger(EventNotificationGenerator.class);
    private static final String UPDATE_TITLE = "Event was updated";
    private static final String UPDATE_CONTENT = "Event {0} you confirmed was updated. Check details.";
    private static final String CANCEL_TITLE = "Event was updated";
    private static final String CANCEL_CONTENT = "Event {0} you confirmed was updated. Check details.";
    private List<Long> eventsListToUpdate = new ArrayList<>();
    private List<Long> eventsListToCancel = new ArrayList<>();

    private EventRepository eventRepository;
    private NotificationRepository notificationRepository;
    private ConfirmationRepository confirmationRepository;

    @Autowired
    public EventNotificationGenerator(EventRepository eventRepository, NotificationRepository notificationRepository, ConfirmationRepository confirmationRepository){
        this.eventRepository = eventRepository;
        this.notificationRepository = notificationRepository;
        this.confirmationRepository = confirmationRepository;
    }

    @Override
    public void addEventIdToUpdate(long id) {
        logger.info("Retrieve new event to update. Add to list");
        eventsListToUpdate.add(id);
    }

    @Override
    public void addEventIdToCancel(long id) {
        logger.info("Retrieve new event to cancel.Add to list");
        eventsListToCancel.add(id);
    }

    @Override
    @Scheduled(initialDelay = 1000, fixedRate = 10000)
    public void generateNotification() {
        logger.info("Starting generating notification");
        List<Long> eventIdsToGenerateNotificationForUpdate = eventsListToUpdate;
        List<Long> eventIdsToGenerateNotificationForCancel = eventsListToCancel;
        List<Notification> toSaveNotification = new ArrayList<>();
        toSaveNotification.addAll(generateNotifications(eventIdsToGenerateNotificationForUpdate,NotificationType.UPDATE));
        toSaveNotification.addAll(generateNotifications(eventIdsToGenerateNotificationForCancel,NotificationType.CANCEL));
        if (!toSaveNotification.isEmpty()){
            notificationRepository.saveAll(toSaveNotification);
        }
        eventsListToUpdate.removeAll(eventIdsToGenerateNotificationForUpdate);
        eventsListToCancel.removeAll(eventIdsToGenerateNotificationForCancel);
        logger.info("Generating finished");
    }

    private List<Notification> generateNotifications(List<Long> ids, NotificationType type){
        List<Notification> notificationToSave = new ArrayList<>();
        if (!ids.isEmpty()){
            List<Event> eventsToGenerateNotification = eventRepository.findAllById(ids);
            eventsToGenerateNotification.stream().forEach(e -> {
                List<Confirmation> byEventId = confirmationRepository.findByEventId(e.getId());
                for (Confirmation c : byEventId){
                    notificationToSave.add(generateSingleNotification(c,e,type));
                }
            });
        }
        return notificationToSave;
    }

    private Notification generateSingleNotification(Confirmation c, Event e, NotificationType type) {
        Notification.Builder builder = new Notification.Builder();
        if (type == NotificationType.UPDATE) {
            builder.setUserUid(c.getUserUid())
                    .setTitle(UPDATE_TITLE)
                    .setContent(MessageFormat.format(UPDATE_CONTENT, e.getName()))
                    .setEvent(e)
                    .setCreated(LocalDateTime.now())
                    .setRead(false);
        } else if (type == NotificationType.CANCEL) {
            builder.setUserUid(c.getUserUid())
                    .setTitle(CANCEL_TITLE)
                    .setContent(MessageFormat.format(CANCEL_CONTENT, e.getName()))
                    .setEvent(e)
                    .setCreated(LocalDateTime.now())
                    .setRead(false);
        }
        return builder.build();
    }
}
