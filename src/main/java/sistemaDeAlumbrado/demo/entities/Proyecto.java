package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "proyecto")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(length = 1000)
    private String descripcion;

    @Column(name = "archivo_url", length = 1000)
    private String objectName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ConsultaXProyecto> elecciones = new HashSet<>();

    public Proyecto(){super();}

    public Proyecto(String titulo, String descripcion, String objectName, UsuarioEntity usuario){
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.objectName = objectName;
        this.usuario = usuario;
    }
}
