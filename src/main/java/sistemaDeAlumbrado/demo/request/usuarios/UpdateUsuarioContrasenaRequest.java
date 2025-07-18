package sistemaDeAlumbrado.demo.request.usuarios;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUsuarioContrasenaRequest {
    @NotNull(message = "id is required")
    private Long id;
    @NotNull(message = "old password is required")
    private String oldPassword;
    @NotNull(message = "new password is required")
    private String newPassword;
}
