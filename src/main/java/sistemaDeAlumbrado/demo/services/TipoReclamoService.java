package sistemaDeAlumbrado.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistemaDeAlumbrado.demo.entities.SubTipoReclamo;
import sistemaDeAlumbrado.demo.entities.TipoReclamo;
import sistemaDeAlumbrado.demo.repositories.TipoReclamoRepository;

@Service
@RequiredArgsConstructor
public class TipoReclamoService {
    private final TipoReclamoRepository tipoReclamoRepository;

    public TipoReclamo findById(Integer id){
        return tipoReclamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("tipo reclamo not found"));
    }
}
