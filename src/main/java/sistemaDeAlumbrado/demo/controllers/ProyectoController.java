package sistemaDeAlumbrado.demo.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sistemaDeAlumbrado.demo.request.CreateProyectoRequest;
import sistemaDeAlumbrado.demo.response.proyectos.InformacionProyectoResponse;
import sistemaDeAlumbrado.demo.response.proyectos.ProyectoResponse;
import sistemaDeAlumbrado.demo.services.ProyectoService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/proyectos")
@RequiredArgsConstructor
public class ProyectoController {
    private final ProyectoService proyectoService;


    @GetMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> getAllProyectos(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("id").descending()
        );

        var proyectos = proyectoService.getAll(pageable);
        
        return ResponseEntity.ok(Map.of(
                "proyectos", proyectos.getContent().stream()
                        .map(ProyectoResponse::from).collect(Collectors.toList()),
                "currentPage", proyectos.getNumber(),
                "totalItems", proyectos.getTotalElements(),
                "totalPages", proyectos.getTotalPages()
        ));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> getProyecto(@PathVariable Long id) {
        var proyecto = proyectoService.getProyecto(id);
        return ResponseEntity.ok(InformacionProyectoResponse.from(proyecto));
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('vecino', 'admin')")
    public ResponseEntity<Object> createProyecto(@ModelAttribute @Valid CreateProyectoRequest proyecto)
            throws IOException {
        proyectoService.createProyecto(proyecto.getTitulo(),
                proyecto.getDescripcion(),
                proyecto.getArchivoUrl(),
                proyecto.getUsuarioId()
        );
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{proyectoId}")
    @PreAuthorize("hasAnyRole('admin')")
    public ResponseEntity<Object> deleteProyecto(@PathVariable Long proyectoId, @RequestParam LocalDate today) {
        proyectoService.deleteProyecto(proyectoId, today);
        return ResponseEntity.ok().build();
    }
}
