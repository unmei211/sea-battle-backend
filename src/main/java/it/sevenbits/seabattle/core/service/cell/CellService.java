package it.sevenbits.seabattle.core.service.cell;

import it.sevenbits.seabattle.core.model.cell.Cell;
import it.sevenbits.seabattle.core.repository.cell.CellRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * cell service
 */
@AllArgsConstructor
@Service
public class CellService {
    private final CellRepository cellRepository;

    /**
     * get cell by id
     *
     * @param id - cell id
     * @return - cell
     */
    public Optional<Cell> getById(final Long id) {
        return Optional.empty();
    }


    public List<Cell> getAll() {
        return null;
    }


    /**
     * remove cell
     *
     * @param id - cell id
     */
    public void remove(final Long id) {
        cellRepository.deleteById(id);
    }

    /**
     * remove cell
     *
     * @param cell - cell
     */
    public void remove(final Cell cell) {
        cellRepository.delete(cell);
    }


    /**
     * update cell
     *
     * @param id              - cell id
     * @param cellToBeUpdated - cell
     */
    public void update(final Long id, final Cell cellToBeUpdated) {
        cellRepository.save(cellToBeUpdated);
    }


    /**
     * save cell
     *
     * @param cellToSave - cell
     */
    public void save(final Cell cellToSave) {
        cellRepository.save(cellToSave);
    }
}
