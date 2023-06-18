package com.examplespring.demo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScoreBoardController {
    private ScoreBoard scoreBoard;

    public ScoreBoardController(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    // Start a match
    public void startMatch(String homeTeam, String awayTeam) {
        scoreBoard.startMatch(homeTeam, awayTeam);
    }

    // Update the score
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        scoreBoard.updateScore(homeTeam, awayTeam, homeScore, awayScore);
    }

    // Finish a match
    public void finishMatch(String homeTeam, String awayTeam) {
        scoreBoard.finishMatch(homeTeam, awayTeam);
    }

    // Get a summary
    public List<Match> getSummary() {
        return scoreBoard.getSummary();
    }

    @GetMapping("/scoreboard")
    public String scoreboard_api() {
        ScoreBoard scoreboard = new ScoreBoard();
        ScoreBoardController controller = new ScoreBoardController(scoreboard);

        // Start matches
        controller.startMatch("Mexico", "Canada");
        controller.startMatch("Spain", "Brazil");
        controller.startMatch("Germany", "France");
        controller.startMatch("Uruguay", "Italy");
        controller.startMatch("Argentina", "Australia");

        // Update scores
        controller.updateScore("Mexico", "Canada", 0, 5);
        controller.updateScore("Spain", "Brazil", 10, 2);
        controller.updateScore("Germany", "France", 2, 2);
        controller.updateScore("Uruguay", "Italy", 6, 6);
        controller.updateScore("Argentina", "Australia", 3, 1);

        // Get summary
        List<Match> summary = controller.getSummary();

        // Print summary
        for (int i = 0; i < summary.size(); i++) {
            Match match = summary.get(i);
            System.out.printf("%d. %s %d - %s %d\n", i + 1, match.getHomeTeam(), match.getHomeScore(),
                    match.getAwayTeam(), match.getAwayScore());
        }

        // Afficher le résumé
        StringBuilder sb = new StringBuilder("Here the scoreboard: ");
        for (int i = 0; i < summary.size(); i++) {
            Match match = summary.get(i);
            sb.append(i + 1).append(". ")
                    .append(match.getHomeTeam()).append(" ")
                    .append(match.getHomeScore()).append(" - ")
                    .append(match.getAwayTeam()).append(" ")
                    .append(match.getAwayScore()).append("\n");
        }

        return sb.toString();

    }

    // Injectez la dépendance de la classe ScoreBoard ici

    // Définissez les endpoints REST pour les opérations requises
    // (démarrer un match, mettre à jour le score, terminer un match, obtenir un
    // résumé, etc.)
}
