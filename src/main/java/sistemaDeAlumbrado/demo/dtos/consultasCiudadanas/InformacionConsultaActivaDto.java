package sistemaDeAlumbrado.demo.dtos.consultasCiudadanas;

import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.proyectos.ProyectoEnConsultaDto;
import sistemaDeAlumbrado.demo.entities.ConsultaCiudadana;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class InformacionConsultaActivaDto {
    private Long id;
    private Long proyectoIdVotado;
    private String titulo;
    private String descripcion;
    private Boolean mostrarRecuento;
    private LocalDate fechaInicio;
    private Time horaInicio;
    private LocalDate fechaCierre;
    private Time horaCierre;
    private List<ProyectoEnConsultaDto> proyectos;

    public InformacionConsultaActivaDto(ConsultaCiudadana consultaCiudadana) {
        this.id = consultaCiudadana.getId();
        this.titulo = consultaCiudadana.getTitulo();
        this.descripcion = consultaCiudadana.getDescripcion();
        this.mostrarRecuento = consultaCiudadana.getMostrarRecuento();
        this.fechaInicio = consultaCiudadana.getFechaInicio();
        this.horaInicio = consultaCiudadana.getHoraInicio();
        this.fechaCierre = consultaCiudadana.getFechaCierre();
        this.horaCierre = consultaCiudadana.getHoraCierre();
        this.proyectos = new ArrayList<>();
    }

    public void addProyectos(List<ProyectoEnConsultaDto> proyectos) {
        this.proyectos.addAll(proyectos);
    }
}
