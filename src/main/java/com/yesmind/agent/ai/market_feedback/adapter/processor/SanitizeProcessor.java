
package com.yesmind.agent.ai.market_feedback.adapter.processor;

import com.yesmind.agent.ai.market_feedback.adapter.Datasanitizer.DataSanitizer;
import com.yesmind.agent.ai.market_feedback.annoation.Sanitize;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.port.datasource.DataSourceConsumable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SanitizeProcessor implements BeanPostProcessor {

    private final DataSanitizer dataSanitizer;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        // Wrap only consumers
        if (!(bean instanceof DataSourceConsumable target)) {
            return bean;
        }

        // Wrap only beans annotated with @Sanitize
        Sanitize ann = bean.getClass().getAnnotation(Sanitize.class);
        if (ann == null) {
            return bean;
        }

        final var type = ann.type();
        log.debug("SanitizeProcessor enabled for bean={}, type={}", beanName, type);

        return Proxy.newProxyInstance(
                bean.getClass().getClassLoader(),
                new Class[]{DataSourceConsumable.class},
                (proxy, method, args) -> {

                    Object result = method.invoke(target, args);

                    // Sanitize only the result of consume()
                    if ("consume".equals(method.getName()) && result instanceof List<?> list) {
                        list.stream()
                                .filter(MarketEvent.class::isInstance)
                                .map(MarketEvent.class::cast)
                                .filter(e -> e.getContent() != null && !e.getContent().isBlank())
                                .forEach(e -> e.setContent(
                                        dataSanitizer.sanitize(e.getContent(), type)
                                ));
                    }

                    return result;
                }
        );
    }
}