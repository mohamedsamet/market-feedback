package com.yesmind.agent.ai.market_feedback.adapter.repository;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "market_events_analyses")
public class MarketEventAnalysisDocument {

    @Id
    private String id;

    @Field("source_id")
    private String sourceId;

    private String theme;
    private String type;

    @Field("genere_le")
    private LocalDateTime genereLe;

    private String prediction;
    private String proposition;
    private String ton;
    private String urgence;
    private String categorie;

    @Field("analyse_le")
    private LocalDateTime analyseEl;
}