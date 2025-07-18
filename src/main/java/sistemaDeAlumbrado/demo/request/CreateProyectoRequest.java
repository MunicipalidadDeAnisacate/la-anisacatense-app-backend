package sistemaDeAlumbrado.demo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateProyectoRequest {
    @NotNull(message = "titulo is required")
    private String titulo;
    @NotNull(message = "descripcion is required")
    private String descripcion;
    private MultipartFile archivoUrl;
    @NotNull(message = "userId is required")
    private Long usuarioId;
}
