package sistemaDeAlumbrado.demo.response.estadisticas;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.estadisticas.EstadisticasReclamosXTipoDto;

import java.util.List;

@Data
@Builder
public class EstadisticasSolicitudesXTipoResponse {
    private String nombreTipoSolicitud;
    private Long cantSolicitudesResueltas;
    private Long cantSolicitudesEnProceso;
    List<EstadisticasSolicitudesXSubTipoResponse> estadisticasSolicitudesXSubTipo;

    public static EstadisticasSolicitudesXTipoResponse from(EstadisticasReclamosXTipoDto dto){
        return EstadisticasSolicitudesXTipoResponse
                .builder()
                .nombreTipoSolicitud(dto.getNombreTipoReclamo())
                .cantSolicitudesResueltas(dto.getCantResueltas())
                .cantSolicitudesEnProceso(dto.getCantEnProceso())
                .estadisticasSolicitudesXSubTipo(dto
                        .getEstadisticasReclamosXSubTipoDtoList()
                        .stream()
                        .map(EstadisticasSolicitudesXSubTipoResponse::from)
                        .toList()
                )
                .build();
    }
}
