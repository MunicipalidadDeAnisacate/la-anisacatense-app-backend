package sistemaDeAlumbrado.demo.dtos.proyectos;

import lombok.Data;

@Data
public class ProyectoEnConsultaDto {
    private Long id;
    private String titulo;
    private String descripcion;
    private String objectName;
    private String archivoUrl;
    private Long cantidad;

    public ProyectoEnConsultaDto(Long id, String titulo, String descripcion, String objectName, Long cantidad) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.objectName = objectName;
        this.cantidad = cantidad;
    }

    public ProyectoEnConsultaDto(Long id, Long cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }
}
