package com.yesmind.agent.ai.market_feedback.port.idatasanitizer;


import com.yesmind.agent.ai.market_feedback.domain.model.SourceType;

public interface IDataSanitizer {

    String sanitize(String data, SourceType type);

    default boolean isSafe(String data) {
        return true;
    };

}
