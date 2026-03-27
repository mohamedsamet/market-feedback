package com.yesmind.agent.ai.market_feedback.adapter.DataSanitizer;

import com.yesmind.agent.ai.market_feedback.port.IdataSanitizer.IDataSanitizer;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataSanitizer  implements IDataSanitizer {
    private final PolicyFactory POLICY_HTML;
    private final PolicyFactory POLICY_XML;

    // Injection des balises depuis application.properties
    public DataSanitizer(
            @Value("${market.feedback.scraping.tags}") String htmlTags,
            @Value("${market.feedback.rss.xml-tags}") String xmlTags

    ) {
        // HTML Policy
        String[] htmlElements = htmlTags.split(",");
        this. POLICY_HTML = new HtmlPolicyBuilder().allowElements(htmlElements).allowAttributes( "title").onElements("a")
                .allowUrlProtocols("http", "https")
                .requireRelNofollowOnLinks()
                .toFactory();

        // XML Policy
        String[] xmlElements = xmlTags.split(",");
        this.POLICY_XML = new HtmlPolicyBuilder().allowElements(xmlElements).toFactory();
    }
    public String sanitizeHtml(String html) {

        return POLICY_HTML.sanitize(html).trim();
    }

    // JSON
    public String sanitizeJson(String json) {
        return json.replaceAll("[\\n\\r\\t]", "").trim();
    }

    // XML
    public String sanitizeXml(String xml) {

        return POLICY_XML.sanitize(xml).trim();
    }

    // Méthode centralisée selon le type de données
    public String sanitize(String data, String type) {
        switch (type.toUpperCase()) {
            case "HTML": return sanitizeHtml(data);
            case "JSON": return sanitizeJson(data);
            case "XML": return sanitizeXml(data);
            default: return data;
        }
    }
    @Override
    public String sanitize(String data) {
        return sanitizeHtml(data); // par défaut HTML
    }
}

