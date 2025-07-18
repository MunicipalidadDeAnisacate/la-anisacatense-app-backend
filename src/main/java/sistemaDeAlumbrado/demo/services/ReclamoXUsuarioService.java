package sistemaDeAlumbrado.demo.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import sistemaDeAlumbrado.demo.entities.*;
import sistemaDeAlumbrado.demo.repositories.ReclamoXUsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ReclamoXUsuarioService {
    private final ReclamoXUsuarioRepository reclamoXUsuarioRepository;
    private final TwilioService twilioService;


    public List<ReclamoXUsuario> getReclamoXUsuario(Reclamo reclamo) {
        return reclamoXUsuarioRepository.findAllReclamoXUsuariosByReclamoXUsuarioId_Reclamo(reclamo);
    }


    @Transactional
    public void save(ReclamoXUsuario reclamoXUsuario){
        reclamoXUsuarioRepository.save(reclamoXUsuario);
    }


    @TransactionalEventListener
    public void notificarAVecinosListener(Reclamo reclamo) {
        notificarAVecinos(reclamo);
    }


    @Transactional
    public void notificarAVecinos(Reclamo reclamo) {
        List<UsuarioEntity> vecinos = reclamoXUsuarioRepository.findVecinosByReclamo(reclamo.getId());

        if (vecinos.isEmpty()) {
            return;
        }

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (UsuarioEntity vecino : vecinos) {
            CompletableFuture<Void> future = twilioService.sendFinishClaimMessageAsync(
                    vecino.getTelefono(),
                    reclamo.getId(),
                    reclamo.getSubTipoReclamo().getTipoReclamo().getNombreTipo(),
                    reclamo.getSubTipoReclamo().getNombreSubTipo());
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
