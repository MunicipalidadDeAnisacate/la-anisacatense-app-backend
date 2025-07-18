package sistemaDeAlumbrado.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sistemaDeAlumbrado.demo.entities.Reclamo;
import sistemaDeAlumbrado.demo.entities.ReclamoXTipoReparacion;
import sistemaDeAlumbrado.demo.entities.ReclamoXTipoReparacionId;

import java.util.List;

@Repository
public interface TipoReparacionXReclamoRepository extends JpaRepository<ReclamoXTipoReparacion, ReclamoXTipoReparacionId> {

    @Query("""
        SELECT rxtr
        FROM ReclamoXTipoReparacion rxtr
        WHERE rxtr.reclamoXTipoReparacionId.reclamo = :reclamo
    """)
    List<ReclamoXTipoReparacion> findAllByReclamo(Reclamo reclamo);
}
