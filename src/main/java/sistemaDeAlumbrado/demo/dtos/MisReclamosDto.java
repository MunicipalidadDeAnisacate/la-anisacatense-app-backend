package sistemaDeAlumbrado.demo.dtos;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.entities.Reclamo;
import sistemaDeAlumbrado.demo.entities.ReclamoXUsuario;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class MisReclamosDto {
    private Long id;
    private String nombreTipoReclamo;
    private String nombrePoste;
    private String nombreSubTipoReclamo;
    private String nombreAnimal;
    private LocalDate fechaArreglo;
    private Time horaArreglo;
    private String nombreEstadoReclamo;
    private Double latitud;
    private Double longitud;
    private String nombreBarrio;
    private LocalDate fechaReclamo;
    private Time horaReclamo;


    public MisReclamosDto(Long id,
                          String nombrePoste,
                          String nombreTipoReclamo,
                          String nombreSubTipoReclamo,
                          String nombreAnimal,
                          LocalDate fechaArreglo,
                          Time horaArreglo,
                          String nombreEstadoReclamo,
                          Double latitud,
                          Double longitud,
                          String nombreBarrio,
                          LocalDate fechaReclamo,
                          Time horaReclamo){
        this.id = id;
        this.nombrePoste = nombrePoste;
        this.nombreTipoReclamo = nombreTipoReclamo;
        this.nombreSubTipoReclamo = nombreSubTipoReclamo;
        this.nombreAnimal = nombreAnimal;
        this.fechaArreglo = fechaArreglo;
        this.horaArreglo = horaArreglo;
        this.nombreEstadoReclamo = nombreEstadoReclamo;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombreBarrio = nombreBarrio;
        this.fechaReclamo = fechaReclamo;
        this.horaReclamo = horaReclamo;
    }
}
