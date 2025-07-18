package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "animal")
@Data
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    public Animal(){super();}
}
