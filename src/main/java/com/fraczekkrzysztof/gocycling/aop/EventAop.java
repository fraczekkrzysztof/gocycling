package com.fraczekkrzysztof.gocycling.aop;

import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dto.event.EventDto;
import com.fraczekkrzysztof.gocycling.service.notification.EventNotificationGeneratorForClubMembers;
import com.fraczekkrzysztof.gocycling.service.notification.EventNotificationGeneratorForConfirmations;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Aspect
@Component
@RequiredArgsConstructor
public class EventAop {

    //    @Qualifier("updateEventNotificationGeneratorForConfirmation")
    private final EventNotificationGeneratorForConfirmations updateEventNotificationGeneratorForConfirmation;
    //    @Qualifier("cancelEventNotificationGeneratorForConfirmation")
    private final EventNotificationGeneratorForConfirmations cancelEventNotificationGeneratorForConfirmation;
    //    @Qualifier("newEventForClubNotificationGeneratorForClubMembers")
    private final EventNotificationGeneratorForClubMembers newEventForClubNotificationGeneratorForClubMembers;
    private final EventRepository eventRepository;

    @Pointcut("execution (* com.fraczekkrzysztof.gocycling.service.EventServiceV2.createEvent(..))")
    private void forEventInsert() {
        //define pointcut
    }

    @Pointcut("execution (* com.fraczekkrzysztof.gocycling.service.EventServiceV2.updateEvent(..))")
    private void forEventUpdate() {
        //define pointcut
    }

    @Pointcut("execution(* com.fraczekkrzysztof.gocycling.service.EventServiceV2.cancelEvent(..))")
    private void forEventCancel() {
        //define pointcut
    }

    @AfterReturning(pointcut = "forEventInsert()", returning = "retVal")
    private void forEventInsert(Object retVal) {
        if (retVal instanceof EventDto) {
            newEventForClubNotificationGeneratorForClubMembers.addEventIdAndIgnoreUser(((EventDto) retVal).getId(), ((EventDto) retVal).getUserId());
            }
    }

    @AfterReturning(pointcut = "forEventUpdate()", returning = "retVal")
    private void forEventUpdate(Object retVal) {
        if (retVal instanceof EventDto) {
            updateEventNotificationGeneratorForConfirmation.addEventIdAndIgnoreUser(((EventDto) retVal).getId(), ((EventDto) retVal).getUserId());
        }
    }

    @After("forEventCancel()")
    private void afterCancel(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Long) {
                long id = (long) arg;
                String userUid = eventRepository.findById(id).orElseThrow(() -> new NoSuchElementException("There is no event of id " + id)).getUser().getId();
                cancelEventNotificationGeneratorForConfirmation.addEventIdAndIgnoreUser(id, userUid);
            }
        }
    }

}
