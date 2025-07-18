package sistemaDeAlumbrado.demo.request.solicitudes;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateSolicitudRequest {
    private Long idReclamo;
    private Integer idSubTipoReclamo;
    private Integer posteId;
    private Long tecnico1Id;
    private Long tecnico2Id;
    private LocalDate fechaArreglo;
    private Time horaArreglo;
    private List<Integer> tiposReparacionesIds;
    private String observacionArreglo;
    private MultipartFile fotografiaArreglo;
}
