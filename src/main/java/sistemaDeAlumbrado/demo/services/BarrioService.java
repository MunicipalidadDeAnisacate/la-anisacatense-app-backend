package sistemaDeAlumbrado.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistemaDeAlumbrado.demo.entities.Barrio;
import sistemaDeAlumbrado.demo.repositories.BarrioRepository;

@Service
@RequiredArgsConstructor
public class BarrioService {
    final BarrioRepository barrioRepository;

    public Barrio findBarrioById(Integer id) {
        return barrioRepository.findById(id)
                .orElseThrow( ()-> new RuntimeException("Barrio no encontrado"));
    }

    public Barrio getOrNull(final Integer idBarrio){
        Barrio barrio = null;
        if (idBarrio != null){
            barrio = this.findBarrioById(idBarrio);
        }
        return barrio;
    }
}
