package sistemaDeAlumbrado.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sistemaDeAlumbrado.demo.entities.ConsultaCiudadana;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public interface ConsultaCiudadanaRepository extends JpaRepository<ConsultaCiudadana, Long> {

    @Query("""
    SELECT e
    FROM ConsultaCiudadana e
    WHERE
        (:hoy > e.fechaInicio OR (:hoy = e.fechaInicio AND :ahora >= e.horaInicio))
        AND
        (:hoy < e.fechaCierre OR (:hoy = e.fechaCierre AND :ahora <= e.horaCierre))
    """)
    List<ConsultaCiudadana> findAllConsultasDisponibles(@Param("hoy") LocalDate today, @Param("ahora") Time now);

    @Query("""
    SELECT v.proyecto.id
    FROM PreferenciaConsultaCiudadana v
    WHERE v.consultaCiudadana.id = :consultaID
    AND v.usuario.id = :usuarioID
    """)
    Long proyectoIDPreferido(@Param("consultaID") Long consultaId, @Param("usuarioID") Long usuarioId);
}
