package it.sevenbits.seabattle.core.repository.ship;

import it.sevenbits.seabattle.core.model.ship.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipRepository extends JpaRepository<Ship, Long> {
}
