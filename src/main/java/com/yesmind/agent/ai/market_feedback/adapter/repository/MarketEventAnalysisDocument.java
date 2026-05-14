package com.yesmind.agent.ai.market_feedback.adapter.repository;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    private Integer totalThemes;

    @Field("pageDB")
    private Integer pageDB;

    @Field("totalPagesDB")
    private Integer totalPagesDB;

    @Field("analyse_le")
    private LocalDateTime analyseLe;

    private List<ThemeAnalysis> themes;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThemeAnalysis {

        private String theme;
        private String prediction;
        private Map<String, String> propositions;
        private String ton;
        private String urgence;
        private String categorie;

        @Field("analyse_le")
        private LocalDateTime analyseLe;
    }
}