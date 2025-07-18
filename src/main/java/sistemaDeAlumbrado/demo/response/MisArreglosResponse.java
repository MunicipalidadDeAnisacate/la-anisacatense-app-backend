package sistemaDeAlumbrado.demo.response;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.arreglos.ArregloDto;
import sistemaDeAlumbrado.demo.entities.Arreglo;
import sistemaDeAlumbrado.demo.response.localsFormatter.LocalsFormatter;

import java.sql.Time;
import java.time.LocalDate;

@Data
@Builder
public class MisArreglosResponse {
    private Long id;
    private String nombrePoste;
    private String nombreAnimal;
    private String nombreTipoReclamo;
    private String fechaArreglo;
    private String horaArreglo;
    private String nombreTecnico1;
    private String apellidoTecnico1;
    private String nombreTecnico2;
    private String apellidoTecnico2;
    private Double latitud;
    private Double longitud;
    private String nombreBarrio;


    public static MisArreglosResponse from(Arreglo arreglo) {
        return MisArreglosResponse.builder()
                .id(arreglo.getId())
                .nombreTipoReclamo(arreglo.getTipoReclamo().getNombreTipo())
                .nombreTecnico1(arreglo.getTecnico1().getNombre())
                .apellidoTecnico1(arreglo.getTecnico1().getApellido())
                .nombreTecnico2(arreglo.getTecnico2() != null ? arreglo.getTecnico2().getNombre() : null)
                .apellidoTecnico2(arreglo.getTecnico2() != null ? arreglo.getTecnico2().getApellido() : null)
                .fechaArreglo(LocalsFormatter.formatearFecha(arreglo.getFechaArreglo()))
                .horaArreglo(LocalsFormatter.formatearHora(arreglo.getHoraArreglo()))
                .nombrePoste(arreglo.getPoste() != null ? arreglo.getPoste().getNombrePoste() : null)
                .latitud(arreglo.getLatitudReclamo() != null ? arreglo.getLatitudReclamo() : null)
                .longitud(arreglo.getLongitudReclamo() != null ? arreglo.getLongitudReclamo() : null)
                .nombreBarrio(arreglo.getBarrio() != null ? arreglo.getBarrio().getNombre() : arreglo.getPoste().getBarrio().getNombre())
                .build();
    }

    public static MisArreglosResponse from(ArregloDto dto) {
        return MisArreglosResponse.builder()
                .id(dto.getId())
                .nombreTipoReclamo(dto.getNombreTipoReclamo())
                .nombreTecnico1(dto.getNombreTecnico1())
                .apellidoTecnico1(dto.getApellidoTecnico1())
                .nombreTecnico2(dto.getNombreTecnico2() != null ? dto.getNombreTecnico2() : null)
                .apellidoTecnico2(dto.getApellidoTecnico2() != null ? dto.getApellidoTecnico2() : null)
                .fechaArreglo(LocalsFormatter.formatearFecha(dto.getFechaArreglo()))
                .horaArreglo(LocalsFormatter.formatearHora(dto.getHoraArreglo()))
                .nombrePoste(dto.getNombrePoste() != null ? dto.getNombrePoste() : null)
                .latitud(dto.getLatitud() != null ? dto.getLatitud() : null)
                .longitud(dto.getLongitud() != null ? dto.getLongitud() : null)
                .nombreBarrio(dto.getNombreBarrio())
                .build();
    }
}
