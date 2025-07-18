package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "consulta_x_proyecto")
@Getter
@Setter
public class ConsultaXProyecto {
    @EmbeddedId
    private ConsultaXProyectoId id = new ConsultaXProyectoId();

    @ManyToOne
    @MapsId("consultaId")
    @JoinColumn(name = "consulta_id")
    private ConsultaCiudadana consultaCiudadana;

    @ManyToOne
    @MapsId("proyectoId")
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;
}
