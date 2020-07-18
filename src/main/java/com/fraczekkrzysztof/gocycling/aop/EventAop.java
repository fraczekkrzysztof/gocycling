package com.fraczekkrzysztof.gocycling.aop;

import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.service.notification.EventNotificationGeneratorForClubMembers;
import com.fraczekkrzysztof.gocycling.service.notification.EventNotificationGeneratorForConfirmations;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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

    @Pointcut("execution (* com.fraczekkrzysztof.gocycling.dao.EventRepository.save(..))")
    private void forEventInsertOrUpdate() {
        //define pointcut
    }

    @Pointcut("execution(* com.fraczekkrzysztof.gocycling.rest.EventController.cancelEvent(..))")
    private void forEventCancel() {
        //define pointcut
    }

    @Before("forEventInsertOrUpdate()")
    private void beforeUpdate(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Event) {
                Long id = ((Event) arg).getId();
                boolean isCanceled = ((Event) arg).isCanceled();
                if (id != 0 && !isCanceled) {
                    updateEventNotificationGeneratorForConfirmation.addEventIdAndIgnoreUser(((Event) arg).getId(), ((Event) arg).getCreatedBy());
                }
                if (id == 0 && !isCanceled) {
                    newEventForClubNotificationGeneratorForClubMembers.addEventIdAndIgnoreUser(((Event) arg).getId(), ((Event) arg).getCreatedBy());
                }
            }
        }
    }

    @Before("forEventCancel()")
    private void beforeCancel(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Long) {
                long id = (long) arg;
                String userUid = eventRepository.findById(id).orElseThrow(() -> new NoSuchElementException("There is no event of id " + id)).getCreatedBy();
                cancelEventNotificationGeneratorForConfirmation.addEventIdAndIgnoreUser(id, userUid);
            }
        }
    }

}
