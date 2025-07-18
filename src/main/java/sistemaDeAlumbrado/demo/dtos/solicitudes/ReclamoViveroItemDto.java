package sistemaDeAlumbrado.demo.dtos.solicitudes;


import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;

@Data
public class ReclamoViveroItemDto {
    private Long id;
    private String nombreSubTipoReclamo;
    private String nombreTipoReclamo;
    private String nombreEstado;
    private String nombreTecnico1;
    private String apellidoTecnico1;
    private String nombreTecnico2;
    private String apellidoTecnico2;
    private LocalDate fechaArreglo;
    private Time horaArreglo;
    private String nombreVecino;
    private String apellidoVecino;
    private String dniVecino;
    private LocalDate fechaPrimerReclamo;
    private Time horaPrimerReclamo;

    public ReclamoViveroItemDto(Long id, String nombreSubTipoReclamo, String nombreTipoReclamo, String nombreEstado,
                                String nombreTecnico1, String apellidoTecnico1, String nombreTecnico2, String apellidoTecnico2,
                                LocalDate fechaArreglo, Time horaArreglo, LocalDate fechaPrimerReclamo, Time horaPrimerReclamo,
                                String nombreVecino, String apellidoVecino, String dniVecino) {
        this.id = id;
        this.fechaArreglo = fechaArreglo;
        this.horaArreglo = horaArreglo;
        this.fechaPrimerReclamo = fechaPrimerReclamo;
        this.horaPrimerReclamo = horaPrimerReclamo;
        this.nombreEstado = nombreEstado;
        this.nombreSubTipoReclamo = nombreSubTipoReclamo;
        this.nombreTipoReclamo = nombreTipoReclamo;
        this.nombreTecnico1 = nombreTecnico1;
        this.apellidoTecnico1 = apellidoTecnico1;
        this.nombreTecnico2 = nombreTecnico2;
        this.apellidoTecnico2 = apellidoTecnico2;
        this.nombreVecino = nombreVecino;
        this.apellidoVecino = apellidoVecino;
        this.dniVecino = dniVecino;
    }
}
