package sistemaDeAlumbrado.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sistemaDeAlumbrado.demo.dtos.proyectos.ProyectoEnConsultaDto;
import sistemaDeAlumbrado.demo.entities.Proyecto;

import java.time.LocalDate;
import java.util.List;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(ep) > 0 THEN TRUE ELSE FALSE END
        FROM ConsultaXProyecto ep
        WHERE ep.proyecto.id = :proyectoId
    """)
    boolean existsProyectoInActiveOrFutureElection(@Param("proyectoId") Long proyectoId);


    @Query(value = """
        SELECT new  sistemaDeAlumbrado.demo.dtos.proyectos.ProyectoEnConsultaDto(
            p.id,
            p.titulo,
            p.descripcion,
            p.objectName,
            CAST((select count(*) from PreferenciaConsultaCiudadana v where v.proyecto.id = p.id and v.consultaCiudadana.id = :consultaId) AS Long)
            )
        FROM ConsultaXProyecto exp
        JOIN exp.proyecto p
        WHERE exp.consultaCiudadana.id = :consultaId
    """)
    List<ProyectoEnConsultaDto> findProyectosEnConsultaDtoByConsulta(@Param("consultaId") Long consultaId);
}
