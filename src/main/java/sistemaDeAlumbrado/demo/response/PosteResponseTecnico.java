package sistemaDeAlumbrado.demo.response;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.InfoReclamoLuminariaUsuarioDto;
import sistemaDeAlumbrado.demo.dtos.PosteDto;

import java.util.Set;

@Data
@Builder
public class PosteResponseTecnico {
    private Integer idPoste;
    private String nombrePoste;
    private Integer estadoPoste;
    private Double latitude;
    private Double longitude;
    private Long idReclamo;
    private Set<InfoReclamoLuminariaUsuarioDto> usuarios;

    public static PosteResponseTecnico from(PosteDto poste) {
        if (poste.getEstadoPoste().equals(2)) {
            return PosteResponseTecnico.builder()
                    .idPoste(poste.getIdPoste())
                    .nombrePoste(poste.getNombrePoste())
                    .estadoPoste(poste.getEstadoPoste())
                    .latitude(poste.getLatitude())
                    .longitude(poste.getLongitude())
                    .idReclamo(poste.getNroReclamo())
                    .usuarios(poste.getUsuarioDeReclamoDtos())
                    .build();
        } else {
            return PosteResponseTecnico.builder()
                    .idPoste(poste.getIdPoste())
                    .nombrePoste(poste.getNombrePoste())
                    .estadoPoste(poste.getEstadoPoste())
                    .latitude(poste.getLatitude())
                    .longitude(poste.getLongitude())
                    .build();
        }
    }
}