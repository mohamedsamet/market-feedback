package com.yesmind.agent.ai.market_feedback.annoation;
import com.yesmind.agent.ai.market_feedback.domain.model.SourceType;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Sanitize {
 SourceType type() default SourceType.REST;
}
