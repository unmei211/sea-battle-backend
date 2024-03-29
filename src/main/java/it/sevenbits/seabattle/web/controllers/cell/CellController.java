package it.sevenbits.seabattle.web.controllers.cell;

import it.sevenbits.seabattle.core.model.cell.Cell;
import it.sevenbits.seabattle.core.service.cell.CellService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * cell controller
 */
@RestController("/cells")
@AllArgsConstructor
@CrossOrigin("http://localhost:5173")
public class CellController {
    private final CellService cellService;

    @GetMapping
    public List<Cell> getCells() {
        return cellService.getAll();
    }

    /**
     * get cell by id
     *
     * @param id - cell id
     * @return - cell or null
     */
    @GetMapping("/{id}")
    public Cell getCell(@PathVariable final Long id) {
        return cellService.getById(id).orElse(null);
    }

    /**
     * delete cell
     *
     * @param id - cell id
     */
    @DeleteMapping("/{id}")
    public void deleteCell(@PathVariable final Long id) {
        cellService.remove(id);
    }

    /**
     * update cell
     *
     * @param id   - cell id
     * @param cell - cell model
     */
    @PatchMapping("/{id}")
    public void updateCell(final Long id, final Cell cell) {
        cellService.update(id, cell);
    }
}
