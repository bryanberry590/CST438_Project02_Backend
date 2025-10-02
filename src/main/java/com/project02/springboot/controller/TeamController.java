package com.project02.springboot.controller;

import com.project02.springboot.model.Team;
import com.project02.springboot.Repository.TeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    private final TeamRepository repo;

    public TeamController(TeamRepository repo) {
        this.repo = repo;
    }
    
    @GetMapping
    public List<Team> getAllTeams() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Team> searchTeams(@RequestParam("name") String name,
                                  @RequestParam(value = "partial", defaultValue = "true") boolean partial) {
        return partial
                ? repo.findByTeamNameContainingIgnoreCase(name)
                : repo.findByTeamNameIgnoreCase(name);
    }

    @GetMapping("/name/{teamName}")
    public ResponseEntity<Team> getTeamByName(@PathVariable String teamName) {
        List<Team> teams = repo.findByTeamNameIgnoreCase(teamName);
        return teams.isEmpty() 
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(teams.get(0));
    }

    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<Team> getTeamByNickname(@PathVariable String nickname) {
        List<Team> teams = repo.findByNicknameIgnoreCase(nickname);
        return teams.isEmpty() 
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(teams.get(0));
    }

    // ===== POST ENDPOINTS (Create Operations) =====
    
    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        try {
            Team savedTeam = repo.save(team);
            return ResponseEntity.ok(savedTeam);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team teamDetails) {
        return repo.findById(id)
                .map(team -> {
                    team.setTeamName(teamDetails.getTeamName());
                    team.setNickname(teamDetails.getNickname());
                    team.setLogoUrl(teamDetails.getLogoUrl());
                    Team updatedTeam = repo.save(team);
                    return ResponseEntity.ok(updatedTeam);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // just to test if the api routes are running prop
    @GetMapping("/testing/")
    public String health() {
        return "API routes are running!";
    }
}