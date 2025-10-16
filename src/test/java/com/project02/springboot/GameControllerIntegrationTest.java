package com.project02.springboot;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.project02.springboot.config.TestSecurityConfig;
import com.project02.springboot.model.Game;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
@DisplayName("Game Entity Tests")
class GameControllerIntegrationTest {

    @Test
    @DisplayName("Should create Game with default constructor")
    void testDefaultConstructor() {
        Game game = new Game();

        assertNotNull(game);
        assertNull(game.getGameId());
    }

    @Test
    @DisplayName("Should create Game with all fields constructor")
    void testAllArgsConstructor() {
        LocalDateTime gameDate = LocalDateTime.of(2025, 10, 15, 19, 30);

        Game game = new Game(
            1L,
            gameDate,
            10L,
            20L,
            "Los Angeles",
            "Boston",
            "Lakers",
            "Celtics",
            "https://example.com/lakers.png",
            "https://example.com/celtics.png"
        );

        assertEquals(1L, game.getGameId());
        assertEquals(gameDate, game.getGameDate());
        assertEquals(10L, game.getHomeTeamId());
        assertEquals(20L, game.getAwayTeamId());
        assertEquals("Los Angeles", game.getHomeTeamName());
        assertEquals("Boston", game.getAwayTeamName());
        assertEquals("Lakers", game.getHomeTeamNickname());
        assertEquals("Celtics", game.getAwayTeamNickname());
        assertEquals("https://example.com/lakers.png", game.getHomeTeamLogoUrl());
        assertEquals("https://example.com/celtics.png", game.getAwayTeamLogoUrl());
    }

    @Test
    @DisplayName("Should verify all setters work correctly")
    void testAllSetters() {
        Game game = new Game();
        LocalDateTime testDate = LocalDateTime.of(2025, 12, 25, 20, 0);

        game.setGameId(100L);
        game.setGameDate(testDate);
        game.setHomeTeamId(5L);
        game.setAwayTeamId(15L);
        game.setHomeTeamName("Chicago");
        game.setAwayTeamName("Miami");
        game.setHomeTeamNickname("Bulls");
        game.setAwayTeamNickname("Heat");
        game.setHomeTeamLogoUrl("https://bulls.com/logo.png");
        game.setAwayTeamLogoUrl("https://heat.com/logo.png");

        assertEquals(100L, game.getGameId());
        assertEquals(testDate, game.getGameDate());
        assertEquals(5L, game.getHomeTeamId());
        assertEquals(15L, game.getAwayTeamId());
        assertEquals("Chicago", game.getHomeTeamName());
        assertEquals("Miami", game.getAwayTeamName());
        assertEquals("Bulls", game.getHomeTeamNickname());
        assertEquals("Heat", game.getAwayTeamNickname());
        assertEquals("https://bulls.com/logo.png", game.getHomeTeamLogoUrl());
        assertEquals("https://heat.com/logo.png", game.getAwayTeamLogoUrl());
    }
}