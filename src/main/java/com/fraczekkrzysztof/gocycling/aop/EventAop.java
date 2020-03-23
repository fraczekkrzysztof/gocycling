package com.fraczekkrzysztof.gocycling.aop;

import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.service.notification.NotificationGenerator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EventAop {


    private NotificationGenerator eventNotificationGenerator;

    @Autowired
    public EventAop(@Qualifier("eventNotificationGenerator") NotificationGenerator eventNotificationGenerator){
        this.eventNotificationGenerator = eventNotificationGenerator;
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
           if (id != 0 && !isCanceled) eventNotificationGenerator.addEventIdToUpdate(((Event)arg).getId());
       }
      }
    }

    @Before("forEventCancel()")
    private  void beforeCancel(JoinPoint joinPoint){
        for (Object arg : joinPoint.getArgs()){
            if (arg instanceof Long){
                long id = (long)arg;
                eventNotificationGenerator.addEventIdToCancel(id);
            }
        }
    }

}
