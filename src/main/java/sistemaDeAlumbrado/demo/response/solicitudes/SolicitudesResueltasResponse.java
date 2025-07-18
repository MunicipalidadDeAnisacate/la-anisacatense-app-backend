package sistemaDeAlumbrado.demo.response.solicitudes;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.entities.Reclamo;
import sistemaDeAlumbrado.demo.response.localsFormatter.LocalsFormatter;

@Data
@Builder
public class SolicitudesResueltasResponse {
    private Long id;
    private String nombrePoste;
    private String nombreSubTipoReclamo;
    private String nombreTipoReclamo;
    private String fechaArreglo;
    private String horaArreglo;
    private String nombreEstadoReclamo;
    private String nombreTecnico1;
    private String apellidoTecnico1;
    private String nombreTecnico2;
    private String apellidoTecnico2;
    private Double latitud;
    private Double longitud;
    private String nombreBarrio;
    private String nombreAnimal;

    public static SolicitudesResueltasResponse from(Reclamo aReclamo){

        return SolicitudesResueltasResponse.builder()
                .id(aReclamo.getId())
                .nombreSubTipoReclamo(aReclamo.getSubTipoReclamo().getNombreSubTipo())
                .nombreTipoReclamo(aReclamo.getSubTipoReclamo().getTipoReclamo().getNombreTipo())
                .nombrePoste(aReclamo.getPosteLuz() != null ? aReclamo.getPosteLuz().getNombrePoste() : null)
                .latitud(aReclamo.getLatitudReclamo() != null ? aReclamo.getLatitudReclamo() : null)
                .longitud(aReclamo.getLongitudReclamo() != null ? aReclamo.getLongitudReclamo() : null)
                .nombreBarrio(aReclamo.getPosteLuz() == null ? null : aReclamo.getPosteLuz().getBarrio().getNombre())
                .fechaArreglo(LocalsFormatter.formatearFecha(aReclamo.getFechaArreglo()))
                .horaArreglo(LocalsFormatter.formatearHora(aReclamo.getHoraArreglo()))
                .nombreEstadoReclamo(aReclamo.getEstadoReclamo().getNombreEstado())
                .nombreTecnico1(aReclamo.getTecnico1().getNombre())
                .apellidoTecnico1(aReclamo.getTecnico1().getApellido())
                .nombreTecnico2(aReclamo.getTecnico2() != null ? aReclamo.getTecnico2().getNombre() : null)
                .apellidoTecnico2(aReclamo.getTecnico2() != null ? aReclamo.getTecnico2().getApellido() : null)
                .nombreAnimal(aReclamo.getAnimal() != null ? aReclamo.getAnimal().getNombre() : null)
                .build();
    }
}
