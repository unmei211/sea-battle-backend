package it.sevenbits.sea_battle.controller;

import it.sevenbits.sea_battle.entity.Cell;
import it.sevenbits.sea_battle.services.CellService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/cells")
@AllArgsConstructor
public class CellController {
    private final CellService cellService;

    @GetMapping
    public List<Cell> getCells() {
        return cellService.getAll();
    }

    @GetMapping("/{id}")
    public Cell getCell(@PathVariable Long id) {
        return cellService.getById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteCell(@PathVariable Long id) {
        cellService.remove(id);
    }

    @PatchMapping("/{id}")
    public void updateCell(Long id, Cell cell) {
        cellService.update(id, cell);
    }
}
