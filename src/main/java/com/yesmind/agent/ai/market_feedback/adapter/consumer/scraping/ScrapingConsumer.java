package com.yesmind.agent.ai.market_feedback.adapter.consumer.scraping;

import com.yesmind.agent.ai.market_feedback.port.datasource.DataSourceConsumable;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.domain.model.SourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@RequiredArgsConstructor
@Component
public class ScrapingConsumer implements DataSourceConsumable {

    private static final Logger log = LoggerFactory.getLogger(ScrapingConsumer.class);
    private final ScrapingConsumerConfig config;
    @Override
    public List<MarketEvent> consume() {
        List<MarketEvent> allEvents = new ArrayList<>();
        log.info("Scraping en cours...");
        config.getUrls().forEach(url -> {
            try {
                log.info("Scraping URL : {}", url);

                Document doc = Jsoup.connect(url).get();
            //Jsoup transforme le HTML → objet manipulable (Document)

                String content = doc.select("h1, h2, h3, p").text();

                MarketEvent event = new MarketEvent();
                event.setId(UUID.randomUUID().toString());
                event.setContent(content);
                event.setSourceUrl(url);
                event.setCreationDate(LocalDateTime.now());
                event.setSourceType(SourceType.SCRAPING);

                allEvents.add(event);

                log.info(" MarketEvent SCRAPING créé depuis : {}", url);

            } catch (Exception e) {
                log.error("Erreur scraping depuis {} : {}", url, e.getMessage());
            }
        });

        return allEvents;
    }

    @Override
    public String getSourceName() {
        return "SCRAPING";
    }
}