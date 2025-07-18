package sistemaDeAlumbrado.demo.dtos;

import lombok.Data;
import sistemaDeAlumbrado.demo.entities.Reclamo;
import sistemaDeAlumbrado.demo.entities.TipoReparacion;

import java.sql.Time;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class InformacionReclamoCompletaDto {
    private Long reclamoId;
    private String nombreSubTipoReclamo;
    private Integer nroSubTipoReclamo;
    private String nombreEstado;
    private String nombreTipoReclamo;
    private Double latitudReclamo;
    private Double longitudReclamo;
    private String nombrePoste;
    private String observacionReclamo;
    private String fotoReclamo;
    private String nombreBarrio;
    private Set<VecinoDto> vecinoDtoSet;
    private String observacionArreglo;
    private String fotoArreglo;
    private String nombreAnimal;
    private String nombreTecnico1;
    private String apellidoTecnico1;
    private String nombreTecnico2;
    private String apellidoTecnico2;
    private Time horaArreglo;
    private LocalDate fechaArreglo;
    private List<String> arreglosPoste;


    public InformacionReclamoCompletaDto(Reclamo reclamo, Set<VecinoDto> vecinoDtoSet, List<TipoReparacion> tipoReparaciones) {
        this.reclamoId = reclamo.getId();
        this.nombreSubTipoReclamo = reclamo.getSubTipoReclamo().getNombreSubTipo();
        this.nroSubTipoReclamo = reclamo.getSubTipoReclamo().getIdSubTipo();
        this.nombreEstado = reclamo.getEstadoReclamo().getNombreEstado();
        this.nombreTipoReclamo = reclamo.getSubTipoReclamo().getTipoReclamo().getNombreTipo();
        this.nombrePoste = reclamo.getPosteLuz() != null ? reclamo.getPosteLuz().getNombrePoste() : null;

        String barrioByPoste = reclamo.getPosteLuz() != null ? reclamo.getPosteLuz().getBarrio().getNombre() : null;
        String barrioByReclamo = reclamo.getBarrio() != null ? reclamo.getBarrio().getNombre() : null;
        String barrioByVecino = !vecinoDtoSet.isEmpty() ?
                (vecinoDtoSet.stream().map(VecinoDto::getNombreBarrio).findFirst()).get() : null;
        this.nombreBarrio = barrioByPoste != null ? barrioByPoste :
                ( barrioByReclamo != null ? barrioByReclamo : barrioByVecino );

        this.nombreTecnico1 = reclamo.getTecnico1() != null ? reclamo.getTecnico1().getNombre() : null;
        this.apellidoTecnico1 = reclamo.getTecnico1() != null ? reclamo.getTecnico1().getApellido() : null;
        this.nombreTecnico2 = reclamo.getTecnico2() != null ? reclamo.getTecnico2().getNombre() : null;
        this.apellidoTecnico2 = reclamo.getTecnico2() != null ? reclamo.getTecnico2().getApellido() : null;
        this.horaArreglo = reclamo.getHoraArreglo() != null ? reclamo.getHoraArreglo() : null;
        this.fechaArreglo = reclamo.getFechaArreglo() != null ? reclamo.getFechaArreglo() : null;
        this.observacionArreglo = reclamo.getObservacionArreglo() != null ? reclamo.getObservacionArreglo() : null;
        this.fotoArreglo = reclamo.getFotoArreglo() != null ? reclamo.getFotoArreglo() : null;
        this.latitudReclamo = reclamo.getLatitudReclamo() != null ? reclamo.getLatitudReclamo() : null;
        this.longitudReclamo = reclamo.getLongitudReclamo() != null ? reclamo.getLongitudReclamo() : null;
        this.nombreAnimal = reclamo.getAnimal() != null ? reclamo.getAnimal().getNombre() : null;
        this.observacionReclamo = reclamo.getObservacionReclamo() != null ? reclamo.getObservacionReclamo() : null;
        this.fotoReclamo = reclamo.getFotoReclamo() != null ? reclamo.getFotoReclamo() : null;
        this.vecinoDtoSet = vecinoDtoSet;
        this.arreglosPoste = tipoReparaciones.stream().map(TipoReparacion::getNombreTipo).toList();
    }
}
