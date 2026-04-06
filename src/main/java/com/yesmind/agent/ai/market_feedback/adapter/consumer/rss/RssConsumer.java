package com.yesmind.agent.ai.market_feedback.adapter.consumer.rss;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.yesmind.agent.ai.market_feedback.annoation.Sanitize;
import com.yesmind.agent.ai.market_feedback.annoation.SanitizeType;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.domain.model.SourceType;
import com.yesmind.agent.ai.market_feedback.port.datasource.DataSourceConsumable;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
@Sanitize(type="RSS")
@Component
@RequiredArgsConstructor
public class RssConsumer implements DataSourceConsumable {
    // s'engage à respecter le contrat IDataSourceConsumer. Donc il doit obligatoirement avoir consume() et getSourceName()
    private final RestTemplate restTemplate = new RestTemplate();
    private final RssConsumerConfig config;
    private final XmlMapper xmlMapper;//version XML de ObjectMapper
    private static final Logger log = LoggerFactory.getLogger(RssConsumer.class);

    @Override
    @Sanitize(type = SanitizeType.HTML)
    public List<MarketEvent> consume() {
        List<MarketEvent> allEvents = new ArrayList<>();
        log.info("Lecture du flux RSS...");
        config.getRss().forEach(source -> {
            if (!source.isEnabled()) {
                log.info("⏭️ Source désactivée : {}", source.getDescription());
                return;
            }
            String url = source.getUrl();
            log.info("Appel flux RSS : {}", source.getDescription());
            String responseXml = restTemplate.getForObject(url, String.class);
            try {
                Object content = xmlMapper.readValue(Objects.requireNonNull(responseXml), Object.class);
                MarketEvent event = new MarketEvent();
                event.setContent(content.toString());
                event.setId(UUID.randomUUID().toString());
                event.setSourceUrl(url);
                event.setCreationDate(LocalDateTime.now());
                event.setSourceType(SourceType.RSS);
                allEvents.add(event);
                log.info("MarketEvent RSS créé depuis : {}", source.getDescription());
            } catch (Exception e) {
                log.error("Erreur parsing RSS depuis {} : {}", source.getDescription(), e.getMessage());

            }
        });
        return allEvents;

    }
        @Override
        public String getSourceName () {
            return "RSS";
        }
    }




