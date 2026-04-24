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
@Document(collection = "market_events_summary")
public class MarketEventSummaryDocument {

    @Id
    private String id;
    private String theme;
    @Field("nombre_articles")
    private int nombreArticles;
    @Field("genere_le")
    private LocalDateTime genereLe;
    private String type;
    @Field("contenu_fr")
    private String contenuFr;
    @Field("contenu_en")
    private String contenuEn;
    @Field("pageDB")
    private int pageDB;
    @Field("totalPagesDB")
    private int totalPagesDB;
}