package com.project02.springboot.Repository;

import com.project02.springboot.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    
    @Query("SELECT g FROM Game g WHERE g.gameDate BETWEEN :startDate AND :endDate " +
           "AND (g.homeTeamId = :teamId OR g.awayTeamId = :teamId)")
    List<Game> findByGameDateBetweenAndTeamId(@Param("startDate") LocalDateTime startDate, 
                                             @Param("endDate") LocalDateTime endDate, 
                                             @Param("teamId") Long teamId);
    
    List<Game> findByGameDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Game> findByHomeTeamIdOrAwayTeamId(Long homeTeamId, Long awayTeamId);
}
