package sistemaDeAlumbrado.demo.response;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.InformacionReclamoCompletaDto;
import sistemaDeAlumbrado.demo.dtos.VecinoDto;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class InformacionReclamoResponse {
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
    private Set<VecinoDto> vecinos;
    private String nombreBarrio;
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

    public static InformacionReclamoResponse from(InformacionReclamoCompletaDto dto) {
        return InformacionReclamoResponse
                .builder()
                .reclamoId(dto.getReclamoId())
                .nombreSubTipoReclamo(dto.getNombreSubTipoReclamo())
                .nroSubTipoReclamo(dto.getNroSubTipoReclamo())
                .nombreEstado(dto.getNombreEstado())
                .nombreTipoReclamo(dto.getNombreTipoReclamo())
                .latitudReclamo(dto.getLatitudReclamo())
                .longitudReclamo(dto.getLongitudReclamo())
                .nombrePoste(dto.getNombrePoste())
                .observacionReclamo(dto.getObservacionReclamo())
                .fotoReclamo(dto.getFotoReclamo())
                .vecinos(dto.getVecinoDtoSet())
                .nombreBarrio(dto.getNombreBarrio())
                .observacionArreglo(dto.getObservacionArreglo())
                .fotoArreglo(dto.getFotoArreglo())
                .nombreAnimal(dto.getNombreAnimal())
                .nombreTecnico1(dto.getNombreTecnico1())
                .nombreTecnico2(dto.getNombreTecnico2() != null ? dto.getNombreTecnico2() : null)
                .horaArreglo(dto.getHoraArreglo())
                .fechaArreglo(dto.getFechaArreglo())
                .apellidoTecnico1(dto.getApellidoTecnico1())
                .apellidoTecnico2(dto.getApellidoTecnico2() != null ? dto.getApellidoTecnico2() : null)
                .arreglosPoste(dto.getArreglosPoste() != null ? dto.getArreglosPoste() : null)
                .build();
    }
}
