package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "consulta_ciudadana")
@Getter
@Setter
public class ConsultaCiudadana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(length = 1000)
    private String descripcion;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "hora_inicio")
    private Time horaInicio;

    @Column(name = "fecha_cierre")
    private LocalDate fechaCierre;

    @Column(name = "hora_cierre")
    private Time horaCierre;

    @Column(name = "mostrar_recuento", nullable = false)
    private Boolean mostrarRecuento;

    @OneToMany(mappedBy = "consultaCiudadana", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ConsultaXProyecto> proyectos = new HashSet<>();

    @OneToMany(mappedBy = "consultaCiudadana", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PreferenciaConsultaCiudadana> preferenciaConsultaCiudadanas = new HashSet<>();

    public ConsultaCiudadana(){super();}

    public ConsultaCiudadana(String titulo,
                             String descripcion,
                             LocalDate fechaInicio,
                             Time horaInicio,
                             LocalDate fechaCierre,
                             Time horaCierre,
                             Boolean mostrarRecuento){
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.horaInicio = horaInicio;
        this.fechaCierre = fechaCierre;
        this.horaCierre = horaCierre;
        this.mostrarRecuento = mostrarRecuento;
    }

    public void addProyecto(ConsultaXProyecto enlace) {
        this.proyectos.add(enlace);
        enlace.setConsultaCiudadana(this);
    }

    public LocalDateTime getFechaHoraInicio(){
        return LocalDateTime.of(this.getFechaInicio(), this.getHoraInicio().toLocalTime());
    }

    public LocalDateTime getFechaHoraCierre(){
        return LocalDateTime.of(this.getFechaCierre(), this.getHoraCierre().toLocalTime());
    }
}