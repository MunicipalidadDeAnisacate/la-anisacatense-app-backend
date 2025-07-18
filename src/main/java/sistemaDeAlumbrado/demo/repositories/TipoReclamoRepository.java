package sistemaDeAlumbrado.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistemaDeAlumbrado.demo.entities.TipoReclamo;

@Repository
public interface TipoReclamoRepository extends JpaRepository<TipoReclamo, Integer> {
}
