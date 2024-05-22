package com.springChess.springChess.repository;

import com.springChess.springChess.model.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

        public List<Game> findGameByLogs(String log);
}
