package sistemaDeAlumbrado.demo.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sistemaDeAlumbrado.demo.dtos.estadisticas.*;
import sistemaDeAlumbrado.demo.entities.Reclamo;

import java.time.LocalDate;
import java.util.List;

public interface EstadisticasRepository extends JpaRepository <Reclamo, Long> {


    @Query("""
        SELECT COUNT(DISTINCT r)
        FROM Reclamo r
        WHERE r.estadoReclamo.id = :id
    """)
    long countReclamosByEstadoReclamo(@Param("id") Integer id);


    @Query(value = """
      WITH RECURSIVE semanas AS (
        SELECT
          0 AS n,
          DATE_SUB(:today,
                   INTERVAL WEEKDAY(:today) + 1 DAY
          ) AS week_start
        UNION ALL
        SELECT
          n + 1,
          DATE_SUB(week_start, INTERVAL 7 DAY)
        FROM semanas
        WHERE n + 1 < :numWeeks
      )
      SELECT
        s.week_start           AS fechaDesde,
        DATE_ADD(s.week_start, INTERVAL 6 DAY) AS fechaHasta,
        COALESCE(SUM(r.estado_reclamo_id = 2), 0) AS cantidadResuelto
      FROM semanas s
      LEFT JOIN reclamo r
        ON r.fecha_arreglo BETWEEN s.week_start
                              AND DATE_ADD(s.week_start, INTERVAL 6 DAY)
      GROUP BY s.n, s.week_start
      ORDER BY s.week_start DESC
    """, nativeQuery = true)
    List<Object[]> findReclamosPorSemana(@Param("today") LocalDate today, @Param("numWeeks") Integer numWeeks);


    @Query("""
      SELECT new sistemaDeAlumbrado.demo.dtos.estadisticas.EstadisticasReclamosXBarrioDto(
        COALESCE(b.zona.id, pb.zona.id, ub.zona.id),
        COALESCE(b.nombre, pb.nombre, ub.nombre),
        COUNT(DISTINCT (CASE WHEN r.estadoReclamo.id = 1 THEN r.id END)),
        COUNT(DISTINCT (CASE WHEN r.estadoReclamo.id = 2 THEN r.id END))
      )
      FROM Reclamo r
        LEFT JOIN r.barrio b
        LEFT JOIN r.posteLuz pl
          LEFT JOIN pl.barrio pb
        LEFT JOIN ReclamoXUsuario rxu
          ON rxu.reclamoXUsuarioId.reclamo.id = r.id
        LEFT JOIN rxu.reclamoXUsuarioId.usuario u
          LEFT JOIN u.barrio ub
      GROUP BY
        COALESCE(b.zona.id, pb.zona.id, ub.zona.id),
        COALESCE(b.nombre, pb.nombre, ub.nombre)
    """)
    List<EstadisticasReclamosXBarrioDto> reclamosPorBarrio();


    @Query("""
      SELECT new sistemaDeAlumbrado.demo.dtos.estadisticas.EstadisticasReclamosXBarrioDto(
        COALESCE(b.zona.id, pb.zona.id, ub.zona.id),
        COALESCE(b.nombre, pb.nombre, ub.nombre),
        COUNT(DISTINCT (CASE WHEN r.estadoReclamo.id = 1 THEN r.id END) ),
        COUNT(DISTINCT (CASE WHEN r.estadoReclamo.id = 2 THEN r.id END) )
      )
      FROM Reclamo r
        LEFT JOIN r.barrio b
        LEFT JOIN r.posteLuz pl
          LEFT JOIN pl.barrio pb
        LEFT JOIN ReclamoXUsuario rxu
          ON rxu.reclamoXUsuarioId.reclamo.id = r.id
        LEFT JOIN rxu.reclamoXUsuarioId.usuario u
          LEFT JOIN u.barrio ub
      WHERE r.subTipoReclamo.tipoReclamo.idTipo = :tipoReclamoId
      GROUP BY
        COALESCE(b.zona.id, pb.zona.id, ub.zona.id),
        COALESCE(b.nombre,   pb.nombre,   ub.nombre)
    """)
    List<EstadisticasReclamosXBarrioDto> reclamosPorBarrioTipoReclamo(@Param("tipoReclamoId") Integer tipoReclamoId);


