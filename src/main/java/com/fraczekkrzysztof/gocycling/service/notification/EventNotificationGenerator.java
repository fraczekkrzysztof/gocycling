package com.fraczekkrzysztof.gocycling.service.notification;

import com.fraczekkrzysztof.gocycling.dao.ConfirmationRepository;
import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dao.NotificationRepository;
import com.fraczekkrzysztof.gocycling.entity.Confirmation;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.Notification;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

public abstract class EventNotificationGenerator {

    Logger logger = null;
    private List<Long> eventsList = new ArrayList<>();

    private EventRepository eventRepository;
    private NotificationRepository notificationRepository;
    private ConfirmationRepository confirmationRepository;

    public EventNotificationGenerator(EventRepository eventRepository, NotificationRepository notificationRepository, ConfirmationRepository confirmationRepository){
        this.eventRepository = eventRepository;
        this.notificationRepository = notificationRepository;
        this.confirmationRepository = confirmationRepository;
    }

    public void addEventId(long id) {
        logger.info("Retrieve new event. Add to list");
        eventsList.add(id);
    }


    @Scheduled(initialDelay = 1000, fixedRate = 10000)
    public void generateNotification() {
        logger.info("Starting generating notification");
        List<Long> eventIdsToGenerateNotificationForUpdate = eventsList;
        List<Notification> toSaveNotification = new ArrayList<>();
        toSaveNotification.addAll(generateNotifications(eventIdsToGenerateNotificationForUpdate));
        if (!toSaveNotification.isEmpty()){
            notificationRepository.saveAll(toSaveNotification);
        }
        eventsList.removeAll(eventIdsToGenerateNotificationForUpdate);
        logger.info("Generating finished");
    }

    private List<Notification> generateNotifications(List<Long> ids){
        List<Notification> notificationToSave = new ArrayList<>();
        if (!ids.isEmpty()){
            List<Event> eventsToGenerateNotification = eventRepository.findAllById(ids);
            eventsToGenerateNotification.stream().forEach(e -> {
                List<Confirmation> byEventId = confirmationRepository.findByEventId(e.getId());
                for (Confirmation c : byEventId){
                    notificationToSave.add(generateSingleNotification(c,e));
                }
            });
        }
        return notificationToSave;
    }

    abstract Notification generateSingleNotification(Confirmation c, Event e);

}
