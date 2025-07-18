package sistemaDeAlumbrado.demo.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistemaDeAlumbrado.demo.dtos.PosteDto;
import sistemaDeAlumbrado.demo.entities.Barrio;
import sistemaDeAlumbrado.demo.entities.Poste;
import sistemaDeAlumbrado.demo.entities.Reclamo;
import sistemaDeAlumbrado.demo.repositories.PosteRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PosteService {
    private final PosteRepository posteRepository;
    private final BarrioService barrioService;

    public Poste findById(final Integer posteId){
        return posteRepository.findById(posteId)
                .orElseThrow(() -> new RuntimeException("poste not found"));
    }

    public Poste getOrNull(final Integer idPoste){
        Poste poste = null;
        if (idPoste != null){
            poste = this.findById(idPoste);
        }
        return poste;
    }

    public List<Poste> getAll(){
        return posteRepository.findAll();
    }


    @Transactional
    public void createPoste(final String nombrePoste,
                            final Integer barrioId,
                            final Double latitud,
                            final Double longitud,
                            final LocalDate fechaCarga){
        Barrio barrio = barrioService.findBarrioById(barrioId);

        Poste poste = new Poste(nombrePoste, barrio, latitud, longitud, fechaCarga);

        posteRepository.save(poste);
    }
}
