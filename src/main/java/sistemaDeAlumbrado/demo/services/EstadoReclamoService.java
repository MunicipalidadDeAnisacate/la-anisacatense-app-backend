package sistemaDeAlumbrado.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistemaDeAlumbrado.demo.entities.EstadoReclamo;
import sistemaDeAlumbrado.demo.repositories.EstadoReclamoRepository;

@Service
@RequiredArgsConstructor
public class EstadoReclamoService {
    private final EstadoReclamoRepository estadoReclamoRepository;

    public EstadoReclamo findById(Integer id) {
        return estadoReclamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("estado not found"));
    }

    public EstadoReclamo findEstadoActivo(){
        return findById(1);
    }

    public EstadoReclamo findEstadoResuelto(){
        return findById(2);
    }
}
