package sistemaDeAlumbrado.demo.response.reparaciones;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.arreglos.InformacionArregloDto;
import sistemaDeAlumbrado.demo.response.localsFormatter.LocalsFormatter;


@Data
@Builder
public class InformacionReparacionResponse {
    private Long id;
    private String nombreTipoReclamo;
    private String nombreBarrio;
    private String nombreTecnico1;
    private String apellidoTecnico1;
    private String nombreTecnico2;
    private String apellidoTecnico2;
    private String fechaArreglo;
    private String horaArreglo;
    private String observacionArreglo;
    private String fotoArreglo;
    private String nombrePoste;

    public static InformacionReparacionResponse from(InformacionArregloDto dto){
        return InformacionReparacionResponse
                .builder()
                .id(dto.getId())
                .nombreTipoReclamo(dto.getNombreTipoReclamo())
                .nombreBarrio(dto.getNombreBarrio())
                .nombreTecnico1(dto.getNombreTecnico1())
                .apellidoTecnico1(dto.getApellidoTecnico1())
                .nombreTecnico2(dto.getNombreTecnico2())
                .apellidoTecnico2(dto.getApellidoTecnico2())
                .fechaArreglo(LocalsFormatter.formatearFecha(dto.getFechaArreglo()))
                .horaArreglo(LocalsFormatter.formatearHora(dto.getHoraArreglo()))
                .observacionArreglo(dto.getObservacionArreglo())
                .fotoArreglo(dto.getFotoArreglo())
                .nombrePoste(dto.getNombrePoste())
                .build();
    }
}
