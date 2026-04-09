package com.yesmind.agent.ai.market_feedback.adapter.web;

import org.springframework.web.bind.annotation.CrossOrigin;

// Classe parent de tous les controllers
// centralise la configuration CORS en un seul endroit
@CrossOrigin(origins = "http://localhost:5173")
public abstract class MainController {
}