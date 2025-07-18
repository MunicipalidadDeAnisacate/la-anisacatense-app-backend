package sistemaDeAlumbrado.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistemaDeAlumbrado.demo.entities.Reclamo;
import sistemaDeAlumbrado.demo.entities.ReclamoXTipoReparacion;
import sistemaDeAlumbrado.demo.entities.TipoReparacion;
import sistemaDeAlumbrado.demo.repositories.TipoReparacionXReclamoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoReparacionXReclamoService {
    private final TipoReparacionXReclamoRepository tipoReparacionXReclamoRepository;

    public List<TipoReparacion> getAllFromReclamo(Reclamo reclamo) {
        List<ReclamoXTipoReparacion> reclamoXTipoReparacionList = tipoReparacionXReclamoRepository.findAllByReclamo(reclamo);

        if (reclamoXTipoReparacionList.isEmpty()) {
            return new ArrayList<>();
        }

        return reclamoXTipoReparacionList
                .stream()
                .map(x -> x.getReclamoXTipoReparacionId().getTipoReparacion())
                .toList();
    }
}
