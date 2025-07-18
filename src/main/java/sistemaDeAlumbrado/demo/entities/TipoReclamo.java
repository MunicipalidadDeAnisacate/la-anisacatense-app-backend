package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tipo_reclamo")
public class TipoReclamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo")
    private Integer idTipo;

    @Column(name = "nombre_tipo", nullable = false, unique = true)
    private String nombreTipo;
}
