package sistemaDeAlumbrado.demo.response.estadisticas;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.estadisticas.EstadisticasReclamosXBarrioDto;

@Data
@Builder
public class EstadisticasSolicitudesXBarrioResponse {
    private Integer zona;
    private String nombreBarrio;
    private Long cantSolicitudesResueltas;
    private Long cantSolicitudesEnProceso;

    public static EstadisticasSolicitudesXBarrioResponse from(EstadisticasReclamosXBarrioDto dto){
        return EstadisticasSolicitudesXBarrioResponse
                .builder()
                .zona(dto.getZona())
                .nombreBarrio(dto.getNombreBarrio())
                .cantSolicitudesEnProceso(dto.getCantEnProceso())
                .cantSolicitudesResueltas(dto.getCantResueltos())
                .build();
    }
}
