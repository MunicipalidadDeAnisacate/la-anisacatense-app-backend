package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "cuadrilla")
@Data
public class Cuadrilla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;
}
