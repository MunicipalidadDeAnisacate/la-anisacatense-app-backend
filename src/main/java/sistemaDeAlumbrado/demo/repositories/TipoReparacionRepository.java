package sistemaDeAlumbrado.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistemaDeAlumbrado.demo.entities.TipoReparacion;

import java.util.List;

@Repository
public interface TipoReparacionRepository extends JpaRepository<TipoReparacion, Integer> {

}
