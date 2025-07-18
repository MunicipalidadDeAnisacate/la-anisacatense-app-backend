package sistemaDeAlumbrado.demo.response.estadisticas;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.estadisticas.EstadisticasVecinoCantSolicitudesDto;

@Data
@Builder
public class EstadisticasVecinoCantSolicitudesResponse {
    private String nombreVecino;
    private String apellidoVecino;
    private String dni;
    private Long cantSolicitudesProceso;
    private Long cantSolicitudesResueltas;

    public static EstadisticasVecinoCantSolicitudesResponse from(EstadisticasVecinoCantSolicitudesDto dto){
        return EstadisticasVecinoCantSolicitudesResponse
                .builder()
                .nombreVecino(dto.getNombreVecino())
                .apellidoVecino(dto.getApellidoVecino())
                .dni(dto.getDni())
                .cantSolicitudesProceso(dto.getCantSolicitudesProceso())
                .cantSolicitudesResueltas(dto.getCantSolicitudesResueltas())
                .build();
    }
}
