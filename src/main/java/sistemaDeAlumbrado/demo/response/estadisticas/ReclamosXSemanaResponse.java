package sistemaDeAlumbrado.demo.response.estadisticas;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.estadisticas.ReclamosXSemanaDto;
import sistemaDeAlumbrado.demo.response.localsFormatter.LocalsFormatter;


@Data
@Builder
public class ReclamosXSemanaResponse {
    private String fechaDesde;
    private String fechaHasta;
    private long cantidadResueltas;

    public static ReclamosXSemanaResponse from(ReclamosXSemanaDto dto){
        return ReclamosXSemanaResponse
                .builder()
                .fechaHasta(LocalsFormatter.formatearFecha(dto.getFechaHasta()))
                .fechaDesde(LocalsFormatter.formatearFecha(dto.getFechaDesde()))
                .cantidadResueltas(dto.getCantidadResuelto())
                .build();
    }
}
