package com.project02.springboot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL AUTO_INCREMENT
    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "logo_url", nullable = false, length = 1024)
    private String logoUrl;

    public Team() {}

    public Team(Long teamId, String teamName, String nickname, String logoUrl) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.nickname = nickname;
        this.logoUrl = logoUrl;
    }

    public Long getTeamId() { return teamId; }
    public void setTeamId(Long teamId) { this.teamId = teamId; }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
}