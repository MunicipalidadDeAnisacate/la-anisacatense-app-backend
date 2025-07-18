package sistemaDeAlumbrado.demo.response.proyectos;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.proyectos.ProyectoEnConsultaDto;

@Data
@Builder
public class ProyectoEnEleccionResponse {
    private Long id;
    private String titulo;
    private String descripcion;
    private String archivoUrl;
    private Long cantidad;

    public static ProyectoEnEleccionResponse from(ProyectoEnConsultaDto dto) {
        return ProyectoEnEleccionResponse
                .builder()
                .id(dto.getId())
                .titulo(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .archivoUrl(dto.getArchivoUrl())
                .cantidad(dto.getCantidad())
                .build();
    }
}
