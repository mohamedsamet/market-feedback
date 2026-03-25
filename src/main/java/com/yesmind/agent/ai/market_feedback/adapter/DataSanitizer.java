package com.yesmind.agent.ai.market_feedback.adapter;
import org.jsoup.Jsoup;


public class DataSanitizer {
    // Nettoie le texte HTML (supprime scripts et balises dangereuses)
        public static String sanitizeHtml(String input) {
            if (input == null) return null;
            return Jsoup.parse(input).text().trim();
        }



    }

