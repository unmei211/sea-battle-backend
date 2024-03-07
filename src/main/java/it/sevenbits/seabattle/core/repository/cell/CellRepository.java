package it.sevenbits.seabattle.core.repository.cell;

import it.sevenbits.seabattle.core.model.cell.Cell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CellRepository extends JpaRepository<Cell, Long> {
//    List<Cell> findAllByUserIdAndSessionId(Long userId, Long sessionId);
}
