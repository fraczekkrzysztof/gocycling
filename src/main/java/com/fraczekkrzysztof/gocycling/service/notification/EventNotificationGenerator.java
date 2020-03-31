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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EventNotificationGenerator {

    Logger logger = null;
    private Map<Long,List<String>> eventsWithUserToIgnore = new HashMap<>();

    private EventRepository eventRepository;
    private NotificationRepository notificationRepository;
    private ConfirmationRepository confirmationRepository;

    public EventNotificationGenerator(EventRepository eventRepository, NotificationRepository notificationRepository, ConfirmationRepository confirmationRepository){
        this.eventRepository = eventRepository;
        this.notificationRepository = notificationRepository;
        this.confirmationRepository = confirmationRepository;
    }

    public void addEventIdAndIgnoreUser(long id, String userUidToIgnore) {
        logger.info("Retrieve new event. Add to list");
        List<String> ignoreUsers = eventsWithUserToIgnore.get(id);
        if (ignoreUsers != null){
            ignoreUsers.add(userUidToIgnore);
        } else {
            ignoreUsers = new ArrayList<>();
            if(userUidToIgnore != null){
                ignoreUsers.add(userUidToIgnore);
            }
        }
        eventsWithUserToIgnore.put(id,ignoreUsers);

    }


    @Scheduled(initialDelay = 1000, fixedRate = 60000)
    public void generateNotification() {
        logger.debug("Starting generating notification");
        Map<Long,List<String>> eventIdsToGenerateNotificationForUpdate = eventsWithUserToIgnore;
        List<Notification> toSaveNotification = new ArrayList<>();
        toSaveNotification.addAll(generateNotifications(eventIdsToGenerateNotificationForUpdate));
        if (!toSaveNotification.isEmpty()){
            notificationRepository.saveAll(toSaveNotification);
        }
        removeElementFromMap(eventIdsToGenerateNotificationForUpdate);
        logger.debug("Generating finished");
    }

    private void removeElementFromMap(Map<Long,List<String>> toRemoved){
        eventsWithUserToIgnore.entrySet().removeIf(entry -> toRemoved.keySet().contains(entry.getKey()));
    }

    private List<Notification> generateNotifications(Map<Long,List<String>> idsWithUserToignore){
        List<Notification> notificationToSave = new ArrayList<>();
        if (!idsWithUserToignore.isEmpty()){
            List<Event> eventsToGenerateNotification = eventRepository.findAllById(idsWithUserToignore.keySet());
            eventsToGenerateNotification.stream().forEach(e -> {
                List<Confirmation> eventConfirmations = confirmationRepository.findByEventId(e.getId());
                for (Confirmation c : eventConfirmations){
                    if (idsWithUserToignore.get(e.getId()).contains(c.getUserUid())){
                        continue;
                    }
                    notificationToSave.add(generateSingleNotification(c,e));
                }
            });
        }
        return notificationToSave;
    }

    abstract Notification generateSingleNotification(Confirmation c, Event e);

}
