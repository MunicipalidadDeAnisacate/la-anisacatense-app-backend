package sistemaDeAlumbrado.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sistemaDeAlumbrado.demo.dtos.arreglos.ArregloDto;
import sistemaDeAlumbrado.demo.entities.Arreglo;
import sistemaDeAlumbrado.demo.entities.UsuarioEntity;

import java.time.LocalDate;


@Repository
public interface ArregloRepository extends JpaRepository<Arreglo, Long> {

    Page<Arreglo> findArreglosByTecnico1OrTecnico2(UsuarioEntity tecnico1, UsuarioEntity tecnico2, Pageable pageable);


    @Query("""
        SELECT new sistemaDeAlumbrado.demo.dtos.arreglos.ArregloDto(
            arr.id,
            p.nombrePoste,
            tr.nombreTipo,
            arr.fechaArreglo,
            arr.horaArreglo,
            t1.nombre,
            t1.apellido,
            t2.nombre,
            t2.apellido,
            arr.latitudReclamo,
            arr.longitudReclamo,
            b.nombre
        )
        FROM Arreglo arr
        LEFT JOIN Poste p ON arr.poste.idPoste = p.idPoste
        LEFT JOIN TipoReclamo tr ON arr.tipoReclamo.idTipo = tr.idTipo
        LEFT JOIN UsuarioEntity t1 ON arr.tecnico1.id = t1.id
        LEFT JOIN UsuarioEntity t2 ON arr.tecnico2.id = t2.id
        LEFT JOIN Barrio b ON arr.barrio.id = b.id
        WHERE
            (:barrioId IS NULL OR b.id = :barrioId)
            AND (:tipoId IS NULL OR tr.idTipo = :tipoId)
            AND (:tecnicoId IS NULL OR t1.id = :tecnicoId OR t2.id = :tecnicoId)
            AND (:fechaDesde IS NULL OR arr.fechaArreglo >= :fechaDesde)
        ORDER BY arr.id DESC
    """)
    Page<ArregloDto> findAllByFilters(
            @Param("barrioId") Integer barrioId,
            @Param("tipoId") Integer tipoId,
            @Param("tecnicoId") Long tecnicoId,
            @Param("fechaDesde") LocalDate fechaDesde,
            Pageable pageable
    );
}
