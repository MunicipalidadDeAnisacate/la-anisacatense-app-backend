package sistemaDeAlumbrado.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sistemaDeAlumbrado.demo.dtos.estadisticas.*;
import sistemaDeAlumbrado.demo.repositories.EstadisticasRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstadisticasService {
    private final EstadisticasRepository estadisticasRepository;

    // SOLICITUDES GENERALES
    public EstadisticasReclamosGeneralDto getEstadisticasSolicitudesGeneral(final LocalDate today,
                                                                            final Integer numSemanas){
        return EstadisticasReclamosGeneralDto
                .builder()
                .cantReclamosEnProceso(getCantidadReclamosByEstado(1))
                .cantReclamosResueltos(getCantidadReclamosByEstado(2))
                .cantReclamosXSemana(getCantidadSolicitudesRecibidasDesde(today, numSemanas))
                .build();
    }


    public List<ReclamosXSemanaDto> getCantidadSolicitudesRecibidasDesde(final LocalDate today,
                                                                         final Integer numSemanas){
        return estadisticasRepository
                .findReclamosPorSemana(today, numSemanas)
                .stream()
                .map(ReclamosXSemanaDto::new)
                .map(dto -> {
                    dto.modificarFechaHastaUltima(today);
                    return dto;
                })
                .collect(Collectors.toList());
    }



    // SOLICITUDES POR BARRIO
    public List<EstadisticasReclamosXBarrioDto> getEstadisticasSolicitudesXBarrio(){
        return estadisticasRepository.reclamosPorBarrio();
    }


    public List<EstadisticasReclamosXBarrioDto> getEstadisticasSolicitudesXBarrioTipoSolicitud(final Integer tipoReclamoId){
        return estadisticasRepository
                .reclamosPorBarrioTipoReclamo(tipoReclamoId);
    }


    public List<EstadisticasReclamosXBarrioDto> getEstadisticasSolicitudesXBarrioSubTipoSolicitudes(
                                                                                    final Integer subTipoReclamoId){
        return estadisticasRepository
                .reclamosPorBarrioSubTipoReclamo(subTipoReclamoId);
    }


    // SOLICITUDES POR TIPO
    public List<EstadisticasReclamosXTipoDto> getEstadisticasSolicitudesPorTipo(){
        List<EstadisticasReclamosXTipoDto> estadisticasDeTipoList = estadisticasRepository.reclamoPorTipoReclamo();

        estadisticasDeTipoList.forEach(e ->
                e.setEstadisticasReclamosXSubTipo(
                        estadisticasRepository.reclamosPorSubTipoDeTipo(e.getIdTipo())
                )
        );

        return estadisticasDeTipoList
                .stream()
                .map(this::getEstadisticasLuminaria)
                .collect(Collectors.toList());
    }


    public EstadisticasReclamosXSubTipoDto getEstadisticasLuminariaDesde(final Integer diasAntes,
                                                                         final LocalDate fechaDesde){
        EstadisticasReclamosXSubTipoDto dto = new EstadisticasReclamosXSubTipoDto();
        return this.getConEstadisticasLuminariaDesde(dto, fechaDesde, diasAntes);
    }


    // VECINOS CON MAS SOLICITUDES
    public EstadisticasPorVecinosDto getEstadisticasSolicitudesPorVecino(){

        Long cantidadVecinos = this.getCantidadVecinosApp();

        Long cantidadVecinosSolicitaron = this.getCantidadVecinosSolicitaron();

        // cantidad de vecinos que devuelve
        Integer cantidad = 10;
        Pageable limite = PageRequest.of(0, cantidad);

        List<EstadisticasVecinoCantSolicitudesDto> listVecinosMasSolicitudes = estadisticasRepository
                .getVecinosMasSolicitudes(limite);

        return new EstadisticasPorVecinosDto(cantidadVecinos,
                cantidadVecinosSolicitaron,
                listVecinosMasSolicitudes
        );
    }


    // METODOS PRIVATE
    // SOLICITUDES GENERALES
    private Long getCantidadReclamosByEstado(Integer estadoId){
        return estadisticasRepository.countReclamosByEstadoReclamo(estadoId);
    }


    // SOLICITUDES POR TIPO
    private EstadisticasReclamosXTipoDto getEstadisticasLuminaria(EstadisticasReclamosXTipoDto estadisticasDeTipo) {

        if (!estadisticasDeTipo.getIdTipo().equals(1)) {
            return estadisticasDeTipo;
        }

        for (EstadisticasReclamosXSubTipoDto statSubTipo :
                estadisticasDeTipo.getEstadisticasReclamosXSubTipoDtoList()) {

            if (statSubTipo.getIdSubTipoReclamo().equals(1)) {

                EstadisticasLuminariaDto dto = estadisticasRepository.estadisticasReclamosLuminaria();

                statSubTipo.setEstaditicasReclamosLuminaria(
                        dto.getCantSolicitudesConCambioFoco(),
                        dto.getCantSolicitudesConCambioFusible(),
                        dto.getCantSolicitudesConCambioFotocelula(),
                        dto.getCantSolicitudesConOtro()
                );
            }
        }
        return estadisticasDeTipo;
    }


    private EstadisticasReclamosXSubTipoDto getConEstadisticasLuminariaDesde(EstadisticasReclamosXSubTipoDto estadisticaSubTipo,
                                                                             LocalDate fechaDesde,
                                                                             Integer diasAntes){
        EstadisticasLuminariaDto dto;

        if (diasAntes == 1 || diasAntes == 0){
            LocalDate soloFecha = fechaDesde.minusDays(diasAntes);
            dto = estadisticasRepository.estadisticasReclamosLuminariaSoloFecha(soloFecha);
        } else {
            LocalDate fechaDesdeFinal = fechaDesde.minusDays(diasAntes);
            dto = estadisticasRepository.estadisticasReclamosLuminariaDesde(fechaDesdeFinal);
        }

        estadisticaSubTipo.setEstaditicasReclamosLuminaria(
                dto.getCantSolicitudesConCambioFoco(),
                dto.getCantSolicitudesConCambioFusible(),
                dto.getCantSolicitudesConCambioFotocelula(),
                dto.getCantSolicitudesConOtro()
        );

        return  estadisticaSubTipo;
    }


    // METODOS PRIVADOS DE ESTADICTICAS POR VECINO
    private Long getCantidadVecinosApp(){
        return estadisticasRepository.countVecinos();
    }

    private Long getCantidadVecinosSolicitaron(){
        return estadisticasRepository.countVecinosSolicitaron();
    }
}
