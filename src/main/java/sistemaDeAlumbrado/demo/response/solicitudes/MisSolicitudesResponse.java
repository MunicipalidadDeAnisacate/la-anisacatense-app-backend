package sistemaDeAlumbrado.demo.response.solicitudes;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.MisReclamosDto;
import sistemaDeAlumbrado.demo.response.localsFormatter.LocalsFormatter;


@Data
@Builder
public class MisSolicitudesResponse {
    private Long id; // obl
    private String nombreTipoReclamo; // obl
    private String nombrePoste;
    private String nombreSubTipoReclamo; // obl
    private String nombreAnimal;
    private String fechaArreglo;
    private String horaArreglo;
    private String nombreEstadoReclamo; // obl
    private Double latitud;
    private Double longitud;
    private String nombreBarrio;
    private String fechaReclamo; // obl
    private String horaReclamo; // obl

    public static MisSolicitudesResponse from(MisReclamosDto dto){
        return MisSolicitudesResponse
                .builder()
                .id(dto.getId())
                .nombreTipoReclamo(dto.getNombreTipoReclamo())
                .nombreSubTipoReclamo(dto.getNombreSubTipoReclamo())
                .nombreEstadoReclamo(dto.getNombreEstadoReclamo())
                .fechaReclamo(LocalsFormatter.formatearFecha(dto.getFechaReclamo()))
                .horaReclamo(LocalsFormatter.formatearHora(dto.getHoraReclamo()))
                .nombrePoste(dto.getNombrePoste() != null ? dto.getNombrePoste() : null)
                .nombreAnimal(dto.getNombreAnimal() != null ? dto.getNombreAnimal() : null)
                .fechaArreglo(dto.getFechaArreglo() != null ? LocalsFormatter.formatearFecha(dto.getFechaArreglo()) : null)
                .horaArreglo(dto.getHoraArreglo() != null ? LocalsFormatter.formatearHora(dto.getHoraArreglo()) : null)
                .latitud(dto.getLatitud() != null ? dto.getLatitud() : null)
                .longitud(dto.getLongitud() != null ? dto.getLongitud() : null)
                .nombreBarrio(dto.getNombreBarrio() != null ? dto.getNombreBarrio() : null)
                .build();
    }
}