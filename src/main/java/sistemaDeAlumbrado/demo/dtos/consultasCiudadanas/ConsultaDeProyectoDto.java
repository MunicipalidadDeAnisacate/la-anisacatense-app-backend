package sistemaDeAlumbrado.demo.dtos.consultasCiudadanas;

import lombok.Data;
import sistemaDeAlumbrado.demo.entities.ConsultaCiudadana;
import sistemaDeAlumbrado.demo.entities.ConsultaXProyecto;

import java.sql.Time;
import java.time.LocalDate;

@Data
public class ConsultaDeProyectoDto {
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaInicio;
    private Time horaInicio;
    private LocalDate fechaCierre;
    private Time horaCierre;

    public ConsultaDeProyectoDto(ConsultaXProyecto consultaXProyecto){
        ConsultaCiudadana consultaCiudadana = consultaXProyecto.getConsultaCiudadana();

        this.id = consultaCiudadana.getId();
        this.titulo = consultaCiudadana.getTitulo();
        this.descripcion = consultaCiudadana.getDescripcion();
        this.fechaInicio = consultaCiudadana.getFechaInicio();
        this.horaInicio = consultaCiudadana.getHoraInicio();
        this.fechaCierre = consultaCiudadana.getFechaCierre();
        this.horaCierre = consultaCiudadana.getHoraCierre();
    }
}
