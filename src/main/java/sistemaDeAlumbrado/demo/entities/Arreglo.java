package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@Table(name = "arreglo")
@Data
public class Arreglo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tipo_reclamo_id", nullable = false)
    private TipoReclamo tipoReclamo;

    @ManyToOne
    @JoinColumn(name = "barrio_id")
    private Barrio barrio;

    @Column(name = "latitud_reclamo")
    private Double latitudReclamo;

    @Column(name = "longitud_reclamo")
    private Double longitudReclamo;

    @ManyToOne
    @JoinColumn(name = "tecnico1_id", nullable = false)
    private UsuarioEntity tecnico1;

    @ManyToOne
    @JoinColumn(name = "tecnico2_id")
    private UsuarioEntity tecnico2;

    @Column(name = "fecha_arreglo", nullable = false)
    private LocalDate fechaArreglo;

    @Column(name = "hora_arreglo", nullable = false)
    private Time horaArreglo;

    @Column(name = "observacion_arreglo", length = 500)
    private String observacionArreglo;

    @Column(name = "foto_arreglo", length = 500)
    private String fotoArreglo;

    @ManyToOne
    @JoinColumn(name = "poste_id")
    private Poste poste;

    public Arreglo(){super();}

    public Arreglo(TipoReclamo tipoReclamo, Double latitudReclamo, Double longitudReclamo, UsuarioEntity tecnico1,
                   UsuarioEntity tecnico2, LocalDate fechaArreglo, Time horaArreglo, String observacionArreglo,
                   String fotoArreglo, Barrio barrio, Poste poste){
        super();
        this.tipoReclamo = tipoReclamo;
        this.latitudReclamo = latitudReclamo;
        this.longitudReclamo = longitudReclamo;
        this.tecnico1 = tecnico1;
        this.tecnico2 = tecnico2;
        this.fechaArreglo = fechaArreglo;
        this.horaArreglo = horaArreglo;
        this.observacionArreglo = observacionArreglo;
        this.fotoArreglo = fotoArreglo;
        this.barrio = barrio;
        this.poste = poste;
    }
}
