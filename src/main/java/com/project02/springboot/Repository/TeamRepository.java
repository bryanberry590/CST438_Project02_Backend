package com.project02.springboot.Repository;

import com.project02.springboot.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByTeamNameIgnoreCase(String teamName);
    List<Team> findByTeamNameContainingIgnoreCase(String partial);
    List<Team> findByNicknameIgnoreCase(String nickname);
}