package sistemaDeAlumbrado.demo.response;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.entities.UsuarioEntity;

@Data
@Builder
public class TecnicoResponse {
    private Long id;
    private String nombre;
    private String apellido;

    public static TecnicoResponse from(UsuarioEntity aUsuario) {
        return TecnicoResponse.builder()
                .id(aUsuario.getId())
                .nombre(aUsuario.getNombre())
                .apellido(aUsuario.getApellido())
                .build();
    }
}
