package sistemaDeAlumbrado.demo.controllers;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sistemaDeAlumbrado.demo.request.CreatePosteRequest;
import sistemaDeAlumbrado.demo.response.PosteSinFiltroResponse;
import sistemaDeAlumbrado.demo.services.PosteService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/postes")
public class PosteController {
    private final PosteService posteService;

    @PostMapping
    @PreAuthorize("hasRole('tecnico')")
    public ResponseEntity<Object> createPoste(@RequestBody CreatePosteRequest request) {
        posteService.createPoste(request.getNombrePoste(),
                request.getIdBarrio(),
                request.getLatitud(),
                request.getLongitud(),
                request.getFechaCarga());
        return ResponseEntity.ok().build();
    }


    // metodo para ver los postes actuales antes de cargar nuevo poste
    @GetMapping
    @PreAuthorize("hasRole('tecnico')")
    public ResponseEntity<Object> getPostes(){
        val postes = posteService.getAll()
                .stream()
                .map(PosteSinFiltroResponse::from)
                .toList();
        return ResponseEntity.ok().body(postes);
    }
}
