package com.examplespring.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ScoreBoard {
    private List<Match> matches;
    private List<Match> scoreSummary;

    public ScoreBoard() {
        this.matches = new ArrayList<>();
        this.scoreSummary = new ArrayList<>();
    }

    public void startMatch(String homeTeam, String awayTeam) {
        Match match = new Match(homeTeam, awayTeam);
        matches.add(match);
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        for (Match match : matches) {
            if (match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam)) {
                match.updateScore(homeScore, awayScore);
                break;
            }
        }
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        matches.removeIf(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam));
    }

    public List<Match> getSummary() {
        scoreSummary.clear();
        scoreSummary.addAll(matches);
        Collections.sort(scoreSummary, Comparator.comparingInt((Match m) -> m.getHomeScore() + m.getAwayScore())
                .thenComparing(Collections.reverseOrder(Comparator.comparingInt(System::identityHashCode))));
        return scoreSummary;
    }

    // Méthodes pour démarrer un match, mettre à jour le score, terminer un match,
    // obtenir un résumé, etc.
}
