package sistemaDeAlumbrado.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistemaDeAlumbrado.demo.entities.Cuadrilla;
import sistemaDeAlumbrado.demo.repositories.CuadrillaRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CuadrillaService {
    private final CuadrillaRepository cuadrillaRepository;

    private Optional<Cuadrilla> findById(Integer id) {
        return cuadrillaRepository.findById(id);
    }

    public Cuadrilla findCuadrilla(Integer cuadrillaId){
        return this.findById(cuadrillaId)
                .orElseThrow( () -> new RuntimeException("cuadrilla not found"));
    }
}
