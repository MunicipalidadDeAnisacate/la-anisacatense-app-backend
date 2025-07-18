package sistemaDeAlumbrado.demo.dtos;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.proyectos.ProyectoEnConsultaDto;

import java.util.List;

@Data
@Builder
public class ResultadoEleccionDto {
    private Long consultaId;
    private List<ProyectoEnConsultaDto> proyectos;

    public ResultadoEleccionDto(Long consultaId, List<ProyectoEnConsultaDto> proyectos) {
        this.consultaId = consultaId;
        this.proyectos = proyectos;
    }
}
