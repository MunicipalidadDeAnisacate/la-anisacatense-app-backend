package sistemaDeAlumbrado.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import sistemaDeAlumbrado.demo.entities.SubTipoReclamo;
import sistemaDeAlumbrado.demo.repositories.SubTipoReclamoRepository;

@Service
@RequiredArgsConstructor
public class SubTipoReclamoService {
    private final SubTipoReclamoRepository subTipoReclamoRepository;

    public SubTipoReclamo findById(Integer id){
        return subTipoReclamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("subTipoReclamo not found"));
    }
}
