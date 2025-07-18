package sistemaDeAlumbrado.demo.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreatePosteRequest {
    private String nombrePoste;
    private Integer idBarrio;
    private Double latitud;
    private Double longitud;
    private LocalDate fechaCarga;
}
