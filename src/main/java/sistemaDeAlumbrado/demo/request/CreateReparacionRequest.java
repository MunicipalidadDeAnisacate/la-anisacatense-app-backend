package sistemaDeAlumbrado.demo.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.time.LocalDate;

@Data
public class CreateReparacionRequest {
    private Integer tipoReclamoId;
    private Double latitudReclamo;
    private Double longitudReclamo;
    private Long tecnico1Id;
    private Long tecnico2Id;
    private LocalDate fechaArreglo;
    private Time horaArreglo;
    private String observacionArreglo;
    private MultipartFile fotoArreglo;
    private Integer posteId;
    private Integer idBarrio;
}
