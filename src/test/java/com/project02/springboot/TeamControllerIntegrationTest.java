package com.project02.springboot;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project02.springboot.Repository.TeamRepository;
import com.project02.springboot.model.Team;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class TeamControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Team team1;
    private Team team2;

    @BeforeEach
    void setUp() {
        teamRepository.deleteAll();

        team1 = new Team();
        team1.setTeamName("Los Angeles Lakers");
        team1.setNickname("Lakers");
        team1.setLogoUrl("https://example.com/lakers.png");
        team1 = teamRepository.save(team1);

        team2 = new Team();
        team2.setTeamName("Boston Celtics");
        team2.setNickname("Celtics");
        team2.setLogoUrl("https://example.com/celtics.png");
        team2 = teamRepository.save(team2);
    }

    // @Test
    // @Order(1)
    // void healthCheck_ShouldReturnSuccessMessage() throws Exception {
    //     mockMvc.perform(get("/api/teams/testing/"))
    //             .andExpect(status().isOk())
    //             .andExpect(content().string("API routes are running!"));
    // }

    @Test
    @Order(2)
    void getAllTeams_ShouldReturnListOfTeams() throws Exception {
        mockMvc.perform(get("/api/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].teamName", 
                    containsInAnyOrder("Los Angeles Lakers", "Boston Celtics")));
    }

    @Test
    @Order(3)
    void getTeamById_WhenTeamExists_ShouldReturnTeam() throws Exception {
        mockMvc.perform(get("/api/teams/" + team1.getTeamId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamName", is("Los Angeles Lakers")))
                .andExpect(jsonPath("$.nickname", is("Lakers")));
    }

    @Test
    @Order(4)
    void createTeam_WithValidData_ShouldReturnCreatedTeam() throws Exception {
        Team newTeam = new Team();
        newTeam.setTeamName("Chicago Bulls");
        newTeam.setNickname("Bulls");
        newTeam.setLogoUrl("https://example.com/bulls.png");

        mockMvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTeam)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamId").exists())
                .andExpect(jsonPath("$.teamName", is("Chicago Bulls")))
                .andExpect(jsonPath("$.nickname", is("Bulls")));
    }

    @Test
    @Order(5)
    void updateTeam_WhenTeamExists_ShouldReturnUpdatedTeam() throws Exception {
        Team updatedDetails = new Team();
        updatedDetails.setTeamName("LA Lakers Updated");
        updatedDetails.setNickname("Lakers Updated");
        updatedDetails.setLogoUrl("https://example.com/new-lakers.png");

        mockMvc.perform(put("/api/teams/" + team1.getTeamId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamName", is("LA Lakers Updated")));
    }

    @Test
    @Order(6)
    void deleteTeam_WhenTeamExists_ShouldDeleteTeam() throws Exception {
        Long teamId = team1.getTeamId();

        mockMvc.perform(delete("/api/teams/" + teamId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/teams/" + teamId))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    void searchTeams_WithPartialMatch_ShouldReturnMatchingTeams() throws Exception {
        mockMvc.perform(get("/api/teams/search")
                        .param("name", "Lakers")
                        .param("partial", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].teamName", is("Los Angeles Lakers")));
    }

    @Test
    @Order(8)
    void getTeamByName_WhenTeamExists_ShouldReturnTeam() throws Exception {
        mockMvc.perform(get("/api/teams/name/Los Angeles Lakers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamName", is("Los Angeles Lakers")))
                .andExpect(jsonPath("$.nickname", is("Lakers")));
    }

    @Test
    @Order(9)
    void getTeamByNickname_WhenTeamExists_ShouldReturnTeam() throws Exception {
        mockMvc.perform(get("/api/teams/nickname/Lakers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname", is("Lakers")))
                .andExpect(jsonPath("$.teamName", is("Los Angeles Lakers")));
    }

    @Test
    @Order(10)
    void verifyDatabasePopulation_AfterMultipleOperations() throws Exception {
        // Create 3 new teams
        String[] teamNames = {"Phoenix Suns", "Dallas Mavericks", "Portland Trail Blazers"};
        String[] nicknames = {"Suns", "Mavericks", "Blazers"};

        for (int i = 0; i < teamNames.length; i++) {
            Team team = new Team();
            team.setTeamName(teamNames[i]);
            team.setNickname(nicknames[i]);
            team.setLogoUrl("https://example.com/" + nicknames[i].toLowerCase() + ".png");

            mockMvc.perform(post("/api/teams")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(team)))
                    .andExpect(status().isOk());
        }

        // Verify total count (2 from setup + 3 new = 5 teams)
        mockMvc.perform(get("/api/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[*].teamName", 
                    hasItems("Los Angeles Lakers", "Boston Celtics", 
                            "Phoenix Suns", "Dallas Mavericks", "Portland Trail Blazers")));
    }
}