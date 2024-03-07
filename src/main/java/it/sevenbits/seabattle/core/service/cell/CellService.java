package it.sevenbits.seabattle.core.service.cell;

import it.sevenbits.seabattle.core.model.cell.Cell;
import it.sevenbits.seabattle.core.repository.cell.CellRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CellService  {
    private final CellRepository cellRepository;

    public Optional<Cell> getById(Long id) {
        return Optional.empty();
    }


    public List<Cell> getAll() {
        return null;
    }


    public void remove(Long id) {
        cellRepository.deleteById(id);
    }


    public void remove(Cell cell) {
        cellRepository.delete(cell);
    }


    public void update(Long id, Cell cellToBeUpdated) {
        cellRepository.save(cellToBeUpdated);
    }


    public void save(Cell cellToSave) {
        cellRepository.save(cellToSave);
    }
}
