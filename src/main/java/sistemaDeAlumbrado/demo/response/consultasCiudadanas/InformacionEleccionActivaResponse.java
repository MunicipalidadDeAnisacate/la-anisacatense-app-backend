package sistemaDeAlumbrado.demo.response.consultasCiudadanas;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.consultasCiudadanas.InformacionConsultaActivaDto;
import sistemaDeAlumbrado.demo.response.localsFormatter.LocalsFormatter;
import sistemaDeAlumbrado.demo.response.proyectos.ProyectoEnEleccionResponse;

import java.util.List;

@Data
@Builder
public class InformacionEleccionActivaResponse {
    private Long id;
    private String titulo;
    private Long proyectoIdVotado;
    private String descripcion;
    private Boolean mostrarRecuento;
    private String fechaInicio;
    private String horaInicio;
    private String fechaCierre;
    private String horaCierre;
    private List<ProyectoEnEleccionResponse> proyectos;

    public static InformacionEleccionActivaResponse from(InformacionConsultaActivaDto dto){
        return InformacionEleccionActivaResponse
                .builder()
                .id(dto.getId())
                .titulo(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .proyectoIdVotado(dto.getProyectoIdVotado() != null ? dto.getProyectoIdVotado() : null)
                .mostrarRecuento(dto.getMostrarRecuento())
                .fechaInicio(LocalsFormatter.formatearFecha(dto.getFechaInicio()))
                .horaInicio(LocalsFormatter.formatearHora(dto.getHoraInicio()))
                .fechaCierre(LocalsFormatter.formatearFecha(dto.getFechaCierre()))
                .horaCierre(LocalsFormatter.formatearHora(dto.getHoraCierre()))
                .proyectos(dto.getProyectos().stream().map(ProyectoEnEleccionResponse::from).toList())
                .build();
    }
}
