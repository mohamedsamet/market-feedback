package com.yesmind.agent.ai.market_feedback.adapter.consumer.rest;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Représente la configuration d'une seule source REST
@Getter
@Setter
public class RestSourceConfig {

    private String url;

    private String apiKey;

    private String method;

    private boolean enabled;
    private String query;
    private List<RestUrlParam> params=new ArrayList<>();

    private int timeout;
    private String contentPath;
    private String description;
    private int daysBack;
    private int retry;


}