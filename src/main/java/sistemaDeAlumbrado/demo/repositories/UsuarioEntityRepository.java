package sistemaDeAlumbrado.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sistemaDeAlumbrado.demo.entities.Proyecto;
import sistemaDeAlumbrado.demo.entities.TipoUsuario;
import sistemaDeAlumbrado.demo.entities.UsuarioEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioEntityRepository extends JpaRepository<UsuarioEntity, Long> {


    Optional<UsuarioEntity> findByDni(String dni);

     boolean existsByDni(String dni);

    boolean existsByMail(String mail);

    boolean existsByTelefono(String telefono);

    @Query("""
        SELECT t
        FROM UsuarioEntity t
        WHERE t.tipoUsuario.id = 2
        AND t.cuadrilla.id = :cuadrillaId
        AND t.id != :tecnicoId
    """)
    Set<UsuarioEntity> getUsuarioEntitiesByTipoUsuario2(@Param("tecnicoId") Long tecnicoId,@Param("cuadrillaId") Integer cuadrillaId);


    @Query("""
        SELECT v
        FROM UsuarioEntity v
        WHERE v.tipoUsuario.id = :tipoUsuarioId
    """)
    Page<UsuarioEntity> findAllUsuariosByRol(@Param("tipoUsuarioId") Integer tipoUsuarioId, Pageable pageable);


    @Query("""
        SELECT t
        FROM UsuarioEntity t
        WHERE t.tipoUsuario.id = 2
    """)
    List<UsuarioEntity> findAllTecnicos();

    @Query("""
        SELECT p
        FROM Proyecto p
        WHERE p.usuario.id = :usuarioId
    """)
    List<Proyecto> findProyectosByUsuario(@Param("usuarioId") Long usuarioId);

    @Query("""
      SELECT CASE WHEN COUNT(cxp) > 0 THEN true ELSE false END
        FROM ConsultaXProyecto cxp
       WHERE cxp.proyecto.id = :proyectoId
    """)
    Boolean existeConsultaCiudadanaDeProyecto(@Param("proyectoId") Long proyectoId);

    @Modifying
    @Query(value = "DELETE FROM preferencia_consulta_ciudadana WHERE usuario_id = :usuarioId", nativeQuery = true)
    void deletePreferenciasDeUsuario(@Param("usuarioId") Long usuarioId);

    @Modifying
    @Query(value = "DELETE FROM proyecto WHERE usuario_id = :usuarioId", nativeQuery = true)
    void deleteProyectosUsuario(@Param("usuarioId") Long usuarioId);

    @Modifying
    @Query(value = "DELETE FROM reset_password_token WHERE usuario_id = :usuarioId", nativeQuery = true)
    void deleteResetPasswordTokenByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Modifying
    @Query(value = "DELETE FROM refresh_token WHERE usuario_id = :usuarioId", nativeQuery = true)
    void deleteRefreshTokenByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query(value = "SELECT id_reclamo FROM reclamo_x_usuario WHERE id_usuario = :usuarioId", nativeQuery = true)
    List<Object[]> reclamosIdsRealizadosPorUsuario(@Param("usuarioId") Long usuarioId);

    @Modifying
    @Query(value = "DELETE FROM reclamo_x_usuario WHERE id_usuario = :usuarioId", nativeQuery = true)
    void deleteReclamoXUsuarioByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Modifying
    @Query(value = "DELETE FROM reclamo_x_tipo_reparacion WHERE id_reclamo IN :reclamosIds", nativeQuery = true)
    void deleteReclamosXTipoDeReparacion(@Param("reclamosIds") List<Long> reclamosIds);

    @Modifying
    @Query(value = "DELETE FROM reclamo WHERE id IN :reclamosIds", nativeQuery = true)
    void deleteReclamosDeUsuario(@Param("reclamosIds") List<Long> reclamosIds);

    @Modifying
    @Query(value = "DELETE FROM arreglo WHERE tecnico1_id = :usuarioId OR tecnico2_id = :usuarioId", nativeQuery = true)
    void deleteArregloByTecnicoId(@Param("usuarioId") Long usuarioId);

    @Modifying
    @Query(value = "DELETE FROM reclamo WHERE tecnico1_id = :usuarioId OR tecnico2_id = :usuarioId", nativeQuery = true)
    void deleteReclamoByTecnicoId(@Param("usuarioId") Long usuarioId);
}
