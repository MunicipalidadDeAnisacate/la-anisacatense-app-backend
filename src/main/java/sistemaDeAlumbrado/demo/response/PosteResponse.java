package sistemaDeAlumbrado.demo.response;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.PosteDto;

@Data
@Builder
public class PosteResponse {
    private Integer idPoste;
    private String nombrePoste;
    private Integer estadoPoste;
    private Double latitude;
    private Double longitude;

    public static PosteResponse from(PosteDto poste) {
        return PosteResponse.builder()
                .idPoste(poste.getIdPoste())
                .nombrePoste(poste.getNombrePoste())
                .estadoPoste(poste.getEstadoPoste())
                .latitude(poste.getLatitude())
                .longitude(poste.getLongitude())
                .build();
    }
}
