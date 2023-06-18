package com.examplespring.demo.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examplespring.demo.Match;
import com.examplespring.demo.ScoreBoard;
import com.examplespring.demo.Request.MatchRequest;
import com.examplespring.demo.Request.ScoreUpdateRequest;

@RestController
public class ScoreBoardController {
    private ScoreBoard scoreBoard;

    public ScoreBoardController(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    @PostMapping("/startMatch")
    public void startMatch(@RequestBody MatchRequest matchRequest) {
        String homeTeam = matchRequest.getHomeTeam();
        String awayTeam = matchRequest.getAwayTeam();
        scoreBoard.startMatch(homeTeam, awayTeam);
    }

    @PostMapping("/updateScore")
    public void updateScore(@RequestBody ScoreUpdateRequest scoreUpdateRequest) {
        String homeTeam = scoreUpdateRequest.getHomeTeam();
        String awayTeam = scoreUpdateRequest.getAwayTeam();
        int homeScore = scoreUpdateRequest.getHomeScore();
        int awayScore = scoreUpdateRequest.getAwayScore();
        scoreBoard.updateScore(homeTeam, awayTeam, homeScore, awayScore);
    }

    @PostMapping("/finishMatch")
    public void finishMatch(@RequestBody MatchRequest matchRequest) {
        String homeTeam = matchRequest.getHomeTeam();
        String awayTeam = matchRequest.getAwayTeam();
        scoreBoard.finishMatch(homeTeam, awayTeam);
    }

    @GetMapping("/getSummary")
    public List<Match> getSummary() {
        return scoreBoard.getSummary();
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

        StringBuilder sb = new StringBuilder("Here the scoreboard v3 : \n\n\n");
        for (int i = 0; i < summary.size(); i++) {
            Match match = summary.get(i);
            sb.append(" // \n\n\n"); // Ligne de séparation
            String matchInfo = String.format("%d. %s %d - %s %d\n\n\n",
                    i + 1, match.getHomeTeam(), match.getHomeScore(), match.getAwayTeam(), match.getAwayScore());
            sb.append(matchInfo);
            sb.append(" // \n\n\n");
        }
        String result = sb.toString();
        return result;

    }
}
