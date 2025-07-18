package sistemaDeAlumbrado.demo.controllers;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sistemaDeAlumbrado.demo.response.estadisticas.*;
import sistemaDeAlumbrado.demo.services.EstadisticasService;

import java.time.LocalDate;


@RestController
@RequiredArgsConstructor
@RequestMapping("/estadisticas")
public class EstadisticasController {
    private final EstadisticasService estadisticasService;

    // SOLICITUDES GENERALES
    @GetMapping("/solicitudes/generales")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object>
            getEstaditicasSolicitudesGenerales(@RequestParam LocalDate today, @RequestParam Integer numSemanas) {
        val estadisticas = estadisticasService.getEstadisticasSolicitudesGeneral(today, numSemanas);
        return ResponseEntity.ok(EstadisticasSolicitudesGeneralResponse.from(estadisticas));
    }

    @GetMapping("/solicitudes/generales/desde")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object>
            getEstadisticaSolicitudesGeneralesRecibidasDesde(@RequestParam LocalDate today, @RequestParam Integer numSemanas) {
        val estadisticas = estadisticasService
                .getCantidadSolicitudesRecibidasDesde(today, numSemanas)
                .stream()
                .map(ReclamosXSemanaResponse::from)
                .toList();
        return ResponseEntity.ok(estadisticas);
    }



    // SOLICITUDES POR BARRIO
    @GetMapping("/solicitudes/barrios")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> getEstadisticasSolicitudesXBarrios() {
        val estadisticas = estadisticasService
                    .getEstadisticasSolicitudesXBarrio()
                    .stream()
                    .map(EstadisticasSolicitudesXBarrioResponse::from)
                    .toList();
        return ResponseEntity.ok(estadisticas);
    }


    @GetMapping("/solicitudes/barrios/{tipoSolicitudId}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> getEstadisticasSolicitudesXBarriosTipoSolicitudes(@PathVariable Integer tipoSolicitudId) {
        val estadisticas = estadisticasService
                .getEstadisticasSolicitudesXBarrioTipoSolicitud(tipoSolicitudId)
                .stream()
                .map(EstadisticasSolicitudesXBarrioResponse::from)
                .toList();
        return ResponseEntity.ok(estadisticas);
    }


    @GetMapping("/solicitudes/barrios/{tipoSolicitudid}/{subTipoSolicitudId}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> getEstadisticasSolicitudesXBarriosTipoSubTipoSolicitudes(@PathVariable Integer subTipoSolicitudId) {
        val estadisticas = estadisticasService
                    .getEstadisticasSolicitudesXBarrioSubTipoSolicitudes(subTipoSolicitudId)
                    .stream()
                    .map(EstadisticasSolicitudesXBarrioResponse::from)
                    .toList();
        return ResponseEntity.ok(estadisticas);
    }


    // SOLICITUDES POR TIPO
    @GetMapping("/solicitudes/tipos")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> getEstadisticasSolicitudesXTipoSolicitudes() {
        val estadisticas = estadisticasService
                .getEstadisticasSolicitudesPorTipo()
                .stream()
                .map(EstadisticasSolicitudesXTipoResponse::from);
        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/solicitudes/tipos/alumbrado")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> getEstadisticasSolicitudesXTipoSolicitudesDesde(@RequestParam Integer diasAntes,
                                                                                  @RequestParam LocalDate fechaDesde) {
        val estadisticas = estadisticasService.getEstadisticasLuminariaDesde(diasAntes, fechaDesde);
        return ResponseEntity.ok(EstadisticasSolicitudesXSubTipoResponse.from(estadisticas));
    }

    @GetMapping("/usuarios/solicitudes")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> getEstadisticasVecinosMasSolicitudes() {
        val estadisticas = estadisticasService.getEstadisticasSolicitudesPorVecino();
        return ResponseEntity.ok(EstadisticasPorVecinoResponse.from(estadisticas));
    }
}
