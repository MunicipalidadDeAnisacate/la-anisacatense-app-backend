package sistemaDeAlumbrado.demo.dtos;

import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;

@Data
public class UsuarioDeReclamoDto {
    private String nombreVecino;
    private String apellidoVecino;
    private LocalDate fechaReclamo;
    private Time horaReclamo;

    public UsuarioDeReclamoDto(String nombreVecino, String apellidoVecino, LocalDate fechaReclamo, Time horaReclamo){
        this.nombreVecino = nombreVecino;
        this.apellidoVecino = apellidoVecino;
        this.fechaReclamo = fechaReclamo;
        this.horaReclamo = horaReclamo;
    }
}
