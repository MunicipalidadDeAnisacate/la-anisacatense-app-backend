package sistemaDeAlumbrado.demo.dtos.arreglos;

import lombok.Data;
import sistemaDeAlumbrado.demo.entities.Arreglo;

import java.sql.Time;
import java.time.LocalDate;

@Data
public class InformacionArregloDto {
    private Long id;
    private String nombreTipoReclamo;
    private String nombreBarrio;
    private String nombreTecnico1;
    private String apellidoTecnico1;
    private String nombreTecnico2;
    private String apellidoTecnico2;
    private LocalDate fechaArreglo;
    private Time horaArreglo;
    private String observacionArreglo;
    private String fotoArreglo;
    private String nombrePoste;

    public InformacionArregloDto(Arreglo arreglo) {
        this.id = arreglo.getId();
        this.nombreTipoReclamo = arreglo.getTipoReclamo().getNombreTipo();
        this.nombreTecnico1 = arreglo.getTecnico1().getNombre();
        this.apellidoTecnico1 = arreglo.getTecnico1().getApellido();
        this.fechaArreglo = arreglo.getFechaArreglo();
        this.horaArreglo = arreglo.getHoraArreglo();
        this.observacionArreglo = arreglo.getObservacionArreglo();
        this.fotoArreglo = arreglo.getFotoArreglo() != null ? arreglo.getFotoArreglo() : null;
        this.nombrePoste = arreglo.getPoste() != null ? arreglo.getPoste().getNombrePoste() : null;
        this.nombreBarrio = arreglo.getPoste() != null ?
                arreglo.getPoste().getBarrio().getNombre() : arreglo.getBarrio().getNombre();
        if (arreglo.getTecnico2() != null){
            this.nombreTecnico2 = arreglo.getTecnico2().getNombre();
            this.apellidoTecnico2 = arreglo.getTecnico2().getApellido();
        }
    }
}
