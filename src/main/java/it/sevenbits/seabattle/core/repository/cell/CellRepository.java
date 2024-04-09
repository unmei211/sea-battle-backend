package it.sevenbits.seabattle.core.repository.cell;

import it.sevenbits.seabattle.core.model.cell.Cell;
import it.sevenbits.seabattle.core.model.session.Session;
import it.sevenbits.seabattle.core.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Cell repository interface
 */
public interface CellRepository extends JpaRepository<Cell, Long> {
    /**
     * take cell which was hit
     *
     * @param sessionId - session id
     * @param userId    - user id
     * @param xPos      - x pos cell
     * @param yPos      - y pos cell
     * @return - cell
     */
    Optional<Cell> findCellBySessionIdAndUserIdAndAxisAndOrdinate(Long sessionId, Long userId, int xPos, int yPos);

    List<Cell> findCellBySessionIdAndUserId(Long sessionId, Long userId);
}
