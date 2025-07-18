package sistemaDeAlumbrado.demo.response;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.ReclamoItemListDto;

import java.sql.Time;
import java.time.LocalDate;

@Data
@Builder
public class ReclamoItemListResponse {
    private Long id;
    private String nombreSubTipoReclamo;
    private String nombreTipoReclamo;
    private String nombreEstado;
    private String nombreBarrio;
    private String nombrePoste;
    private String nombreAnimal;
    private String nombreTecnico1;
    private String apellidoTecnico1;
    private String nombreTecnico2;
    private String apellidoTecnico2;
    private LocalDate fechaArreglo;
    private Time horaArreglo;
    private LocalDate fechaPrimerReclamo;
    private Time horaPrimerReclamo;

    public static ReclamoItemListResponse from(ReclamoItemListDto dto){
        return ReclamoItemListResponse
                .builder()
                .id(dto.getId())
                .nombreSubTipoReclamo(dto.getNombreSubTipoReclamo())
                .nombreTipoReclamo(dto.getNombreTipoReclamo())
                .nombreEstado(dto.getNombreEstado())
                .nombreBarrio(dto.getNombreBarrio())
                .nombrePoste(dto.getNombrePoste())
                .nombreAnimal(dto.getNombreAnimal())
                .nombreTecnico1(dto.getNombreTecnico1())
                .apellidoTecnico1(dto.getApellidoTecnico1())
                .nombreTecnico2(dto.getNombreTecnico2())
                .apellidoTecnico2(dto.getApellidoTecnico2())
                .fechaArreglo(dto.getFechaArreglo())
                .horaArreglo(dto.getHoraArreglo())
                .fechaPrimerReclamo(dto.getFechaPrimerReclamo())
                .horaPrimerReclamo(dto.getHoraPrimerReclamo())
                .build();

    }
}