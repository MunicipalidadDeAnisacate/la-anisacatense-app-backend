package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "zona")
@Data
public class Zona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;
}
