package com.yesmind.agent.ai.market_feedback.adapter.datasanitizer;
import com.yesmind.agent.ai.market_feedback.port.IdataSanitizer.IDataSanitizer;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

public class DataSanitizer implements IDataSanitizer {

    private static final PolicyFactory POLICY_html = new HtmlPolicyBuilder()
            .allowElements("p", "br", "b", "strong", "i", "em", "u", "ul", "ol", "li", "blockquote", "h1", "h2", "h3", "a")//on va faire un parametre dans proproties
            .allowAttributes("href", "title").onElements("a")
            .allowUrlProtocols("http", "https")
            .requireRelNofollowOnLinks()
            .toFactory();
    private static final PolicyFactory policy_Xml = new HtmlPolicyBuilder()
            .allowElements("title", "description","articles") // seules ces balises sont conservées puis on va le mettre comme parametre dans proproties
            .toFactory();

    // HTML
    public String sanitizeHtml(String html) {

        return POLICY_html.sanitize(html).trim();
    }

    // JSON
    public String sanitizeJson(String json) {
        return json.replaceAll("[\\n\\r\\t]", "").trim();
    }

    // XML
    public String sanitizeXml(String xml) {

        return policy_Xml.sanitize(xml).trim();
    }

    // Méthode centralisée selon le type de données
    public String sanitize(String data, String type) {
        switch (type.toUpperCase()) { // toujours mettre en majuscule pour éviter les erreurs
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