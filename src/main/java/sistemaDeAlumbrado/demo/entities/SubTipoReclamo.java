package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "sub_tipo_reclamo")
public class SubTipoReclamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sub_tipo")
    private Integer idSubTipo;

    @Column(name = "nombre_sub_tipo", nullable = false)
    private String nombreSubTipo;

    @ManyToOne
    @JoinColumn(name = "tipo_reclamo_id")
    private TipoReclamo tipoReclamo;
}
