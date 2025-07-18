package sistemaDeAlumbrado.demo.dtos;

import lombok.Data;
import sistemaDeAlumbrado.demo.entities.ReclamoXUsuario;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

@Data
public class InfoReclamoLuminariaUsuarioDto {
    private String nombreUsuario;
    private String apellidoUsuario;
    private LocalDate fechaReclamo;
    private Time horaReclamo;

    public InfoReclamoLuminariaUsuarioDto(ReclamoXUsuario reclamoXUsuario) {
        this.nombreUsuario = reclamoXUsuario.getReclamoXUsuarioId().getUsuario().getNombre();
        this.apellidoUsuario = reclamoXUsuario.getReclamoXUsuarioId().getUsuario().getApellido();
        this.fechaReclamo = reclamoXUsuario.getFechaReclamo();
        this.horaReclamo = reclamoXUsuario.getHoraReclamo();
    }

    public InfoReclamoLuminariaUsuarioDto(Object[] rows) {
        this.nombreUsuario = (String) rows[1];
        this.apellidoUsuario = (String) rows[2];
        this.fechaReclamo = ((Date) rows[3]).toLocalDate();
        this.horaReclamo = ((Time) rows[4]);
    }
}
