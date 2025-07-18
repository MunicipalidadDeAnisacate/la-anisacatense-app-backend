package sistemaDeAlumbrado.demo.request;

import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;

@Data
public class PreferenciaRequest {
    private Long consultaId;
    private Long proyectoId;
    private Long usuarioId;
    private LocalDate fecha;
    private Time hora;
}
