package com.project02.springboot.controller;

import com.project02.springboot.Repository.GameRepository;
import com.project02.springboot.model.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameRepository gameRepository;

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
    
    @GetMapping
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        return gameRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/date-range")
    public List<Game> getGamesByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) Long teamId) {
        
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
        
        if (teamId != null) {
            return gameRepository.findByGameDateBetweenAndTeamId(start, end, teamId);
        } else {
            return gameRepository.findByGameDateBetween(start, end);
        }
    }

    @GetMapping("/team/{teamId}")
    public List<Game> getGamesByTeam(@PathVariable Long teamId) {
        return gameRepository.findByHomeTeamIdOrAwayTeamId(teamId, teamId);
    }
    
    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        try {
            Game savedGame = gameRepository.save(game);
            return ResponseEntity.ok(savedGame);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // bulk for creating multiple games at once
    @PostMapping("/bulk")
    public ResponseEntity<List<Game>> createGames(@RequestBody List<Game> games) {
        try {
            List<Game> savedGames = gameRepository.saveAll(games);
            return ResponseEntity.ok(savedGames);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @RequestBody Game gameDetails) {
        return gameRepository.findById(id)
                .map(game -> {
                    game.setGameDate(gameDetails.getGameDate());
                    game.setHomeTeamId(gameDetails.getHomeTeamId());
                    game.setAwayTeamId(gameDetails.getAwayTeamId());
                    game.setHomeTeamName(gameDetails.getHomeTeamName());
                    game.setAwayTeamName(gameDetails.getAwayTeamName());
                    game.setHomeTeamNickname(gameDetails.getHomeTeamNickname());
                    game.setAwayTeamNickname(gameDetails.getAwayTeamNickname());
                    game.setHomeTeamLogoUrl(gameDetails.getHomeTeamLogoUrl());
                    game.setAwayTeamLogoUrl(gameDetails.getAwayTeamLogoUrl());
                    Game updatedGame = gameRepository.save(game);
                    return ResponseEntity.ok(updatedGame);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        if (gameRepository.existsById(id)) {
            gameRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/testing")
    public String health() {
        return "Game API routes are running!";
    }
}
