package sistemaDeAlumbrado.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sistemaDeAlumbrado.demo.entities.PreferenciaConsultaCiudadana;

public interface PreferenciaRepository extends JpaRepository<PreferenciaConsultaCiudadana, Integer> {
    boolean existsByConsultaCiudadanaIdAndUsuarioId(Long consultaId, Long usuarioId);

    @Query(value = """
        SELECT COUNT(*)
        FROM PreferenciaConsultaCiudadana
        WHERE consultaCiudadana.id = :consultaId
        AND proyecto.id = :proyectoId
    """)
    Long countPreferenciasDeProyectoEnConsulta(@Param("proyectoId") Long proyectoId, @Param("consultaId") Long consultaId);
}
