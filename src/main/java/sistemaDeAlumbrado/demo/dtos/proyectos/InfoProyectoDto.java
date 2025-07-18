package sistemaDeAlumbrado.demo.dtos.proyectos;

import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.consultasCiudadanas.ConsultaDeProyectoDto;
import sistemaDeAlumbrado.demo.entities.Proyecto;

import java.util.List;

@Data
public class InfoProyectoDto {
    private Long id;
    private String titulo;
    private String descripcion;
    private String archivoUrl;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String dniUsuario;
    private String mailUsuario;
    private String telefonoUsuario;
    private String estadoProyecto;
    private List<ConsultaDeProyectoDto> consultaDeProyectoDtoList;

    public InfoProyectoDto(Proyecto proyecto) {
        this.id = proyecto.getId();
        this.titulo = proyecto.getTitulo();
        this.descripcion = proyecto.getDescripcion();
        this.archivoUrl = proyecto.getObjectName();
        this.nombreUsuario = proyecto.getUsuario().getNombre();
        this.apellidoUsuario = proyecto.getUsuario().getApellido();
        this.dniUsuario = proyecto.getUsuario().getDni();
        this.mailUsuario = proyecto.getUsuario().getMail();
        this.telefonoUsuario = proyecto.getUsuario().getTelefono();
        this.consultaDeProyectoDtoList = proyecto.getElecciones()
                .stream().map(ConsultaDeProyectoDto::new).toList();
    }
}
