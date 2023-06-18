package com.examplespring.demo.Controller;

import com.examplespring.demo.Match;
import com.examplespring.demo.ScoreBoard;
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
}