    @Query("""
      SELECT new sistemaDeAlumbrado.demo.dtos.estadisticas.EstadisticasReclamosXBarrioDto(
        COALESCE(b.zona.id, pb.zona.id, ub.zona.id),
        COALESCE(b.nombre,   pb.nombre,   ub.nombre),
        COUNT(DISTINCT (CASE WHEN r.estadoReclamo.id = 1 THEN r.id END) ),
        COUNT(DISTINCT (CASE WHEN r.estadoReclamo.id = 2 THEN r.id END) )
      )
      FROM Reclamo r
        LEFT JOIN r.barrio b
        LEFT JOIN r.posteLuz pl
          LEFT JOIN pl.barrio pb
        LEFT JOIN ReclamoXUsuario rxu
          ON rxu.reclamoXUsuarioId.reclamo.id = r.id
        LEFT JOIN rxu.reclamoXUsuarioId.usuario u
          LEFT JOIN u.barrio ub
      WHERE r.subTipoReclamo.idSubTipo = :subTipoReclamoId
      GROUP BY
        COALESCE(b.zona.id, pb.zona.id, ub.zona.id),
        COALESCE(b.nombre,   pb.nombre,   ub.nombre)
    """)
    List<EstadisticasReclamosXBarrioDto> reclamosPorBarrioSubTipoReclamo(@Param("subTipoReclamoId") Integer subTipoReclamoId);


    @Query("""
        SELECT new sistemaDeAlumbrado.demo.dtos.estadisticas.EstadisticasReclamosXTipoDto(
            r.subTipoReclamo.tipoReclamo.idTipo,
            r.subTipoReclamo.tipoReclamo.nombreTipo,
            COUNT (CASE WHEN r.estadoReclamo.id = 1 THEN r.id END),
            COUNT (CASE WHEN r.estadoReclamo.id = 2 THEN r.id END)
        )
        FROM Reclamo r
        GROUP BY r.subTipoReclamo.tipoReclamo.idTipo, r.subTipoReclamo.tipoReclamo.nombreTipo
        ORDER BY r.subTipoReclamo.tipoReclamo.idTipo
    """)
    List<EstadisticasReclamosXTipoDto> reclamoPorTipoReclamo();


    @Query("""
      SELECT new sistemaDeAlumbrado.demo.dtos.estadisticas.EstadisticasReclamosXSubTipoDto(
        str.idSubTipo,
        str.nombreSubTipo,
        COUNT(CASE WHEN r.estadoReclamo.id = 1 THEN r.id END),
        COUNT(CASE WHEN r.estadoReclamo.id = 2 THEN r.id END)
      )
      FROM Reclamo r
        JOIN r.subTipoReclamo str
        JOIN str.tipoReclamo tr
      WHERE tr.idTipo = :tipoReclamoId
      GROUP BY str.idSubTipo, str.nombreSubTipo
    """)
    List<EstadisticasReclamosXSubTipoDto> reclamosPorSubTipoDeTipo(@Param("tipoReclamoId") Integer tipoReclamoId);


    @Query("""
      SELECT new sistemaDeAlumbrado.demo.dtos.estadisticas.EstadisticasLuminariaDto(
        SUM(CASE WHEN rtr.reclamoXTipoReparacionId.tipoReparacion.id = 1 THEN 1 ELSE 0 END),
        SUM(CASE WHEN rtr.reclamoXTipoReparacionId.tipoReparacion.id = 2 THEN 1 ELSE 0 END),
        SUM(CASE WHEN rtr.reclamoXTipoReparacionId.tipoReparacion.id = 3 THEN 1 ELSE 0 END),
        SUM(CASE WHEN rtr.reclamoXTipoReparacionId.tipoReparacion.id = 4 THEN 1 ELSE 0 END)
      )
      FROM ReclamoXTipoReparacion rtr
        JOIN rtr.reclamoXTipoReparacionId.reclamo r
      WHERE r.subTipoReclamo.idSubTipo = 1
    """)
    EstadisticasLuminariaDto estadisticasReclamosLuminaria();


