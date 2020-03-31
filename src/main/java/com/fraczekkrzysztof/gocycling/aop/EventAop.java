package com.fraczekkrzysztof.gocycling.aop;

import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.service.notification.EventNotificationGenerator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Aspect
@Component
public class EventAop {


    private EventNotificationGenerator updateEventNotificationGenerator;
    private EventNotificationGenerator cancelEventNotificationGenerator;
    private EventRepository eventRepository;

    @Autowired
    public EventAop(@Qualifier("updateEventNotificationGenerator") EventNotificationGenerator updateEventNotificationGenerator,
                    @Qualifier("cancelEventNotificationGenerator") EventNotificationGenerator cancelEventNotificationGenerator,
                    EventRepository eventRepository){
        this.updateEventNotificationGenerator = updateEventNotificationGenerator;
        this.cancelEventNotificationGenerator = cancelEventNotificationGenerator;
        this.eventRepository = eventRepository;
    }

    @Pointcut("execution (* com.fraczekkrzysztof.gocycling.dao.EventRepository.save(..))")
    private void forEventUpdate(){
        //define pointcut
    }

    @Pointcut("execution(* com.fraczekkrzysztof.gocycling.rest.EventController.cancelEvent(..))")
    private void forEventCancel(){
        //define pointcut
    }

    @Before("forEventUpdate()")
    private void beforeUpdate(JoinPoint joinPoint){
      for (Object arg : joinPoint.getArgs()){
       if (arg instanceof Event){
           Long id = ((Event)arg).getId();
           boolean isCanceled = ((Event)arg).isCanceled();
           if (id != 0 && !isCanceled) updateEventNotificationGenerator.addEventIdAndIgnoreUser(((Event)arg).getId(),((Event)arg).getCreatedBy());
       }
      }
    }

    @Before("forEventCancel()")
    private  void beforeCancel(JoinPoint joinPoint){
        for (Object arg : joinPoint.getArgs()){
            if (arg instanceof Long){
                long id = (long)arg;
                String userUid = eventRepository.findById(id).orElseThrow(() -> new NoSuchElementException("There is no event of id "+ id)).getCreatedBy();
                cancelEventNotificationGenerator.addEventIdAndIgnoreUser(id,userUid);
            }
        }
    }

}
