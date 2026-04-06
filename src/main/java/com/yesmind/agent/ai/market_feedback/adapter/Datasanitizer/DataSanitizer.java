package com.yesmind.agent.ai.market_feedback.adapter.Datasanitizer;
import com.yesmind.agent.ai.market_feedback.domain.model.SourceType;
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
            @Value("${sanitizer.html.allowed-elements:p,br,strong,em,ul,ol,li,a}") String htmlTags,
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
    public String sanitize(String data, SourceType type) {
        if (data == null) return null;

        return switch (type) {
            case SCRAPING -> sanitizeHtml(data);
            case REST     -> sanitizeJson(data);
            case RSS      -> sanitizeXml(data);
            default       -> data.trim();

        };
    }


}