package sistemaDeAlumbrado.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sistemaDeAlumbrado.demo.dtos.solicitudes.ReclamoViveroItemDto;
import sistemaDeAlumbrado.demo.entities.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface ReclamoRepository extends JpaRepository<Reclamo, Long> {


    Page<Reclamo> findReclamosByTecnico1OrTecnico2(UsuarioEntity tecnico1, UsuarioEntity tecnico2, Pageable pageable);


    Optional<Reclamo> findReclamoByEstadoReclamoAndSubTipoReclamoAndPosteLuz(EstadoReclamo estadoReclamo,
                                                                             SubTipoReclamo subTipoReclamo,
                                                                             Poste poste);


    @Query(value = """
        SELECT rxu.id_reclamo,
               p.nombre,
               tp.nombre_tipo,
               stp.nombre_sub_tipo,
               a.nombre,
               r.fecha_arreglo,
               r.hora_arreglo,
               e.nombre_estado,
               r.latitud_reclamo,
               r.longitud_reclamo,
               COALESCE(b1.nombre, b2.nombre),
               rxu.fecha_reclamo,
               rxu.hora_reclamo
        FROM reclamo_x_usuario rxu
        JOIN usuario u ON rxu.id_usuario = u.id
        JOIN reclamo r ON r.id = rxu.id_reclamo
        JOIN sub_tipo_reclamo stp ON r.sub_tipo_reclamo_id = stp.id_sub_tipo
        JOIN tipo_reclamo tp ON stp.tipo_reclamo_id = tp.id_tipo
        LEFT JOIN postes_luz p ON r.poste_id = p.id
        JOIN estado_reclamo e ON r.estado_reclamo_id = e.id
        LEFT JOIN barrio b1 ON r.barrio_id = b1.id
        LEFT JOIN barrio b2 ON p.barrio_id = b2.id
        LEFT JOIN animal a ON r.animal_id = a.id
        WHERE u.id = :idVecino
        ORDER BY rxu.fecha_reclamo DESC, rxu.hora_reclamo DESC
        LIMIT :pageSize OFFSET :offset
    """, nativeQuery = true)
    List<Object[]> findReclamoVecino(@Param("idVecino") Long idVecino,
                                     @Param("pageSize") int pageSize,
                                     @Param("offset") int offset);


    @Query(value = "SELECT COUNT(*) FROM reclamo_x_usuario WHERE id_usuario = :idVecino", nativeQuery = true)
    long countByVecino(@Param("idVecino") Long idVecino);


    @Query(value = """
        SELECT  p.id,
        		p.nombre,
                CASE
        		   WHEN p.id IN
        				(SELECT r.poste_id
        				  FROM reclamo r
        				  WHERE r.estado_reclamo_id = 1
        					AND r.sub_tipo_reclamo_id = :subTipoReclamoId)
        			   THEN 2
        		   ELSE 1
        		END AS estadoPoste,
                p.latitud,
                p.longitud
        FROM postes_luz p
    """, nativeQuery = true)
    List<Object[]>  findPostesReclamadosBySubTipoAndActivos(@Param("subTipoReclamoId") Integer subTipoReclamoId);


    @Query(value = """
        SELECT 	r.poste_id,
        		u.nombre,
                u.apellido,
                rxu.fecha_reclamo,
                rxu.hora_reclamo,
                r.id
        FROM reclamo r
        JOIN reclamo_x_usuario rxu ON r.id = rxu.id_reclamo
        JOIN usuario u ON rxu.id_usuario = u.id
        WHERE r.poste_id = :posteId
          AND r.estado_reclamo_id = 1;
    """, nativeQuery = true)
    List<Object[]> findReclamosDeLuminariaByPoste(@Param("posteId") Integer posteId);


    @Query(value = """
        SELECT p.id AS idPoste,
               p.nombre AS nombrePoste,
               p.latitud AS latitude,
               p.longitud AS longitude,
               CASE
                   WHEN p.id IN
                        ( SELECT r.poste_id
                          FROM reclamo r
                          JOIN reclamo_x_usuario rxu ON r.id = rxu.id_reclamo
                          WHERE r.estado_reclamo_id = 1
                            AND r.sub_tipo_reclamo_id = :subTipoReclamoId
                            AND rxu.id_usuario = :vecinoId )
                       THEN 2
                   ELSE 1
               END AS estadoPoste
        FROM postes_luz p
    """, nativeQuery = true)
    List<Object[]> findLuminaria(@Param("vecinoId") Long vecinoId, @Param("subTipoReclamoId") Integer subTipoReclamoId);


    @Query(value = """
        SELECT r.id,
               str.nombre_sub_tipo,
               str.id_sub_tipo,
               e.nombre_estado,
               tr.nombre_tipo,
               r.latitud_reclamo,
               r.longitud_reclamo,
               v.dni,
               v.nombre,
               v.apellido,
               v.mail,
               v.telefono,
               v.nombre_calle,
               v.numero_calle,
               v.manzana,
               v.lote,
               rxu.fecha_reclamo,
               rxu.hora_reclamo
        FROM reclamo r
        JOIN sub_tipo_reclamo str ON r.sub_tipo_reclamo_id = str.id_sub_tipo
        JOIN estado_reclamo e ON r.estado_reclamo_id = e.id
        JOIN tipo_reclamo tr ON str.tipo_reclamo_id = tr.id_tipo
        JOIN reclamo_x_usuario rxu ON r.id = rxu.id_reclamo
        JOIN usuario v ON rxu.id_usuario = v.id
        WHERE r.estado_reclamo_id = 1
        AND r.sub_tipo_reclamo_id = :idSubTipoReclamo
    """, nativeQuery = true)
    List<Object[]> getAllActiveClaimsBySubTipo(@Param("idSubTipoReclamo") Integer idSubTipoReclamo);


    @Query(value = """
        WITH primer_reclamo AS (
          SELECT *
          FROM (
            SELECT
              rxu.id_reclamo,
              rxu.fecha_reclamo  AS fecha_primera,
              rxu.hora_reclamo   AS hora_primera,
              u.nombre           AS nombreVecino,
              u.apellido         AS apellidoVecino,
              ROW_NUMBER() OVER (
                PARTITION BY rxu.id_reclamo
                ORDER BY rxu.fecha_reclamo ASC, rxu.hora_reclamo ASC
              ) AS rn
            FROM reclamo_x_usuario rxu
            JOIN usuario u ON u.id = rxu.id_usuario
          ) sub
          WHERE sub.rn = 1
        ),
        barrio_vecino AS (
          SELECT
            sub.id_reclamo,
            sub.barrio_vecino
          FROM (
            SELECT
              rxu.id_reclamo,
              b.nombre AS barrio_vecino,
              ROW_NUMBER() OVER (
                PARTITION BY rxu.id_reclamo
                ORDER BY rxu.fecha_reclamo ASC, rxu.hora_reclamo ASC
              ) AS rn
            FROM reclamo_x_usuario rxu
            JOIN usuario u ON u.id = rxu.id_usuario
            JOIN barrio b ON b.id = u.barrio_id
          ) sub
          WHERE sub.rn = 1
        )
        SELECT
          r.id,
          r.fecha_arreglo,
          r.hora_arreglo,
          b1.nombre           AS nombreBarrioByReclamo,
          pr.fecha_primera    AS fechaReclamo,
          pr.hora_primera     AS horaReclamo,
          er.nombre_estado    AS nombreEstado,
          sr.nombre_sub_tipo  AS nombreSubTipoReclamo,
          tr.nombre_tipo      AS nombreTipoReclamo,
          p.nombre            AS nombrePoste,
          b2.nombre           AS nombreBarrioByPoste,
          a.nombre            AS nombreAnimal,
          t1.nombre           AS nombreTecnico1,
          t1.apellido         AS apellidoTecnico1,
          t2.nombre           AS nombreTecnico2,
          t2.apellido         AS apellidoTecnico2,
          bv.barrio_vecino    AS nombreBarrioByVecino,
          pr.nombreVecino     AS nombreVecino,
          pr.apellidoVecino   AS apellidoVecino
        FROM reclamo r
        LEFT JOIN primer_reclamo pr         ON pr.id_reclamo = r.id
        LEFT JOIN barrio b1                 ON b1.id = r.barrio_id
        LEFT JOIN estado_reclamo er         ON er.id = r.estado_reclamo_id
        JOIN sub_tipo_reclamo sr            ON sr.id_sub_tipo = r.sub_tipo_reclamo_id
        JOIN tipo_reclamo tr                ON tr.id_tipo = sr.tipo_reclamo_id
        LEFT JOIN postes_luz p              ON p.id = r.poste_id
        LEFT JOIN barrio b2                 ON b2.id = p.barrio_id
        LEFT JOIN animal a                  ON a.id = r.animal_id
        LEFT JOIN usuario t1                ON t1.id = r.tecnico1_id
        LEFT JOIN usuario t2                ON t2.id = r.tecnico2_id
        LEFT JOIN barrio_vecino bv          ON bv.id_reclamo = r.id
        WHERE
              (:barrioId            IS NULL OR b1.id = :barrioId OR b2.id = :barrioId OR bv.barrio_vecino = :barrioId)
          AND (:tipoId              IS NULL OR tr.id_tipo = :tipoId)
          AND (:subTipoId           IS NULL OR r.sub_tipo_reclamo_id = :subTipoId)
          AND (:estadoId            IS NULL OR r.estado_reclamo_id = :estadoId)
          AND (:tecnicoId           IS NULL OR (t1.id = :tecnicoId OR t2.id = :tecnicoId))
          AND (:fechaDesdeReclamo   IS NULL OR pr.fecha_primera   >= :fechaDesdeReclamo)
          AND (:fechaDesdeReparacion IS NULL OR r.fecha_arreglo   >= :fechaDesdeReparacion)
        ORDER BY r.id DESC
        LIMIT  ?#{#pageable.pageSize}
        OFFSET ?#{#pageable.offset}
    """, countQuery = """
        WITH primer_reclamo AS (
          SELECT id_reclamo,
                 MIN(fecha_reclamo) AS fecha_primera,
                 MIN(hora_reclamo)   AS hora_primera
          FROM reclamo_x_usuario
          GROUP BY id_reclamo
        ),
        barrio_vecino AS (
          SELECT
            rxu.id_reclamo,
            b.nombre AS barrio_vecino
          FROM reclamo_x_usuario rxu
          JOIN usuario u ON u.id = rxu.id_usuario
          JOIN barrio b ON b.id = u.barrio_id
          WHERE (rxu.fecha_reclamo, rxu.hora_reclamo) IN (
            SELECT MIN(rx2.fecha_reclamo), MIN(rx2.hora_reclamo)
            FROM reclamo_x_usuario rx2
            WHERE rx2.id_reclamo = rxu.id_reclamo
          )
        )
        SELECT COUNT(DISTINCT r.id)
        FROM reclamo r
        LEFT JOIN primer_reclamo pr         ON pr.id_reclamo = r.id
        LEFT JOIN barrio b1                 ON b1.id = r.barrio_id
        LEFT JOIN estado_reclamo er         ON er.id = r.estado_reclamo_id
        JOIN sub_tipo_reclamo sr            ON sr.id_sub_tipo = r.sub_tipo_reclamo_id
        JOIN tipo_reclamo tr                ON tr.id_tipo = sr.tipo_reclamo_id
        LEFT JOIN postes_luz p              ON p.id = r.poste_id
        LEFT JOIN barrio b2                 ON b2.id = p.barrio_id
        LEFT JOIN usuario t1                ON t1.id = r.tecnico1_id
        LEFT JOIN usuario t2                ON t2.id = r.tecnico2_id
        LEFT JOIN barrio_vecino bv          ON bv.id_reclamo = r.id
        WHERE
              (:barrioId            IS NULL OR b1.id = :barrioId OR b2.id = :barrioId OR bv.barrio_vecino = :barrioId)
          AND (:tipoId              IS NULL OR tr.id_tipo = :tipoId)
          AND (:subTipoId           IS NULL OR r.sub_tipo_reclamo_id = :subTipoId)
          AND (:estadoId            IS NULL OR r.estado_reclamo_id = :estadoId)
          AND (:tecnicoId           IS NULL OR (t1.id = :tecnicoId OR t2.id = :tecnicoId))
          AND (:fechaDesdeReclamo   IS NULL OR pr.fecha_primera   >= :fechaDesdeReclamo)
          AND (:fechaDesdeReparacion IS NULL OR r.fecha_arreglo   >= :fechaDesdeReparacion)
    """, nativeQuery = true)
    Page<Object[]> findAllReclamos(
            @Param("barrioId")             Integer barrioId,
            @Param("tipoId")               Integer tipoReclamoId,
            @Param("subTipoId")            Integer subTipoId,
            @Param("estadoId")             Integer estadoId,
            @Param("tecnicoId")            Long tecnicoId,
            @Param("fechaDesdeReclamo")    LocalDate fechaDesdeReclamo,
            @Param("fechaDesdeReparacion") LocalDate fechaDesdeReparacion,
            Pageable pageable
    );


    @Query(value = """
    WITH primer_reclamo AS (
      SELECT *
      FROM (
        SELECT
          rxu.id_reclamo,
          rxu.fecha_reclamo  AS fecha_primera,
          rxu.hora_reclamo   AS hora_primera,
          u.nombre           AS nombreVecino,
          u.apellido         AS apellidoVecino,
          ROW_NUMBER() OVER (
            PARTITION BY rxu.id_reclamo
            ORDER BY rxu.fecha_reclamo ASC, rxu.hora_reclamo ASC
          ) AS rn
        FROM reclamo_x_usuario rxu
        JOIN usuario u ON u.id = rxu.id_usuario
      ) sub
      WHERE sub.rn = 1
    ),
    barrio_vecino AS (
      SELECT
        sub.id_reclamo,
        sub.barrio_vecino
      FROM (
        SELECT
          rxu.id_reclamo,
          b.nombre AS barrio_vecino,
          ROW_NUMBER() OVER (
            PARTITION BY rxu.id_reclamo
            ORDER BY rxu.fecha_reclamo ASC, rxu.hora_reclamo ASC
          ) AS rn
        FROM reclamo_x_usuario rxu
        JOIN usuario u ON u.id = rxu.id_usuario
        JOIN barrio b ON b.id = u.barrio_id
      ) sub
      WHERE sub.rn = 1
    )
    SELECT
      r.id                                   AS id,
      r.fecha_arreglo                        AS fechaArreglo,
      r.hora_arreglo                         AS horaArreglo,
      b1.nombre                              AS nombreBarrioByReclamo,
      pr.fecha_primera                       AS fechaReclamo,
      pr.hora_primera                        AS horaReclamo,
      er.nombre_estado                       AS nombreEstado,
      sr.nombre_sub_tipo                     AS nombreSubTipoReclamo,
      tr.nombre_tipo                         AS nombreTipoReclamo,
      p.nombre                               AS nombrePoste,
      b2.nombre                              AS nombreBarrioByPoste,
      a.nombre                               AS nombreAnimal,
      t1.nombre                              AS nombreTecnico1,
      t1.apellido                            AS apellidoTecnico1,
      t2.nombre                              AS nombreTecnico2,
      t2.apellido                            AS apellidoTecnico2,
      bv.barrio_vecino                       AS nombreBarrioByVecino,
      pr.nombreVecino                        AS nombreVecino,
      pr.apellidoVecino                      AS apellidoVecino
    FROM reclamo r
      LEFT JOIN primer_reclamo pr ON pr.id_reclamo = r.id
      LEFT JOIN barrio b1 ON b1.id = r.barrio_id
      LEFT JOIN estado_reclamo er ON er.id = r.estado_reclamo_id
      JOIN sub_tipo_reclamo sr ON sr.id_sub_tipo = r.sub_tipo_reclamo_id
      JOIN tipo_reclamo tr ON tr.id_tipo = sr.tipo_reclamo_id
      LEFT JOIN postes_luz p ON p.id = r.poste_id
      LEFT JOIN barrio b2 ON b2.id = p.barrio_id
      LEFT JOIN animal a ON a.id = r.animal_id
      LEFT JOIN usuario t1 ON t1.id = r.tecnico1_id
      LEFT JOIN usuario t2 ON t2.id = r.tecnico2_id
      LEFT JOIN barrio_vecino bv ON bv.id_reclamo = r.id
    WHERE r.id = :reclamoId
    """,
        nativeQuery = true)
    List<Object[]> findByIdReclamoItemList(@Param("reclamoId") long reclamoId);


    @Query(
            value = """
      SELECT new sistemaDeAlumbrado.demo.dtos.solicitudes.ReclamoViveroItemDto(
        r.id,
        str.nombreSubTipo,
        tr.nombreTipo,
        er.nombreEstado,
        u1.nombre,
        u1.apellido,
        u2.nombre,
        u2.apellido,
        r.fechaArreglo,
        r.horaArreglo,
        rxu.fechaReclamo,
        rxu.horaReclamo,
        uv.nombre,
        uv.apellido,
        uv.dni
      )
      FROM Reclamo r
      JOIN SubTipoReclamo str ON r.subTipoReclamo.idSubTipo = str.idSubTipo
      JOIN TipoReclamo tr ON str.tipoReclamo.idTipo = tr.idTipo
      JOIN EstadoReclamo er ON r.estadoReclamo.id = er.id
      LEFT JOIN UsuarioEntity u1 ON r.tecnico1.id = u1.id
      LEFT JOIN UsuarioEntity u2 ON r.tecnico2.id = u2.id
      JOIN ReclamoXUsuario rxu ON r.id = rxu.reclamoXUsuarioId.reclamo.id
      JOIN UsuarioEntity uv ON rxu.reclamoXUsuarioId.usuario.id = uv.id
      WHERE r.subTipoReclamo.idSubTipo = :subTipoReclamoId
    """,
            countQuery = """
      SELECT COUNT(r)
        FROM Reclamo r
        JOIN SubTipoReclamo str ON str.idSubTipo = r.subTipoReclamo.idSubTipo
      WHERE str.idSubTipo = :subTipoReclamoId
    """
    )
    Page<ReclamoViveroItemDto> findAllReclamosViveroXSubTipo(
            @Param("subTipoReclamoId") Integer subTipoReclamoId,
            Pageable pageable
    );

}
