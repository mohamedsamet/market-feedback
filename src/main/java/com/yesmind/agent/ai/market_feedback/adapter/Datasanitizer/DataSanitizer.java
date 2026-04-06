package com.yesmind.agent.ai.market_feedback.adapter.Datasanitizer;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.port.idatasanitizer.IDataSanitizer;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataSanitizer implements IDataSanitizer {

    private final PolicyFactory POLICY_HTML;
    private final PolicyFactory POLICY_XML;

    public DataSanitizer(
            @Value("${market.feedback.scraping[0].tags}") String htmlTags,
            @Value("${market.feedback.rss.xml-tags}") String xmlTags
    ) {
        // HTML Policy
        String[] htmlElements = htmlTags.split(",");
        this.POLICY_HTML = new HtmlPolicyBuilder()
                .allowElements(htmlElements)
                .allowAttributes("title").onElements("a")
                .allowUrlProtocols("http", "https")
                .requireRelNofollowOnLinks()
                .toFactory();

        // XML Policy
        String[] xmlElements = xmlTags.split(",");
        this.POLICY_XML = new HtmlPolicyBuilder()
                .allowElements(xmlElements)
                .toFactory();
    }

    public String sanitizeHtml(String html) {
        return POLICY_HTML.sanitize(html).trim();
    }

    public String sanitizeJson(String json) {
        return  json.replaceAll("[\\n\\r\\t]", "").trim();
    }

    public String sanitizeXml(String xml) {
        return  POLICY_XML.sanitize(xml).trim();
    }

    @Override
    public String sanitize(String data, String type) {
       switch (type.toUpperCase()) {
            case "JSON": return sanitizeJson(data);
            case "HTML": return sanitizeHtml(data);
            case "XML": return sanitizeXml(data);
            default: return data.trim();

        }
    }


}