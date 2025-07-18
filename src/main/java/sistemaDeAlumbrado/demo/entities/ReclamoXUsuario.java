package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "reclamo_x_usuario")
public class ReclamoXUsuario {
    @EmbeddedId
    private ReclamoXUsuarioId reclamoXUsuarioId;

    @Column(name = "fecha_reclamo", nullable = false)
    private LocalDate fechaReclamo;

    @Column(name = "hora_reclamo", nullable = false)
    private Time horaReclamo;

    public ReclamoXUsuario() {super();}

    public ReclamoXUsuario(Reclamo reclamo, UsuarioEntity usuario, LocalDate fechaReclamo, Time horaReclamo) {
        super();
        this.reclamoXUsuarioId = new ReclamoXUsuarioId(reclamo, usuario);
        this.fechaReclamo = fechaReclamo;
        this.horaReclamo = horaReclamo;
    }
}
