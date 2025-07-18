package sistemaDeAlumbrado.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sistemaDeAlumbrado.demo.entities.ConsultaXProyecto;
import sistemaDeAlumbrado.demo.entities.ConsultaXProyectoId;

public interface EleccionXProyectoRepository extends JpaRepository<ConsultaXProyecto, ConsultaXProyectoId> {
}
