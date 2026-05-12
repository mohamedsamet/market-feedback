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
@Document(collection = "market_events_analyses")
public class MarketEventAnalysisDocument {

    @Id
    private String id;

    private String famille;

    @Field("genere_le")
    private LocalDateTime genereLe;

    @Field("total_themes")
    private int totalThemes;

    @Field("pageDB")
    private int pageDB;

    @Field("totalPagesDB")
    private int totalPagesDB;

    @Field("analyse_le")
    private LocalDateTime analyseEl;

    private List<ThemeDocument> themes;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThemeDocument {

        private String theme;
        private String prediction;
        private String proposition;
        private String ton;
        private String urgence;
        private String categorie;

        @Field("analyse_le")
        private LocalDateTime analyseEl;
    }
}