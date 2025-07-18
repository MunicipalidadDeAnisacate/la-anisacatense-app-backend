package sistemaDeAlumbrado.demo.response.estadisticas;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.estadisticas.EstadisticasReclamosXSubTipoDto;

@Data
@Builder
public class EstadisticasSolicitudesXSubTipoResponse {
    private Integer idSubTipoSolicitud;
    private String nombreSubTipoSolicitud;
    private Long cantSolicitudesResueltas;
    private Long cantSolicitudesEnProceso;
    private Long cantSolicitudesConCambioFoco;
    private Long cantSolicitudesConCambioFotocelula;
    private Long cantSolicitudesConCambioFusible;
    private Long cantSolicitudesConOtro;

    public static EstadisticasSolicitudesXSubTipoResponse from(EstadisticasReclamosXSubTipoDto dto){
        return EstadisticasSolicitudesXSubTipoResponse
                .builder()
                .idSubTipoSolicitud(dto.getIdSubTipoReclamo())
                .nombreSubTipoSolicitud(dto.getNombreSubTipoReclamo())
                .cantSolicitudesResueltas(dto.getCantResueltas())
                .cantSolicitudesEnProceso(dto.getCantEnProceso())
                .cantSolicitudesConCambioFoco(dto.getCantCambioFoco())
                .cantSolicitudesConCambioFusible(dto.getCantCambioFusible())
                .cantSolicitudesConCambioFotocelula(dto.getCantCambioFotocelula())
                .cantSolicitudesConOtro(dto.getCantOtro())
                .build();
    }
}
