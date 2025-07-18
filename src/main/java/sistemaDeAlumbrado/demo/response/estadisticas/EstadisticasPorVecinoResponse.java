package sistemaDeAlumbrado.demo.response.estadisticas;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.estadisticas.EstadisticasPorVecinosDto;

import java.util.List;


@Data
@Builder
public class EstadisticasPorVecinoResponse {
    private Long  cantidadVecinosRegistrados;
    private Long cantidadVecinosSolicitaron;
    private List<EstadisticasVecinoCantSolicitudesResponse> vecinosMasSolicitaron;

    public static EstadisticasPorVecinoResponse from(EstadisticasPorVecinosDto dto){
        return EstadisticasPorVecinoResponse
                .builder()
                .cantidadVecinosRegistrados(dto.getCantidadVecinosEnLaApp())
                .cantidadVecinosSolicitaron(dto.getCantidadVecinosSolicitaron())
                .vecinosMasSolicitaron(dto.getEstadisticasVecinoCantSolicitudes()
                        .stream()
                        .map(EstadisticasVecinoCantSolicitudesResponse::from)
                        .toList()
                )
                .build();
    }
}
