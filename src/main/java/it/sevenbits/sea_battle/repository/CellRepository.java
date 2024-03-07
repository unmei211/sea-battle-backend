package it.sevenbits.sea_battle.repository;

import it.sevenbits.sea_battle.entity.Cell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CellRepository extends JpaRepository<Cell, Long> {
    List<Cell> findAllByUserIdAndSessionId(Long userId, Long sessionId);
}

