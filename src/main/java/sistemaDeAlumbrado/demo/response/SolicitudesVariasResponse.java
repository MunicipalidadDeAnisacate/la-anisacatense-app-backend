package sistemaDeAlumbrado.demo.response;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.SolicitudesVariasDto;
import sistemaDeAlumbrado.demo.dtos.VecinoDto;

import java.util.Set;

@Builder
@Data
public class SolicitudesVariasResponse {
    private Long id;
    private String nombreSubTipoReclamo;
    private String nombreTipoReclamo;
    private Integer nroSubTipoReclamo;
    private Double latitudReclamo;
    private Double longitudReclamo;
    private String nombreEstado;
    private Set<VecinoDto> vecinoDtoList;

    public static SolicitudesVariasResponse from(SolicitudesVariasDto dto){
        return SolicitudesVariasResponse
                .builder()
                .id(dto.getId())
                .nombreSubTipoReclamo(dto.getNombreSubTipoReclamo())
                .nroSubTipoReclamo(dto.getNroSubTipoReclamo())
                .nombreTipoReclamo(dto.getNombreTipoReclamo())
                .latitudReclamo(dto.getLatitudReclamo())
                .longitudReclamo(dto.getLongitudReclamo())
                .nombreEstado(dto.getNombreEstado())
                .vecinoDtoList(dto.getVecinoDtoList())
                .build();
    }
}