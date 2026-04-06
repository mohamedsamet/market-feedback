package com.yesmind.agent.ai.market_feedback.business.annoation;

import jakarta.validation.Constraint;
import org.springframework.messaging.handler.annotation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SanitizeValidation.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Sanitize {
    String message() default "Invalid Sanitize";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
