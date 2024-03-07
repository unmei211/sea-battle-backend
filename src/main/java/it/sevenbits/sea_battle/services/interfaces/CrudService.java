package it.sevenbits.sea_battle.services.interfaces;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {
    Optional<T> getById(Long id);
    List<T> getAll();
    void remove(Long id);
    void remove(T object);
    void update(Long id, T objectToBeUpdated);
    void save(T objectToSave);
}
