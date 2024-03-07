package it.sevenbits.sea_battle.repository;
import it.sevenbits.sea_battle.entity.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ShipRepository extends JpaRepository<Ship, Long>{
}
