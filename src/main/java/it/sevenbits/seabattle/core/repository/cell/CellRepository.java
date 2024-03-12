package it.sevenbits.seabattle.core.repository.cell;

import it.sevenbits.seabattle.core.model.cell.Cell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CellRepository extends JpaRepository<Cell, Long> {
    //    List<Cell> findAllByUserIdAndSessionId(Long userId, Long sessionId);
    Optional<Cell> findCellBySessionIdAndUserIdAndAxisAndOrdinate(Long sessionId, Long userId, int xPos, int yPos);
}
