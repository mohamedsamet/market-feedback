package com.yesmind.agent.ai.market_feedback.adapter.repository;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "market_events_summary")
public class MarketEventSummaryDocument {

    @Id
    private String id;

    private String famille;

    @Field("genere_le")
    private LocalDateTime genereLe;

    @Field("pageDB")
    private int pageDB;

    @Field("totalPagesDB")
    private int totalPagesDB;

    private List<ThemeSummary> themes;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ThemeSummary {
        private String theme;

        @Field("contenu_fr")
        private String contenuFr;

        @Field("contenu_en")
        private String contenuEn;
    }
}