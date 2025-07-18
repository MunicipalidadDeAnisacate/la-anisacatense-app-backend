package sistemaDeAlumbrado.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import sistemaDeAlumbrado.demo.entities.Reclamo;
import sistemaDeAlumbrado.demo.entities.ReclamoXUsuario;
import sistemaDeAlumbrado.demo.entities.ReclamoXUsuarioId;
import sistemaDeAlumbrado.demo.entities.UsuarioEntity;

import java.util.List;

@Repository
public interface ReclamoXUsuarioRepository extends JpaRepository<ReclamoXUsuario, ReclamoXUsuarioId> {

    List<ReclamoXUsuario> findAllReclamoXUsuariosByReclamoXUsuarioId_Reclamo(Reclamo reclamo);

    @Query("""
        SELECT rxu.reclamoXUsuarioId.usuario
        FROM ReclamoXUsuario rxu
        WHERE rxu.reclamoXUsuarioId.reclamo.id = :reclamoId
    """)
    List<UsuarioEntity> findVecinosByReclamo(@Param("reclamoId") Long reclamoId);
}
