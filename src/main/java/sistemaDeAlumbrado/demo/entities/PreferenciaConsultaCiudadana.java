package sistemaDeAlumbrado.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@Table(name = "preferencia_consulta_ciudadana",
        uniqueConstraints = @UniqueConstraint(columnNames = {"consulta_id", "usuario_id"}))
@Data
public class PreferenciaConsultaCiudadana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "consulta_id", nullable = false)
    private ConsultaCiudadana consultaCiudadana;

    @ManyToOne(optional = false)
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @Column(name = "fecha_voto", nullable = false)
    private LocalDate fechaPrefrencia;

    @Column(name = "hora_voto", nullable = false)
    private Time horaPreferencia;
}
