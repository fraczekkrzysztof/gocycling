package com.fraczekkrzysztof.gocycling.aop;

import com.fraczekkrzysztof.gocycling.entity.Conversation;
import com.fraczekkrzysztof.gocycling.service.notification.NewConversationNotificationGenerator;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ConversationAop {

    @Qualifier("newConversationNotificationGenerator")
    private final NewConversationNotificationGenerator newConversationNotificationGenerator;

    @Pointcut("execution (* com.fraczekkrzysztof.gocycling.dao.ConversationRepository.save(..))")
    private void forEventUpdate(){
        //define pointcut
    }

    @Before("forEventUpdate()")
    private void beforeUpdate(JoinPoint joinPoint){
        for (Object arg : joinPoint.getArgs()){
            if (arg instanceof Conversation){
                Long id = ((Conversation)arg).getId();
                if (id == 0 ) newConversationNotificationGenerator.addEventIdAndIgnoreUser(((Conversation)arg).getEvent().getId(),((Conversation)arg).getUserUid());
            }
        }
    }
}
