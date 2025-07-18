package sistemaDeAlumbrado.demo.response.proyectos;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.entities.Proyecto;

@Data
@Builder
public class ProyectoResponse {
    private Long id;
    private String titulo;
    private String descripcion;
    private String nombreUsuario;
    private String dniUsuario;

    public static ProyectoResponse from(Proyecto proyecto) {
        return ProyectoResponse
                .builder()
                .id(proyecto.getId())
                .titulo(proyecto.getTitulo())
                .descripcion(proyecto.getDescripcion() != null ? proyecto.getDescripcion() : null)
                .nombreUsuario(proyecto.getUsuario().getNombre() + " " + proyecto.getUsuario().getApellido())
                .dniUsuario(proyecto.getUsuario().getDni())
                .build();
    }
}
