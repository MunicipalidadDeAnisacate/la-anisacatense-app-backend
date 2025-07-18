package sistemaDeAlumbrado.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sistemaDeAlumbrado.demo.request.PreferenciaRequest;
import sistemaDeAlumbrado.demo.services.PreferenciaService;

@RestController
@RequestMapping("/preferencias")
@RequiredArgsConstructor
public class PreferenciaController {
    private final PreferenciaService preferenciaService;

    @PostMapping
    @PreAuthorize("hasRole('vecino')")
    public ResponseEntity<Object> elegirProyecto(@RequestBody PreferenciaRequest req) {
        preferenciaService.addPreferenciaToConsulta(
                req.getConsultaId(),
                req.getProyectoId(),
                req.getUsuarioId(),
                req.getFecha(),
                req.getHora()
        );
        return ResponseEntity.ok().build();
    }

}
