package sistemaDeAlumbrado.demo.response;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.entities.Poste;

@Data
@Builder
public class PosteSinFiltroResponse {
    private Integer id;
    private Double longitude;
    private Double latitude;
    private String nombrePoste;

    public static PosteSinFiltroResponse from(Poste poste) {
        return PosteSinFiltroResponse
                .builder()
                .id(poste.getIdPoste())
                .latitude(poste.getLatitude())
                .longitude(poste.getLongitude())
                .nombrePoste(poste.getNombrePoste())
                .build();
    }
}
