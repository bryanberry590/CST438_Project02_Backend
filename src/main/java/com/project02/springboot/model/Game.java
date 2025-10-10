package com.project02.springboot.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "game_date", nullable = false)
    private LocalDateTime gameDate;

    @Column(name = "home_team_id", nullable = false)
    private Long homeTeamId;

    @Column(name = "away_team_id", nullable = false)
    private Long awayTeamId;

    @Column(name = "home_team_name", nullable = false)
    private String homeTeamName;

    @Column(name = "away_team_name", nullable = false)
    private String awayTeamName;

    @Column(name = "home_team_nickname", nullable = false)
    private String homeTeamNickname;

    @Column(name = "away_team_nickname", nullable = false)
    private String awayTeamNickname;

    @Column(name = "home_team_logo_url", nullable = false, length = 1024)
    private String homeTeamLogoUrl;

    @Column(name = "away_team_logo_url", nullable = false, length = 1024)
    private String awayTeamLogoUrl;

    public Game() {}

    // basic constructor for each field
    public Game(Long gameId, LocalDateTime gameDate, Long homeTeamId, Long awayTeamId,
                String homeTeamName, String awayTeamName, String homeTeamNickname, 
                String awayTeamNickname, String homeTeamLogoUrl, String awayTeamLogoUrl) {
        this.gameId = gameId;
        this.gameDate = gameDate;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamNickname = homeTeamNickname;
        this.awayTeamNickname = awayTeamNickname;
        this.homeTeamLogoUrl = homeTeamLogoUrl;
        this.awayTeamLogoUrl = awayTeamLogoUrl;
    }

    // basic getters and setters for each 
    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }

    public LocalDateTime getGameDate() { return gameDate; }
    public void setGameDate(LocalDateTime gameDate) { this.gameDate = gameDate; }

    public Long getHomeTeamId() { return homeTeamId; }
    public void setHomeTeamId(Long homeTeamId) { this.homeTeamId = homeTeamId; }

    public Long getAwayTeamId() { return awayTeamId; }
    public void setAwayTeamId(Long awayTeamId) { this.awayTeamId = awayTeamId; }

    public String getHomeTeamName() { return homeTeamName; }
    public void setHomeTeamName(String homeTeamName) { this.homeTeamName = homeTeamName; }

    public String getAwayTeamName() { return awayTeamName; }
    public void setAwayTeamName(String awayTeamName) { this.awayTeamName = awayTeamName; }

    public String getHomeTeamNickname() { return homeTeamNickname; }
    public void setHomeTeamNickname(String homeTeamNickname) { this.homeTeamNickname = homeTeamNickname; }

    public String getAwayTeamNickname() { return awayTeamNickname; }
    public void setAwayTeamNickname(String awayTeamNickname) { this.awayTeamNickname = awayTeamNickname; }

    public String getHomeTeamLogoUrl() { return homeTeamLogoUrl; }
    public void setHomeTeamLogoUrl(String homeTeamLogoUrl) { this.homeTeamLogoUrl = homeTeamLogoUrl; }

    public String getAwayTeamLogoUrl() { return awayTeamLogoUrl; }
    public void setAwayTeamLogoUrl(String awayTeamLogoUrl) { this.awayTeamLogoUrl = awayTeamLogoUrl; }
}
