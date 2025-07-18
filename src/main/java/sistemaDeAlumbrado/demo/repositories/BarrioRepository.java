package sistemaDeAlumbrado.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistemaDeAlumbrado.demo.entities.Barrio;

@Repository
public interface BarrioRepository extends JpaRepository<Barrio, Integer> {
}
