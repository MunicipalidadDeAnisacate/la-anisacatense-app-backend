package sistemaDeAlumbrado.demo.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sistemaDeAlumbrado.demo.dtos.MisReclamosDto;
import sistemaDeAlumbrado.demo.request.solicitudes.UpdateSolicitudRequest;
import sistemaDeAlumbrado.demo.response.solicitudes.SolicitudItemListResponse;
import sistemaDeAlumbrado.demo.response.solicitudes.SolicitudesResueltasResponse;
import sistemaDeAlumbrado.demo.request.solicitudes.CreateSolicitudRequest;
import sistemaDeAlumbrado.demo.response.*;
import sistemaDeAlumbrado.demo.response.solicitudes.MisSolicitudesResponse;
import sistemaDeAlumbrado.demo.response.solicitudes.SolicitudViveroItemListResponse;
import sistemaDeAlumbrado.demo.services.ReclamoService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reclamos")
public class SolicitudController {
    private final ReclamoService reclamoService;


    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('tecnico', 'admin')")
    public ResponseEntity<Object> getAllReclamos(@RequestParam(required = false) Integer barrioId,
                                                 @RequestParam(required = false) Integer tipoId,
                                                 @RequestParam(required = false) Integer subTipoId,
                                                 @RequestParam(required = false) Integer estadoId,
                                                 @RequestParam(required = false) Long tecnicoId,
                                                 @RequestParam(required = false) Integer fechaDesdeReclamo,
                                                 @RequestParam(required = false) LocalDate today,
                                                 @RequestParam(required = false) Integer fechaDesdeReparacion,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        val reclamosPage = reclamoService.findAllReclamos(barrioId,
                tipoId,
                subTipoId,
                estadoId,
                tecnicoId,
                fechaDesdeReclamo,
                today,
                fechaDesdeReparacion,
                pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("reclamos", reclamosPage.getContent().stream().map(SolicitudItemListResponse::from).toList());
        response.put("currentPage", reclamosPage.getNumber());
        response.put("totalItems", reclamosPage.getTotalElements());
        response.put("totalPages", reclamosPage.getTotalPages());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('tecnico', 'admin')")
    public ResponseEntity<Object> findOneReclamo(@PathVariable final Long id) {
        val reclamo = reclamoService.findOneReclamo(id);
        return ResponseEntity.ok(SolicitudItemListResponse.from(reclamo));
    }


    // Este metodo es usado por los tecnicos de cuadrilla ambiente, en vez de ver las solicitudes en el mapa
    // se listan las solicitudes
    @GetMapping("/vivero/x/subtipo/list")
    @PreAuthorize("hasAnyRole('tecnico')")
    public ResponseEntity<Object> getAllReclamosVivero(@RequestParam Integer subTipoReclamoId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        val solicitudesPage = reclamoService.findAllReclamosViveroXSubTipo(subTipoReclamoId, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("solicitudes", solicitudesPage.getContent()
                .stream().map(SolicitudViveroItemListResponse::from).toList());
        response.put("currentPage", solicitudesPage.getNumber());
        response.put("totalItems", solicitudesPage.getTotalElements());
        response.put("totalPages", solicitudesPage.getTotalPages());

        return ResponseEntity.ok(response);
    }



    // Crea un reclamo, solo lo puede hacer un vecino o el tecnico en "solicitud como vecino"
    @PostMapping(path = "/nuevo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('tecnico', 'vecino')")
    public ResponseEntity<Object> createReclamo(@ModelAttribute @Valid CreateSolicitudRequest dto) throws IOException {
        reclamoService.createReclamo(
                dto.getIdVecino(),
                dto.getPosteId(),
                dto.getIdAnimal(),
                dto.getIdSubTipoReclamo(),
                dto.getIdBarrio(),
                dto.getFotoReclamo(),
                dto.getHoraReclamo(),
                dto.getFechaReclamo(),
                dto.getLatitud(),
                dto.getLongitud(),
                dto.getObservacionReclamo()
        );
        return ResponseEntity.accepted().build();
    }


    // get de vecino, devuelve el listado de todos sus reclamos
    @GetMapping("/vecino/mios/{vecinoId}")
    @PreAuthorize("hasAnyRole('vecino', 'admin')")
    public ResponseEntity<Map<String, Object>> getMisReclamos(@PathVariable Long vecinoId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MisReclamosDto> reclamosPage = reclamoService.findMyClaims2(vecinoId, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("solicitudes", reclamosPage.getContent().stream().map(MisSolicitudesResponse::from).toList());
        response.put("currentPage", reclamosPage.getNumber());
        response.put("totalItems", reclamosPage.getTotalElements());
        response.put("totalPages", reclamosPage.getTotalPages());

        return ResponseEntity.ok(response);
    }


    // get de tecnico, devuelve sus arreglos
    @GetMapping("/tecnico/mios/{tecnicoId}")
    @PreAuthorize("hasAnyRole('tecnico', 'admin')")
    public ResponseEntity<Object> findMisArreglos(@PathVariable final Long tecnicoId,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "50") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "fechaArreglo")
                        .and(Sort.by(Sort.Direction.DESC, "horaArreglo"))
        );

        val misSolicitudesReparadas = reclamoService.findMisSolicitudesReparadas(tecnicoId, pageable);

        val solicitudesReparadas = misSolicitudesReparadas.getContent()
                .stream()
                .map(SolicitudesResueltasResponse::from)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("solicitudesResueltas", solicitudesReparadas);
        response.put("currentPage", misSolicitudesReparadas.getNumber());
        response.put("totalItems", misSolicitudesReparadas.getTotalElements());
        response.put("totalPages", misSolicitudesReparadas.getTotalPages());

        return ResponseEntity.ok(response);
    }


    // funcion de tecnico, busca reclamos de un tipo de solicitud - este endpoint es para tipos de solicitudes varias,
    // para luminaria se utiliza el mismo getLuces
    @GetMapping("/tecnico/varias/activos/{subTipoReclamoId}")
    @PreAuthorize("hasAnyRole('tecnico', 'admin')")
    public ResponseEntity<Object> getSolicitudesExpandido(@PathVariable final Integer subTipoReclamoId) {
        val reclamos = reclamoService.getAllActiveClaimsExpandido(subTipoReclamoId)
                .stream().map(SolicitudesVariasResponse::from);
        return ResponseEntity.ok(reclamos);
    }


    // funcion de vecino, busca los reclamos de lumnaria y los devuelve segun el tipo de solicitud,
    // esta funcion es para la representacion en el mapa
    @GetMapping("/vecino/luminarias/{vecinoId}/{subTipoReclamoId}")
    @PreAuthorize("hasRole('vecino')")
    public ResponseEntity<Object> getLuces(@PathVariable final Long vecinoId,
                                           @PathVariable final Integer subTipoReclamoId) {
        val postes = reclamoService.findMyActiveLuminariaClaims(vecinoId, subTipoReclamoId)
                .stream()
                .map(PosteResponse::from);
        return ResponseEntity.ok(postes);
    }


    // funcion de tecnico, busca los reclamos de lumnaria y los devuelve segun el tipo de solicitud,
    // esta funcion es para la representacion en el mapa
    @GetMapping("/tecnico/luminarias/{subTipoReclamoId}")
    @PreAuthorize("hasAnyRole('tecnico', 'admin')")
    public ResponseEntity<Object> getLucesTecnico(@PathVariable final Integer subTipoReclamoId) {
        val postes = reclamoService.findMyLuminariaClaimsTecnico(subTipoReclamoId)
                .stream()
                .map(PosteResponseTecnico::from);
        return ResponseEntity.ok(postes);
    }


    @GetMapping("/completo/{reclamoId}")
    @PreAuthorize("hasAnyRole('tecnico', 'admin')")
    public ResponseEntity<Object> getReclamo(@PathVariable final Long reclamoId) {
        val reclamo = reclamoService.getInformacionReclamo(reclamoId);
        return ResponseEntity.ok(InformacionReclamoResponse.from(reclamo));
    }


    // cambia estado de reclamo de en proceso a resuelto
    @PatchMapping(path = "/finalizado", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('tecnico')")
    public ResponseEntity<Object> finalizeClaim(@ModelAttribute UpdateSolicitudRequest dto) throws IOException {
        reclamoService.finalizeClaim(
                dto.getIdReclamo(),
                dto.getIdSubTipoReclamo(),
                dto.getPosteId(),
                dto.getTecnico1Id(),
                dto.getTecnico2Id(),
                dto.getFechaArreglo(),
                dto.getHoraArreglo(),
                dto.getTiposReparacionesIds(),
                dto.getObservacionArreglo(),
                dto.getFotografiaArreglo());
        return ResponseEntity.ok().build();
    }
}
