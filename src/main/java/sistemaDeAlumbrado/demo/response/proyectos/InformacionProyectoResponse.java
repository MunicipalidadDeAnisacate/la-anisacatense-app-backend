package sistemaDeAlumbrado.demo.response.proyectos;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.proyectos.InfoProyectoDto;
import sistemaDeAlumbrado.demo.response.consultasCiudadanas.ConsultaItemResponse;

import java.util.List;

@Data
@Builder
public class InformacionProyectoResponse {
    private Long id;
    private String titulo;
    private String descripcion;
    private String archivoUrl;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String dniUsuario;
    private String mailUsuario;
    private String telefonoUsuario;
    private List<ConsultaItemResponse> eleccionDeProyectoList;

    public static InformacionProyectoResponse from(InfoProyectoDto proyectoDto) {
        return InformacionProyectoResponse
                .builder()
                .id(proyectoDto.getId())
                .titulo(proyectoDto.getTitulo())
                .descripcion(proyectoDto.getDescripcion())
                .archivoUrl(proyectoDto.getArchivoUrl())
                .nombreUsuario(proyectoDto.getNombreUsuario())
                .apellidoUsuario(proyectoDto.getApellidoUsuario())
                .dniUsuario(proyectoDto.getDniUsuario())
                .mailUsuario(proyectoDto.getMailUsuario())
                .telefonoUsuario(proyectoDto.getTelefonoUsuario())
                .eleccionDeProyectoList(proyectoDto.getConsultaDeProyectoDtoList() != null ?
                        proyectoDto.getConsultaDeProyectoDtoList().stream().map(ConsultaItemResponse::from).toList()
                        : null)
                .build();
    }
}
