package com.yesmind.agent.ai.market_feedback.domain.model;

import lombok.*;
/* au lieu de passer 4 paramètres séparés entre les couches,
 on les emballe dans un seul objet. Le controller le crée,
  le service le reçoit, le repository l'utilise.*/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketEventFilter {
    private String search;
    private int page;
    private int size;
    private String source;}