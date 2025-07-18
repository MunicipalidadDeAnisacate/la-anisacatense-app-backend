package sistemaDeAlumbrado.demo.response.estadisticas;

import lombok.Builder;
import lombok.Data;

import sistemaDeAlumbrado.demo.dtos.estadisticas.EstadisticasReclamosGeneralDto;

import java.util.List;


@Builder
@Data
public class EstadisticasSolicitudesGeneralResponse {
    private Long cantSolicitudesEnProceso;
    private Long cantSolicitudesResueltos;
    private List<ReclamosXSemanaResponse> reclamosXSemanaResponse;

    public static EstadisticasSolicitudesGeneralResponse from(EstadisticasReclamosGeneralDto dto){
        return EstadisticasSolicitudesGeneralResponse
                .builder()
                .cantSolicitudesEnProceso(dto.getCantReclamosEnProceso())
                .cantSolicitudesResueltos(dto.getCantReclamosResueltos())
                .reclamosXSemanaResponse(dto.getCantReclamosXSemana().stream().map(ReclamosXSemanaResponse::from).toList())
                .build();
    }
}
