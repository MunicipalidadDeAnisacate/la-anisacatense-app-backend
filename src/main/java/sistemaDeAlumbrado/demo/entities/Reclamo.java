package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@Table(name = "reclamo")
@Data
public class Reclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sub_tipo_reclamo_id", nullable = false)
    private SubTipoReclamo subTipoReclamo;

    @ManyToOne
    @JoinColumn(name = "estado_reclamo_id", nullable = false)
    private EstadoReclamo estadoReclamo;

    @ManyToOne
    @JoinColumn(name = "barrio_id")
    private Barrio barrio;

    @Column(name = "latitud_reclamo")
    private Double latitudReclamo;

    @Column(name = "longitud_reclamo")
    private Double longitudReclamo;

    @ManyToOne
    @JoinColumn(name = "poste_id")
    private Poste posteLuz;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "tecnico1_id")
    private UsuarioEntity tecnico1;

    @ManyToOne
    @JoinColumn(name = "tecnico2_id")
    private UsuarioEntity tecnico2;

    @Column(name = "fecha_arreglo")
    private LocalDate fechaArreglo;

    @Column(name = "hora_arreglo")
    private Time horaArreglo;

    @Column(name = "observacion_reclamo", length = 500)
    private String observacionReclamo;

    @Column(name = "foto_reclamo", length = 500)
    private String fotoReclamo;

    @Column(name = "observacion_arreglo", length = 500)
    private String observacionArreglo;

    @Column(name = "foto_arreglo", length = 500)
    private String fotoArreglo;

    public Reclamo(){super();}

    public Reclamo(SubTipoReclamo subTipoReclamo, EstadoReclamo estadoReclamo,
                   Barrio barrio, Double latitudReclamo, Double longitudReclamo,
                   Poste posteLuz, Animal animal, String observacionReclamo,
                   String fotoReclamo){
        super();
        this.posteLuz = posteLuz;
        this.animal = animal;
        this.latitudReclamo = latitudReclamo;
        this.longitudReclamo = longitudReclamo;
        this.barrio = barrio;
        this.fotoReclamo = fotoReclamo;
        this.estadoReclamo = estadoReclamo;
        this.subTipoReclamo = subTipoReclamo;
        this.observacionReclamo = observacionReclamo;
    }

    public void finalizar(UsuarioEntity tecnico1, UsuarioEntity tecnico2, LocalDate fechaArreglo, Time horaArreglo,
                          EstadoReclamo estadoReclamo, String observacionArreglo, String fotoArreglo){
        this.tecnico1 = tecnico1;
        this.tecnico2 = tecnico2;
        this.fechaArreglo = fechaArreglo;
        this.horaArreglo = horaArreglo;
        this.estadoReclamo = estadoReclamo;
        this.observacionArreglo = observacionArreglo;
        this.fotoArreglo = fotoArreglo;
    }
}

