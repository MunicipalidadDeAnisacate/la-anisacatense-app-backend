package sistemaDeAlumbrado.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sistemaDeAlumbrado.demo.dtos.consultasCiudadanas.InformacionConsultaActivaDto;
import sistemaDeAlumbrado.demo.request.CreateConsultaRequest;
import sistemaDeAlumbrado.demo.response.consultasCiudadanas.ConsultaItemResponse;
import sistemaDeAlumbrado.demo.response.consultasCiudadanas.InformacionEleccionActivaResponse;
import sistemaDeAlumbrado.demo.services.ConsultaCiudadanaService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/consultasciudadanas")
@RequiredArgsConstructor
public class ConsultaCiudadanaController {
    private final ConsultaCiudadanaService consultaCiudadanaService;


    @GetMapping("/activas")
    @PreAuthorize("hasRole('vecino')")
    public ResponseEntity<Object> getConsultasActivas(@RequestParam LocalDate today,
                                                       @RequestParam LocalTime now) {
        var consultas = consultaCiudadanaService
                .findConsultasActivas(today, now)
                .stream()
                .map(ConsultaItemResponse::from)
                .toList();

        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/informacion/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> getInformacionConsulta(@PathVariable Long id) {
        var consulta = consultaCiudadanaService.findInformacionConsultaCiudadana(id);
        return ResponseEntity.ok(InformacionEleccionActivaResponse.from(consulta));
    }


    @GetMapping("/activas/{id}/{usuarioId}")
    @PreAuthorize("hasRole('vecino')")
    public ResponseEntity<Object> getConsultaActiva(@PathVariable Long id,
                                                    @PathVariable Long usuarioId) {
        var consulta = consultaCiudadanaService.findConsultaActiva(id, usuarioId);
        return ResponseEntity.ok(InformacionEleccionActivaResponse.from(consulta));
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> getAllConsultas(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        var consultas = consultaCiudadanaService.findAll(pageable);

        System.out.println( consultas.getContent().stream()
                .map(ConsultaItemResponse::from)
                .collect(Collectors.toList()));

        return ResponseEntity.ok(Map.of(
                "consultas", consultas.getContent().stream()
                                .map(ConsultaItemResponse::from)
                                .collect(Collectors.toList()),
                "currentPage", consultas.getNumber(),
                "totalItems", consultas.getTotalElements(),
                "totalPages", consultas.getTotalPages()
        ));
    }


    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> createConsulta(@RequestBody CreateConsultaRequest consulta) {
        consultaCiudadanaService.createConsulta(
                consulta.getTitulo(),
                consulta.getDescripcion(),
                consulta.getFechaInicio(),
                consulta.getHoraInicio(),
                consulta.getFechaCierre(),
                consulta.getHoraCierre(),
                consulta.getMostrarRecuento(),
                consulta.getProyectosId()
        );
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/mostrarrecuento/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> patchMostrarRecuentoConsulta(@PathVariable Long id) {
        consultaCiudadanaService.patchMostrarRecuentoConsulta(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/cerrar/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> patchCerrarConsulta(@PathVariable Long id,
                                                      @RequestParam LocalDate today,
                                                      @RequestParam LocalTime now) {
        consultaCiudadanaService.patchCerrarConsulta(id, today, now);
        return ResponseEntity.ok().build();
    }
}
