package sistemaDeAlumbrado.demo.request.usuarios;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUsuarioPerfilRequest {
    @NotNull(message = "id is required")
    private Long id;
    @NotNull(message = "nombre is required")
    private String newNombre;
    @NotNull(message = "apellido is required")
    private String newApellido;
    @NotNull(message = "mail is required")
    private String newMail;
    @NotNull(message = "telefono is required")
    private String newTelefono;
    @NotNull(message = "fechaNacimiento is required")
    private LocalDate newFechaNacimiento;
}
