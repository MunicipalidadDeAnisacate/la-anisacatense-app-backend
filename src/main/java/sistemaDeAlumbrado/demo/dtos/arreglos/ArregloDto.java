package sistemaDeAlumbrado.demo.dtos.arreglos;


import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;

@Data
public class ArregloDto {
    private Long id;
    private String nombrePoste;
    private String nombreTipoReclamo;
    private LocalDate fechaArreglo;
    private Time horaArreglo;
    private String nombreTecnico1;
    private String apellidoTecnico1;
    private String nombreTecnico2;
    private String apellidoTecnico2;
    private Double latitud;
    private Double longitud;
    private String nombreBarrio;

    public ArregloDto(Long id,
            String nombrePoste,
            String nombreTipoReclamo,
            LocalDate fechaArreglo,
            Time horaArreglo,
            String nombreTecnico1,
            String apellidoTecnico1,
            String nombreTecnico2,
            String apellidoTecnico2,
            Double latitud,
            Double longitud,
            String nombreBarrio){
        this.id = id;
        this.nombrePoste = nombrePoste;
        this.nombreTipoReclamo = nombreTipoReclamo;
        this.fechaArreglo = fechaArreglo;
        this.horaArreglo = horaArreglo;
        this.nombreTecnico1 = nombreTecnico1;
        this.apellidoTecnico1 = apellidoTecnico1;
        this.nombreTecnico2 = nombreTecnico2;
        this.apellidoTecnico2 = apellidoTecnico2;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombreBarrio = nombreBarrio;
    }
}
