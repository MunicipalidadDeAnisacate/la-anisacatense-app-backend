package sistemaDeAlumbrado.demo.request.usuarios;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUsuarioDomicilioRequest {
    @NotNull(message = "id is required")
    private Long id;
    @NotNull(message = "barrio id is required")
    private Integer barrioId;
    private String newCalle;
    private String newNumeroCalle;
    private String newManzana;
    private String newLote;
    @NotNull(message = "latitud is required")
    private Double newLatitudDomicilio;
    @NotNull(message = "longitud is required")
    private Double newLongitudDomicilio;
}
