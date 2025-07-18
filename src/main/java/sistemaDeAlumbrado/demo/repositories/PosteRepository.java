package sistemaDeAlumbrado.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistemaDeAlumbrado.demo.entities.Poste;


@Repository
public interface PosteRepository extends JpaRepository<Poste, Integer> {

}
