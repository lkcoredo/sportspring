package com.examplespring.demo.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public List<Match> sortMatchesByClosestResults() {
        List<Match> summary = scoreBoard.getSummary();

        // Sort the matches by closest results
        Collections.sort(summary, new Comparator<Match>() {
            @Override
            public int compare(Match match1, Match match2) {
                int diff1 = Math.abs(match1.getHomeScore() - match1.getAwayScore());
                int diff2 = Math.abs(match2.getHomeScore() - match2.getAwayScore());
                return Integer.compare(diff1, diff2);
            }
        });

        return summary;
    }

    public List<Match> sortMatchesByHigherResults() {
        List<Match> summary = scoreBoard.getSummary();

        // Sort the matches by higher results
        Collections.sort(summary, new Comparator<Match>() {
            @Override
            public int compare(Match match1, Match match2) {
                int diff1 = match2.getHomeScore() - match2.getAwayScore();
                int diff2 = match1.getHomeScore() - match1.getAwayScore();
                return Integer.compare(diff1, diff2);
            }
        });

        return summary;
    }

    public List<Match> sortMatchesByAlphabeticalOrderHomeTeam() {
        List<Match> summary = scoreBoard.getSummary();

        // Sort the matches by home team name alphabetically
        Collections.sort(summary, new Comparator<Match>() {
            @Override
            public int compare(Match match1, Match match2) {
                return match1.getHomeTeam().compareToIgnoreCase(match2.getHomeTeam());
            }
        });

        return summary;
    }

    public List<Match> sortMatchesByAlphabeticalOrderAwayTeam() {
        List<Match> summary = scoreBoard.getSummary();

        // Sort the matches by home team name alphabetically
        Collections.sort(summary, new Comparator<Match>() {
            @Override
            public int compare(Match match1, Match match2) {
                return match1.getAwayTeam().compareToIgnoreCase(match2.getAwayTeam());
            }
        });

        return summary;
    }

    public List<Match> sortMatchesByWinner() {
        List<Match> summary = new ArrayList<>(scoreBoard.getSummary());

        Collections.sort(summary, new Comparator<Match>() {
            @Override
            public int compare(Match match1, Match match2) {
                String winner1 = determineWinner(match1);
                String winner2 = determineWinner(match2);
                if (winner1.equalsIgnoreCase(winner2)) {
                    return 0;
                } else if (winner1.equalsIgnoreCase("Draw")) {
                    return 1;
                } else if (winner2.equalsIgnoreCase("Draw")) {
                    return -1;
                } else {
                    return winner1.compareToIgnoreCase(winner2);
                }
            }

            private String determineWinner(Match match) {
                int homeScore = match.getHomeScore();
                int awayScore = match.getAwayScore();
                if (homeScore > awayScore) {
                    return match.getHomeTeam();
                } else if (awayScore > homeScore) {
                    return match.getAwayTeam();
                } else {
                    return "Draw";
                }
            }
        });

        return summary;
    }

}
