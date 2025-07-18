package sistemaDeAlumbrado.demo.request;

import lombok.Data;

import java.time.LocalDate;


@Data
public class CreateUsuarioComplete {
    private String dni;
    private String password;
    private String nombre;
    private String apellido;
    private Integer tipoUsuarioId;
    private Integer cuadrillaId;
    private String mail;
    private String telefono;
    private LocalDate fechaNacimiento;
    private Integer barrioId;
    private String nombreCalle;
    private String numeroCalle;
    private String manzana;
    private String lote;
}

