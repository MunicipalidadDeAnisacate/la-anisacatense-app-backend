package sistemaDeAlumbrado.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistemaDeAlumbrado.demo.entities.Animal;
import sistemaDeAlumbrado.demo.repositories.AnimalRepository;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;

    public Animal findById(final Integer id){
        return animalRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("animal not found"));
    }

    public Animal getOrNull(final Integer idAnimal){
        Animal animal = null;
        if (idAnimal != null){
            animal = this.findById(idAnimal);
        }
        return animal;
    }
}
