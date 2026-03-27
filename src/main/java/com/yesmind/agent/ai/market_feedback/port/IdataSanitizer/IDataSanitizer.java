package com.yesmind.agent.ai.market_feedback.port.IdataSanitizer;

import lombok.Data;

public interface IDataSanitizer {
    String sanitize(String data);
}
