package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ConsultaXProyectoId implements Serializable {
    private Long consultaId;
    private Long proyectoId;

    public ConsultaXProyectoId() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConsultaXProyectoId)) return false;
        ConsultaXProyectoId that = (ConsultaXProyectoId) o;
        return consultaId.equals(that.consultaId) && proyectoId.equals(that.proyectoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consultaId, proyectoId);
    }
}

