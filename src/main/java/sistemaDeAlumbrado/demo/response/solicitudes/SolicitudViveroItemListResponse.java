package sistemaDeAlumbrado.demo.response.solicitudes;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.solicitudes.ReclamoViveroItemDto;

import java.sql.Time;
import java.time.LocalDate;

@Data
@Builder
public class SolicitudViveroItemListResponse {
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

    public static SolicitudViveroItemListResponse from(ReclamoViveroItemDto dto){
        return SolicitudViveroItemListResponse
                .builder()
                .id(dto.getId())
                .nombreSubTipoReclamo(dto.getNombreSubTipoReclamo())
                .nombreTipoReclamo(dto.getNombreTipoReclamo())
                .nombreEstado(dto.getNombreEstado())
                .nombreVecino(dto.getNombreVecino())
                .apellidoVecino(dto.getApellidoVecino())
                .dniVecino(dto.getDniVecino())
                .nombreTecnico1(dto.getNombreTecnico1() != null ? dto.getNombreTecnico1() : null)
                .apellidoTecnico1(dto.getApellidoTecnico1() != null ? dto.getApellidoTecnico1() : null)
                .nombreTecnico2(dto.getNombreTecnico2() != null ? dto.getNombreTecnico2() : null)
                .apellidoTecnico2(dto.getApellidoTecnico2() != null ? dto.getApellidoTecnico2() : null)
                .fechaArreglo(dto.getFechaArreglo() != null ? dto.getFechaArreglo() : null)
                .horaArreglo(dto.getHoraArreglo() != null ? dto.getHoraArreglo() : null)
                .horaPrimerReclamo(dto.getHoraPrimerReclamo())
                .fechaPrimerReclamo(dto.getFechaPrimerReclamo())
                .build();
    }
}
