package sistemaDeAlumbrado.demo.request.solicitudes;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.time.LocalDate;

@Data
public class CreateSolicitudRequest {
    @NotNull(message = "vecino id is required")
    private Long idVecino;
    @NotNull(message = "sub tipo reclamo id is required")
    private Integer idSubTipoReclamo;
    @NotNull(message = "hora reclamo is required")
    private Time horaReclamo;
    @NotNull(message = "fecha reclamo is required")
    private LocalDate fechaReclamo;
    private Integer idAnimal;
    private Integer posteId;
    private Integer idBarrio;
    private MultipartFile fotoReclamo;
    private String observacionReclamo;
    private Double latitud;
    private Double longitud;
}
