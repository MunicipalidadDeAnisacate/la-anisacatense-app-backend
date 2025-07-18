package sistemaDeAlumbrado.demo.response.usuarios;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.entities.UsuarioEntity;

@Data
@Builder
public class UsuarioDomicilioResponse {
    private Integer barrioId;
    private String calle;
    private String numeroCalle;
    private String manzana;
    private String lote;
    private Double latitudDomicilio;
    private Double longitudDomicilio;

    public static UsuarioDomicilioResponse from(UsuarioEntity usuario){
        return UsuarioDomicilioResponse
                .builder()
                .barrioId(usuario.getBarrio().getId())
                .calle(usuario.getNombreCalle())
                .numeroCalle(usuario.getNumeroCalle())
                .manzana(usuario.getManzana())
                .lote(usuario.getLote())
                .latitudDomicilio(usuario.getLatitudeDomicilio())
                .longitudDomicilio(usuario.getLongitudeDomicilio())
                .build();
    }
}
