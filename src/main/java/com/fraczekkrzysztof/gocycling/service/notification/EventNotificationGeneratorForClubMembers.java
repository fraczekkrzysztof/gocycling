package com.fraczekkrzysztof.gocycling.service.notification;

import com.fraczekkrzysztof.gocycling.dao.ClubRepository;
import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dao.MemberRepository;
import com.fraczekkrzysztof.gocycling.dao.NotificationRepository;
import com.fraczekkrzysztof.gocycling.entity.Club;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.Member;
import com.fraczekkrzysztof.gocycling.entity.Notification;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EventNotificationGeneratorForClubMembers {

    Logger logger = null;
    private Map<Long, List<String>> eventsWithUserToIgnore = new HashMap<>();

    private final EventRepository eventRepository;
    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;

    public EventNotificationGeneratorForClubMembers(EventRepository eventRepository, NotificationRepository notificationRepository, MemberRepository memberRepository, ClubRepository clubRepository) {
        this.eventRepository = eventRepository;
        this.notificationRepository = notificationRepository;
        this.memberRepository = memberRepository;
        this.clubRepository = clubRepository;
    }

    public void addEventIdAndIgnoreUser(long id, String userUidToIgnore) {
        logger.info("Retrieve new event. Add to list");
        List<String> ignoreUsers = eventsWithUserToIgnore.get(id);
        if (ignoreUsers != null) {
            ignoreUsers.add(userUidToIgnore);
        } else {
            ignoreUsers = new ArrayList<>();
            if (userUidToIgnore != null) {
                ignoreUsers.add(userUidToIgnore);
            }
        }
        eventsWithUserToIgnore.put(id, ignoreUsers);
    }

    @Scheduled(initialDelay = 1000, fixedRate = 60000)
    public void generateNotification() {
        logger.debug("Starting generating notification");
        Map<Long, List<String>> eventIdsToGenerateNotificationForUpdate = eventsWithUserToIgnore;
        List<Notification> toSaveNotification = new ArrayList<>();
        toSaveNotification.addAll(generateNotifications(eventIdsToGenerateNotificationForUpdate));
        if (!toSaveNotification.isEmpty()) {
            notificationRepository.saveAll(toSaveNotification);
        }
        removeElementFromMap(eventIdsToGenerateNotificationForUpdate);
        logger.debug("Generating finished");
    }

    private void removeElementFromMap(Map<Long, List<String>> toRemoved) {
        eventsWithUserToIgnore.entrySet().removeIf(entry -> toRemoved.keySet().contains(entry.getKey()));
    }

    protected List<Notification> generateNotifications(Map<Long, List<String>> idsWithUserToignore) {
        List<Notification> notificationToSave = new ArrayList<>();
        if (!idsWithUserToignore.isEmpty()) {
            List<Event> eventsToGenerateNotification = eventRepository.findAllById(idsWithUserToignore.keySet());
            eventsToGenerateNotification.stream().forEach(e -> {
                Club club = clubRepository.findSingleClubForEventId(e.getId());
                List<Member> clubMembers = memberRepository.findAllClubMembers(club.getId());
                for (Member m : clubMembers) {
                    if (idsWithUserToignore.get(e.getId()).contains(m.getUserUid())) {
                        continue;
                    }

                    notificationToSave.add(generateSingleNotification(club, m, e));
                }
            });
        }
        return notificationToSave;
    }

    abstract Notification generateSingleNotification(Club c, Member m, Event e);
}
