package sistemaDeAlumbrado.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistemaDeAlumbrado.demo.entities.ReclamoXTipoReparacion;
import sistemaDeAlumbrado.demo.entities.ReclamoXTipoReparacionId;

@Repository
public interface ReclamoXTipoReparacionRepository extends JpaRepository<ReclamoXTipoReparacion, ReclamoXTipoReparacionId> {
}
