package sistemaDeAlumbrado.demo.response.solicitudes;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.ReclamoItemListDto;
import sistemaDeAlumbrado.demo.response.localsFormatter.LocalsFormatter;


@Builder
@Data
public class SolicitudItemListResponse {
    private Long id; // obl
    private String nombreSubTipoReclamo;
    private String nombreTipoReclamo; // obl
    private String nombreEstado; // obl
    private String nombreBarrio;
    private String nombrePoste;
    private String nombreAnimal;
    private String nombreTecnico1;
    private String apellidoTecnico1;
    private String nombreTecnico2;
    private String apellidoTecnico2;
    private String fechaArreglo;
    private String horaArreglo;
    private String fechaPrimerReclamo; // obl
    private String horaPrimerReclamo; // obl
    private String nombreVecino;
    private String apellidoVecino;

    public static SolicitudItemListResponse from(ReclamoItemListDto dto){
        return SolicitudItemListResponse
                .builder()
                .id(dto.getId())
                .nombreSubTipoReclamo(dto.getNombreSubTipoReclamo() != null ? dto.getNombreSubTipoReclamo() : null)
                .nombreTipoReclamo(dto.getNombreTipoReclamo())
                .nombreEstado(dto.getNombreEstado())
                .nombreBarrio(dto.getNombreBarrio() != null ? dto.getNombreBarrio() : null)
                .nombrePoste(dto.getNombrePoste() != null ? dto.getNombrePoste() : null)
                .nombreAnimal(dto.getNombreAnimal() != null ? dto.getNombreAnimal() : null)
                .nombreTecnico1(dto.getNombreTecnico1() != null ? dto.getNombreTecnico1() : null)
                .apellidoTecnico1(dto.getApellidoTecnico1() != null ? dto.getApellidoTecnico1() : null)
                .nombreTecnico2(dto.getNombreTecnico2() != null ? dto.getNombreTecnico2() : null)
                .apellidoTecnico2(dto.getApellidoTecnico2() != null ? dto.getApellidoTecnico2() : null)
                .fechaArreglo(dto.getFechaArreglo() != null ? LocalsFormatter.formatearFecha(dto.getFechaArreglo()) : null)
                .horaArreglo(dto.getHoraArreglo() != null ? LocalsFormatter.formatearHora(dto.getHoraArreglo()) : null)
                .fechaPrimerReclamo(dto.getFechaPrimerReclamo() != null ? LocalsFormatter.formatearFecha(dto.getFechaPrimerReclamo()) : null)
                .horaPrimerReclamo(dto.getHoraPrimerReclamo() != null ? LocalsFormatter.formatearHora(dto.getHoraPrimerReclamo()) : null)
                .nombreVecino(dto.getNombreVecino() != null ? dto.getNombreVecino() : null)
                .apellidoVecino(dto.getApellidoVecino() != null ? dto.getApellidoVecino() : null)
                .build();
    }
}
