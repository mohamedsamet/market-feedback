package com.yesmind.agent.ai.market_feedback.adapter.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SanitizeAspect {

    private final SanitizeProcessor sanitizeProcessor;

    @Around("execution(* com.yesmind..*(..))") // intercepte les méthodes
    public Object sanitizeAround(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result = joinPoint.proceed(); // exécuter la méthode

        // 🔹 Si le résultat n'est pas null → on le sanitize
        if (result != null) {
            if (result instanceof Iterable<?>) {
                for (Object obj : (Iterable<?>) result) {
                    sanitizeProcessor.process(obj);
                }
            } else {
                sanitizeProcessor.process(result);
            }
        }

        return result;
    }
}