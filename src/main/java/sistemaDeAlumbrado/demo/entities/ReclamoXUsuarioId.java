package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class ReclamoXUsuarioId {
    @ManyToOne
    @JoinColumn(name = "id_reclamo")
    private Reclamo reclamo;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    public ReclamoXUsuarioId() {super();}

    public ReclamoXUsuarioId(Reclamo reclamo, UsuarioEntity usuario) {
        super();
        this.reclamo = reclamo;
        this.usuario = usuario;
    }
}
