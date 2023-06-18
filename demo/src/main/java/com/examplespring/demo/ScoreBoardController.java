package com.examplespring.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScoreBoardController {
    private ScoreBoard scoreBoard;

    @GetMapping("/scoreboard")
    public String scoreboard_api() {
        return "Here the scoreboard : " + scoreBoard;
    }

    // Injectez la dépendance de la classe ScoreBoard ici

    // Définissez les endpoints REST pour les opérations requises
    // (démarrer un match, mettre à jour le score, terminer un match, obtenir un
    // résumé, etc.)
}
