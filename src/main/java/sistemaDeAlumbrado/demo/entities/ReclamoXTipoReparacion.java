package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "reclamo_x_tipo_reparacion")
@Data
public class ReclamoXTipoReparacion {
    @EmbeddedId
    private ReclamoXTipoReparacionId reclamoXTipoReparacionId;

    public ReclamoXTipoReparacion(){super();}

    public ReclamoXTipoReparacion(Reclamo reclamoP, TipoReparacion tipoReparacionP) {
        super();
        reclamoXTipoReparacionId = new ReclamoXTipoReparacionId(reclamoP, tipoReparacionP);
    }
}