    @Query("""
        SELECT new sistemaDeAlumbrado.demo.dtos.estadisticas.EstadisticasLuminariaDto(
          SUM(CASE WHEN rxtr.reclamoXTipoReparacionId.tipoReparacion.id = 1 THEN 1 ELSE 0 END),
          SUM(CASE WHEN rxtr.reclamoXTipoReparacionId.tipoReparacion.id = 2 THEN 1 ELSE 0 END),
          SUM(CASE WHEN rxtr.reclamoXTipoReparacionId.tipoReparacion.id = 3 THEN 1 ELSE 0 END),
          SUM(CASE WHEN rxtr.reclamoXTipoReparacionId.tipoReparacion.id = 4 THEN 1 ELSE 0 END)
        )
        FROM ReclamoXTipoReparacion rxtr
        JOIN Reclamo r ON rxtr.reclamoXTipoReparacionId.reclamo = r
        WHERE rxtr.reclamoXTipoReparacionId.reclamo.subTipoReclamo.idSubTipo = 1
        AND r.fechaArreglo = :fecha
    """)
    EstadisticasLuminariaDto estadisticasReclamosLuminariaSoloFecha(@Param("fecha") LocalDate fecha);


    @Query("""
        SELECT new sistemaDeAlumbrado.demo.dtos.estadisticas.EstadisticasLuminariaDto(
          SUM(CASE WHEN rxtr.reclamoXTipoReparacionId.tipoReparacion.id = 1 THEN 1 ELSE 0 END),
          SUM(CASE WHEN rxtr.reclamoXTipoReparacionId.tipoReparacion.id = 2 THEN 1 ELSE 0 END),
          SUM(CASE WHEN rxtr.reclamoXTipoReparacionId.tipoReparacion.id = 3 THEN 1 ELSE 0 END),
          SUM(CASE WHEN rxtr.reclamoXTipoReparacionId.tipoReparacion.id = 4 THEN 1 ELSE 0 END)
        )
        FROM ReclamoXTipoReparacion rxtr
        JOIN Reclamo r ON rxtr.reclamoXTipoReparacionId.reclamo = r
        WHERE rxtr.reclamoXTipoReparacionId.reclamo.subTipoReclamo.idSubTipo = 1
        AND r.fechaArreglo >= :fecha
    """)
    EstadisticasLuminariaDto estadisticasReclamosLuminariaDesde(@Param("fecha") LocalDate fecha);


    @Query("""
        SELECT COUNT(*)
        FROM UsuarioEntity usr
        WHERE usr.tipoUsuario.id = 1
    """)
    long countVecinos();


    @Query("""
        SELECT COUNT(DISTINCT rxu.reclamoXUsuarioId.usuario.id)
        FROM ReclamoXUsuario rxu
    """)
    long countVecinosSolicitaron();


    @Query("""
        SELECT new sistemaDeAlumbrado.demo.dtos.estadisticas.EstadisticasVecinoCantSolicitudesDto(
            u.nombre,
            u.apellido,
            u.dni,
            SUM(CASE WHEN r.estadoReclamo.id = 1 THEN 1 ELSE 0 END),
            SUM(CASE WHEN r.estadoReclamo.id = 2 THEN 1 ELSE 0 END)
        )
        FROM UsuarioEntity u
        LEFT JOIN ReclamoXUsuario rxu
          ON rxu.reclamoXUsuarioId.usuario.id = u.id
        LEFT JOIN Reclamo r
          ON rxu.reclamoXUsuarioId.reclamo.id = r.id
        GROUP BY u.id, u.nombre, u.apellido, u.dni
        ORDER BY COUNT(r) DESC
    """)
    List<EstadisticasVecinoCantSolicitudesDto> getVecinosMasSolicitudes(Pageable pageable);
}
