package sistemaDeAlumbrado.demo.response;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.MisReclamosDto;

import java.sql.Time;
import java.time.LocalDate;

@Data
@Builder
public class MisReclamosResponse {
    private Long id;
    private String nombrePoste;
    private String nombreTipoReclamo;
    private String nombreSubTipoReclamo;
    private LocalDate fechaReclamo;
    private String nombreAnimal;
    private Time horaReclamo;
    private LocalDate fechaArreglo;
    private Time horaArreglo;
    private String nombreEstadoReclamo;
    private Double latitud;
    private Double longitud;
    private String nombreBarrio;

    public static MisReclamosResponse from(MisReclamosDto misReclamosDto) {
        return MisReclamosResponse.builder()
                .id(misReclamosDto.getId())
                .nombrePoste(misReclamosDto.getNombrePoste() == null ? null : misReclamosDto.getNombrePoste())
                .nombreTipoReclamo(misReclamosDto.getNombreTipoReclamo())
                .nombreSubTipoReclamo(misReclamosDto.getNombreSubTipoReclamo())
                .fechaReclamo(misReclamosDto.getFechaReclamo())
                .horaReclamo(misReclamosDto.getHoraReclamo())
                .fechaArreglo(misReclamosDto.getFechaArreglo())
                .nombreAnimal(misReclamosDto.getNombreAnimal() != null ? misReclamosDto.getNombreAnimal() : null)
                .horaArreglo(misReclamosDto.getHoraArreglo())
                .nombreEstadoReclamo(misReclamosDto.getNombreEstadoReclamo())
                .latitud(misReclamosDto.getLatitud())
                .longitud(misReclamosDto.getLongitud())
                .nombreBarrio(misReclamosDto.getNombreBarrio() == null ? null : misReclamosDto.getNombreBarrio())
                .build();
    }
}
