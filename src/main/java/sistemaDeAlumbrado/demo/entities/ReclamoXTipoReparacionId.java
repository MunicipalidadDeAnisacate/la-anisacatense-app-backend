package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Embeddable
@Data
public class ReclamoXTipoReparacionId {
    @ManyToOne
    @JoinColumn(name = "id_reclamo")
    private Reclamo reclamo;

    @ManyToOne
    @JoinColumn(name = "id_tipo_reparacion")
    private TipoReparacion tipoReparacion;

    public ReclamoXTipoReparacionId(){super();}

    public ReclamoXTipoReparacionId(Reclamo reclamoP, TipoReparacion tipoReparacionP){
        super();
        reclamo = reclamoP;
        tipoReparacion = tipoReparacionP;
    }
}
