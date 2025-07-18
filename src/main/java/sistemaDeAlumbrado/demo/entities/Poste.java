package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "postes_luz")
public class Poste {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPoste;

    @Column(name = "nombre")
    private String nombrePoste;

    @Column(name = "latitud", nullable = false)
    private Double latitude;

    @Column(name = "longitud", nullable = false)
    private Double longitude;

    @Column(name = "fecha_carga")
    private LocalDate fechaCarga;

    @ManyToOne
    @JoinColumn(name = "barrio_id")
    private Barrio barrio;

    public Poste() {super();}

    public Poste(String nombrePoste, Barrio barrio, Double latitud, Double longitud,
                 LocalDate fechaCarga) {
        super();
        this.nombrePoste = nombrePoste;
        this.barrio = barrio;
        this.latitude = latitud;
        this.longitude = longitud;
        this.fechaCarga = fechaCarga;
    }
}