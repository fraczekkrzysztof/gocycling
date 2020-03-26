package com.fraczekkrzysztof.gocycling.aop;

import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.service.notification.EventNotificationGenerator;
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


    private EventNotificationGenerator updateEventNotificationGenerator;
    private EventNotificationGenerator cancelEventNotificationGenerator;

    @Autowired
    public EventAop(@Qualifier("updateEventNotificationGenerator") EventNotificationGenerator updateEventNotificationGenerator,
                    @Qualifier("cancelEventNotificationGenerator") EventNotificationGenerator cancelEventNotificationGenerator){
        this.updateEventNotificationGenerator = updateEventNotificationGenerator;
        this.cancelEventNotificationGenerator = cancelEventNotificationGenerator;
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
           if (id != 0 && !isCanceled) updateEventNotificationGenerator.addEventId(((Event)arg).getId());
       }
      }
    }

    @Before("forEventCancel()")
    private  void beforeCancel(JoinPoint joinPoint){
        for (Object arg : joinPoint.getArgs()){
            if (arg instanceof Long){
                long id = (long)arg;
                cancelEventNotificationGenerator.addEventId(id);
            }
        }
    }

}
