package com.yesmind.agent.ai.market_feedback.adapter.processor;


import com.yesmind.agent.ai.market_feedback.annoation.Sanitize;
import com.yesmind.agent.ai.market_feedback.domain.model.SourceType;
import com.yesmind.agent.ai.market_feedback.port.idatasanitizer.IDataSanitizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SanitizeProcessor {

    private final IDataSanitizer sanitizer;

    public void process(Object obj, String type) {
        if (obj == null) return;

        // 1️⃣ Reflection → récupère tous les fields
        Arrays.stream(obj.getClass().getDeclaredFields())

                // 2️⃣ Reflection → garde uniquement ceux avec @Sanitize
                .filter(field -> field.isAnnotationPresent(Sanitize.class))

                // 3️⃣ traite chaque field annoté
                .forEach(field -> sanitizeField(obj, field, type));
    }

    private void sanitizeField(Object obj, Field field, String type) {

        // 4️⃣ Reflection → rend le field private accessible
        field.setAccessible(true);

        Optional.ofNullable(getFieldValue(obj, field))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(raw -> sanitizer.sanitize(raw, type))
                .ifPresent(cleaned -> setFieldValue(obj, field, cleaned));
    }

    private Object getFieldValue(Object obj, Field field) {
        try {
            // 5️⃣ Reflection → lit la valeur du field
            return field.get(obj);
        } catch (IllegalAccessException e) {
            log.error("❌ Cannot read field '{}'", field.getName(), e);
            return null;
        }
    }

    private void setFieldValue(Object obj, Field field, String value) {
        try {
            // 6️⃣ Reflection → modifie la valeur du field
            field.set(obj, value);
            log.debug("✅ '{}' sanitized", field.getName());
        } catch (IllegalAccessException e) {
            log.error("❌ Cannot write field '{}'", field.getName(), e);
        }
    }
}