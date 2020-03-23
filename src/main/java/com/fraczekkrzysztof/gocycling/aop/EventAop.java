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


    private NotificationGenerator updateNotificationGenerator;

    @Autowired
    public EventAop(@Qualifier("updateEventNotificationGenerator") NotificationGenerator updateNotificationGenerator){
        this.updateNotificationGenerator = updateNotificationGenerator;
    }

    @Pointcut("execution (* com.fraczekkrzysztof.gocycling.dao.EventRepository.save(..))")
    private void forEventUpdate(){
        //define pointcut
    }

    @Before("forEventUpdate()")
    private void beforeUpdate(JoinPoint joinPoint){
      for (Object arg : joinPoint.getArgs()){
       if (arg instanceof Event){
           Long id = ((Event)arg).getId();
           if (id != 0) updateNotificationGenerator.addEventId(((Event)arg).getId());
       }
      }
    }

}
