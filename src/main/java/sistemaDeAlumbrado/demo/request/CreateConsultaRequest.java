package sistemaDeAlumbrado.demo.request;

import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class CreateConsultaRequest {
    private String titulo;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalTime horaInicio;
    private LocalDate fechaCierre;
    private LocalTime horaCierre;
    private Boolean mostrarRecuento;
    private List<Long> proyectosId;
}
