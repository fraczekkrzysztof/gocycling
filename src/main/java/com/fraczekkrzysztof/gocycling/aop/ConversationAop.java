package com.fraczekkrzysztof.gocycling.aop;

import com.fraczekkrzysztof.gocycling.dto.event.ConversationDto;
import com.fraczekkrzysztof.gocycling.service.notification.NewConversationNotificationGeneratorForConfirmation;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Aspect
@Component
@RequiredArgsConstructor
public class ConversationAop {

    @Qualifier("newConversationNotificationGeneratorForConfirmation")
    private final NewConversationNotificationGeneratorForConfirmation newConversationNotificationGeneratorForConfirmation;

    @Pointcut("execution (* com.fraczekkrzysztof.gocycling.service.ConversationServiceV2.addConversation(..))")
    private void forEventUpdate() {
        //define pointcut
    }

    @AfterReturning(pointcut = "forEventUpdate()", returning = "retVal")
    private void afterUpdate(JoinPoint joinPoint, Object retVal) {
        Long eventId = null;
        String userUid = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Long) {
                eventId = (Long) arg;
            }
        }
        if (retVal instanceof ConversationDto) {
            userUid = ((ConversationDto) retVal).getUserId();
        }

        if (!ObjectUtils.isEmpty(eventId) && !StringUtils.isEmpty(userUid)) {
            newConversationNotificationGeneratorForConfirmation.addEventIdAndIgnoreUser(eventId, userUid);
        }
    }
}
