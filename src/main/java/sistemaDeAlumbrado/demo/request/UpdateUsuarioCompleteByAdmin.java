package sistemaDeAlumbrado.demo.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUsuarioCompleteByAdmin {
    private Long id;
    private String newDni;
    private String newPassword;
    private String newNombre;
    private String newApellido;
    private Integer tipoUsuarioId;
    private Integer cuadrillaId;
    private String newMail;
    private String newTelefono;
    private LocalDate newFechaNacimiento;
    private Integer barrioId;
    private String newNombreCalle;
    private String newNumeroCalle;
    private String newManzana;
    private String newLote;
}
