package com.examplespring.demo;

public class Match {
    private String homeTeam;
    private String awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = 0;
        this.awayTeamScore = 0;
    }

    public void updateScore(int homeScore, int awayScore) {
        this.homeTeamScore = homeScore;
        this.awayTeamScore = awayScore;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeTeamScore;
    }

    public int getAwayScore() {
        return awayTeamScore;
    }
}
