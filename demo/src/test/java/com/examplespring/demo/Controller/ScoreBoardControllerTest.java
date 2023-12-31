package com.examplespring.demo.Controller;

import com.examplespring.demo.Match;
import com.examplespring.demo.ScoreBoard;
import com.examplespring.demo.Request.MatchRequest;
import com.examplespring.demo.Request.ScoreUpdateRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreBoardControllerTest {
    private ScoreBoardController controller;
    private ScoreBoard scoreBoard;

    @BeforeEach
    void setUp() {
        scoreBoard = new ScoreBoard();
        controller = new ScoreBoardController(scoreBoard);
    }

    @Test
    void testStartMatch() {
        String homeTeam = "Mexico";
        String awayTeam = "Canada";

        controller.startMatch(homeTeam, awayTeam);

        List<Match> summary = controller.getSummary();
        assertEquals(1, summary.size());

        Match match = summary.get(0);
        assertEquals(homeTeam, match.getHomeTeam());
        assertEquals(awayTeam, match.getAwayTeam());
    }

    @Test
    void testUpdateScore() {
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        int homeScore = 0;
        int awayScore = 5;

        controller.updateScore(homeTeam, awayTeam, homeScore, awayScore);

        List<Match> summary = controller.getSummary();
        assertEquals(1, summary.size());

        Match match = summary.get(0);
        assertEquals(homeTeam, match.getHomeTeam());
        assertEquals(awayTeam, match.getAwayTeam());
        assertEquals(homeScore, match.getHomeScore());
        assertEquals(awayScore, match.getAwayScore());
    }

    @Test
    void testFinishMatch() {
        String homeTeam = "Mexico";
        String awayTeam = "Canada";

        controller.startMatch(homeTeam, awayTeam);
        controller.finishMatch(homeTeam, awayTeam);

        List<Match> summary = controller.getSummary();
        assertEquals(0, summary.size());
    }

    @Test
    void testGetSummary() {
        String homeTeam = "Mexico";
        String awayTeam = "Canada";

        controller.startMatch(homeTeam, awayTeam);

        List<Match> summary = controller.getSummary();
        assertEquals(1, summary.size());

        Match match = summary.get(0);
        assertEquals(homeTeam, match.getHomeTeam());
        assertEquals(awayTeam, match.getAwayTeam());
    }

    @Test
    void testSortMatchesByWinner() {
        // Add matches to the scoreboard
        controller.startMatch(new MatchRequest("Mexico", "Canada"));
        controller.updateScore(new ScoreUpdateRequest("Mexico", "Canada", 5, 3));
        controller.finishMatch(new MatchRequest("Mexico", "Canada"));

        controller.startMatch(new MatchRequest("Spain", "Brazil"));
        controller.updateScore(new ScoreUpdateRequest("Spain", "Brazil", 2, 2));
        controller.finishMatch(new MatchRequest("Spain", "Brazil"));

        controller.startMatch(new MatchRequest("Germany", "France"));
        controller.updateScore(new ScoreUpdateRequest("Germany", "France", 1, 2));
        controller.finishMatch(new MatchRequest("Germany", "France"));

        // Sort matches by winner
        List<Match> sortedMatches = controller.sortMatchesByWinner();

        // Verify the order
        assertEquals("Germany", sortedMatches.get(0).getWinner());
        assertEquals("Mexico", sortedMatches.get(1).getWinner());
        assertEquals("Spain", sortedMatches.get(2).getWinner());
    }

    @Test
    void testSortMatchesByAlphabeticalOrder() {
        // Add matches to the scoreboard
        controller.startMatch(new MatchRequest("Spain", "Brazil"));
        controller.updateScore(new ScoreUpdateRequest("Spain", "Brazil", 2, 2));
        controller.finishMatch(new MatchRequest("Spain", "Brazil"));

        controller.startMatch(new MatchRequest("Germany", "France"));
        controller.updateScore(new ScoreUpdateRequest("Germany", "France", 1, 2));
        controller.finishMatch(new MatchRequest("Germany", "France"));

        controller.startMatch(new MatchRequest("Mexico", "Canada"));
        controller.updateScore(new ScoreUpdateRequest("Mexico", "Canada", 5, 3));
        controller.finishMatch(new MatchRequest("Mexico", "Canada"));

        // Sort matches by alphabetical order
        List<Match> sortedMatches = controller.sortMatchesByAlphabeticalOrder();

        // Verify the order
        assertEquals("Germany", sortedMatches.get(0).getHomeTeam());
        assertEquals("Mexico", sortedMatches.get(1).getHomeTeam());
        assertEquals("Spain", sortedMatches.get(2).getHomeTeam());
    }

    @Test
    void testSortMatchesByHigherResults() {
        // Add matches to the scoreboard
        controller.startMatch(new MatchRequest("Germany", "France"));
        controller.updateScore(new ScoreUpdateRequest("Germany", "France", 1, 2));
        controller.finishMatch(new MatchRequest("Germany", "France"));

        controller.startMatch(new MatchRequest("Mexico", "Canada"));
        controller.updateScore(new ScoreUpdateRequest("Mexico", "Canada", 5, 3));
        controller.finishMatch(new MatchRequest("Mexico", "Canada"));

        controller.startMatch(new MatchRequest("Spain", "Brazil"));
        controller.updateScore(new ScoreUpdateRequest("Spain", "Brazil", 2, 2));
        controller.finishMatch(new MatchRequest("Spain", "Brazil"));

        // Sort matches by higher results
        List<Match> sortedMatches = controller.sortMatchesByHigherResults();

        // Verify the order
        assertEquals(5, sortedMatches.get(0).getHomeScore());
        assertEquals(2, sortedMatches.get(1).getHomeScore());
        assertEquals(1, sortedMatches.get(2).getHomeScore());
    }

    @Test
    void testSortMatchesByClosestResults() {
        // Add matches to the scoreboard
        controller.startMatch(new MatchRequest("Mexico", "Canada"));
        controller.updateScore(new ScoreUpdateRequest("Mexico", "Canada", 5, 3));
        controller.finishMatch(new MatchRequest("Mexico", "Canada"));

        controller.startMatch(new MatchRequest("Spain", "Brazil"));
        controller.updateScore(new ScoreUpdateRequest("Spain", "Brazil", 2, 2));
        controller.finishMatch(new MatchRequest("Spain", "Brazil"));

        controller.startMatch(new MatchRequest("Germany", "France"));
        controller.updateScore(new ScoreUpdateRequest("Germany", "France", 1, 2));
        controller.finishMatch(new MatchRequest("Germany", "France"));

        // Sort matches by closest results
        List<Match> sortedMatches = controller.sortMatchesByClosestResults();

        // Verify the order
        assertEquals(2, sortedMatches.get(0).getScoreDifference());
        assertEquals(2, sortedMatches.get(1).getScoreDifference());
        assertEquals(3, sortedMatches.get(2).getScoreDifference());
    }
}
