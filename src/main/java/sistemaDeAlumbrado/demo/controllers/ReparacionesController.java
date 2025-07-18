package sistemaDeAlumbrado.demo.controllers;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sistemaDeAlumbrado.demo.request.CreateReparacionRequest;
import sistemaDeAlumbrado.demo.response.MisArreglosResponse;
import sistemaDeAlumbrado.demo.response.reparaciones.InformacionReparacionResponse;
import sistemaDeAlumbrado.demo.services.ArregloService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reparaciones")
public class ReparacionesController {
    private final ArregloService arregloService;


    @GetMapping("/x/tipo")
    @PreAuthorize("hasAnyRole('tecnico', 'admin')")
    public ResponseEntity<Object> findAllReparacionXTipo(@RequestParam(required = false) Integer barrioId,
                                                         @RequestParam(required = false) Integer tipoId,
                                                         @RequestParam(required = false) Long tecnicoId,
                                                         @RequestParam(required = false) Integer fechaDesdeReparacion,
                                                         @RequestParam(required = false)LocalDate today,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "50") int size){

        Pageable pageable = PageRequest.of(page, size);
        var reparacionesPage = arregloService.findAllArreglosXTipo(barrioId,
                tipoId,
                tecnicoId,
                fechaDesdeReparacion,
                today,
                pageable);

        var reparaciones = reparacionesPage
                .stream()
                .map(MisArreglosResponse::from)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("reparaciones", reparaciones);
        response.put("currentPage", reparacionesPage.getNumber());
        response.put("totalItems", reparacionesPage.getTotalElements());
        response.put("totalPages", reparacionesPage.getTotalPages());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{reparacionId}")
    @PreAuthorize("hasAnyRole('tecnico', 'admin')")
    public ResponseEntity<Object> findOneReparacion(@PathVariable Long reparacionId){
        var reparacion = arregloService.findOneArreglo(reparacionId);
        return ResponseEntity.ok(MisArreglosResponse.from(reparacion));
    }


    @GetMapping("/mios/{tecnicoId}")
    @PreAuthorize("hasAnyRole('tecnico', 'admin')")
    public ResponseEntity<Object> findAllByTecnico(@PathVariable Long tecnicoId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "50") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "fechaArreglo")
                        .and(Sort.by(Sort.Direction.DESC, "horaArreglo"))
        );

        var reparacionesPage = arregloService.getArreglosByTecnico(tecnicoId, pageable);

        var reparaciones = reparacionesPage
                .stream()
                .map(MisArreglosResponse::from)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("reparaciones", reparaciones);
        response.put("currentPage", reparacionesPage.getNumber());
        response.put("totalItems", reparacionesPage.getTotalElements());
        response.put("totalPages", reparacionesPage.getTotalPages());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/informacion/{reparacionId}")
    @PreAuthorize("hasAnyRole('tecnico', 'admin')")
    public ResponseEntity<Object> findOneReparacionInformacion(@PathVariable Long reparacionId) {
        val reparacion = arregloService.findOneReparacionInformacion(reparacionId);
        return ResponseEntity.ok(InformacionReparacionResponse.from(reparacion));
    }



    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('tecnico')")
    public ResponseEntity<Object> createReparacion(@ModelAttribute CreateReparacionRequest request) throws IOException {
        arregloService.createArreglo(request.getTipoReclamoId(),
                request.getLatitudReclamo(),
                request.getLongitudReclamo(),
                request.getTecnico1Id(),
                request.getTecnico2Id(),
                request.getFechaArreglo(),
                request.getHoraArreglo(),
                request.getObservacionArreglo(),
                request.getFotoArreglo(),
                request.getPosteId(),
                request.getIdBarrio());
        return ResponseEntity.ok().build();
    }
}
