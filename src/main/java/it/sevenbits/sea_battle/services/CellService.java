package it.sevenbits.sea_battle.services;

import it.sevenbits.sea_battle.entity.Cell;
import it.sevenbits.sea_battle.repository.CellRepository;
import it.sevenbits.sea_battle.services.interfaces.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CellService implements CrudService<Cell> {
    private final CellRepository cellRepository;
    @Override
    public Optional<Cell> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Cell> getAll() {
        return null;
    }

    @Override
    public void remove(Long id) {
        cellRepository.deleteById(id);
    }

    @Override
    public void remove(Cell cell) {
        cellRepository.delete(cell);
    }

    @Override
    public void update(Long id, Cell cellToBeUpdated) {
        cellRepository.save(cellToBeUpdated);
    }

    @Override
    public void save(Cell cellToSave) {
        cellRepository.save(cellToSave);
    }
}
