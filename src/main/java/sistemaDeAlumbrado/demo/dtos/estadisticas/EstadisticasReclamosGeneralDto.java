package sistemaDeAlumbrado.demo.dtos.estadisticas;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class EstadisticasReclamosGeneralDto {
    private Long cantReclamosEnProceso;
    private Long cantReclamosResueltos;
    private Long cantReclamosRecibidosDesde;
    private List<ReclamosXSemanaDto> cantReclamosXSemana;
}
