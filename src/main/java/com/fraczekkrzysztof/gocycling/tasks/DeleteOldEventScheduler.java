package com.fraczekkrzysztof.gocycling.tasks;

import com.fraczekkrzysztof.gocycling.GoCyclingProperties;
import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dao.NotificationRepository;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DeleteOldEventScheduler {

    private final GoCyclingProperties properties;
    private final EventRepository eventRepository;
    private final NotificationRepository notificationRepository;

    @Scheduled(initialDelay = 1000, fixedRateString = "${gocycling.deleteOldEventsInterval}")
    public void deleteOldEvents() {
        log.info("Deleting old even started");
        List<Event> eventsListToDelete = eventRepository.findEventsOlderThan(LocalDateTime.now().minusDays(properties.getDeleteOldEventsAfterDays()), Pageable.unpaged()).getContent();
        if (!CollectionUtils.isEmpty(eventsListToDelete)) {
            eventRepository.deleteAll(eventsListToDelete);
            List<Long> deletedEvents = eventsListToDelete.stream().map(Event::getId).collect(Collectors.toList());
            List<Notification> notificationsToDelete = notificationRepository.findByEventIdList(deletedEvents, Pageable.unpaged()).getContent();
            notificationRepository.deleteAll(notificationsToDelete);
            log.info(String.format("Deleting old events finished successfully. %d events deleted", deletedEvents.size()));
        }
    }

}
