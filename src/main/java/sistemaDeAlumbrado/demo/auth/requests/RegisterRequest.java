package sistemaDeAlumbrado.demo.auth.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    @NotNull
    private String nombre;
    @NotNull
    private String apellido;
    @NotNull
    private String mail;
    @NotNull
    private String dni;
    @NotNull
    private String password;
    @NotNull
    private String telefono;
    @NotNull
    private LocalDate fechaNacimiento;
    @NotNull
    private Integer barrioId;
    @NotNull
    private String nombreCalle;
    @NotNull
    private String numeroCalle;
    @NotNull
    private String manzana;
    @NotNull
    private String lote;
    private Double latitudeDomicilio;
    private Double longitudeDomicilio;
}
