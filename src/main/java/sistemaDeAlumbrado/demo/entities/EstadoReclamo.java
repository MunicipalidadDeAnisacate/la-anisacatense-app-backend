package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "estado_reclamo")
@Data
public class EstadoReclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_estado", nullable = false)
    private String nombreEstado;
}

