package com.yesmind.agent.ai.market_feedback.business.annoation;

import com.yesmind.agent.ai.market_feedback.port.idatasanitizer.IDataSanitizer;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SanitizeValidation implements ConstraintValidator<Sanitize, String> {

    private final IDataSanitizer dataSanitizer;

    @Override
    public boolean isValid(String data, ConstraintValidatorContext constraintValidatorContext) {

        return dataSanitizer.isSafe(data);
    }
}
