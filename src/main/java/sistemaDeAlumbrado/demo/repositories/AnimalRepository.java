package sistemaDeAlumbrado.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistemaDeAlumbrado.demo.entities.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {
}
