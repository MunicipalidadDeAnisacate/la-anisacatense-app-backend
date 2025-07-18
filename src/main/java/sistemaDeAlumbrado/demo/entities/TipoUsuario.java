package sistemaDeAlumbrado.demo.entities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tipo_usuario")
@Data
public class TipoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_tipo", nullable = false)
    private String nombreTipo;

    public TipoUsuario(){super();}
}

