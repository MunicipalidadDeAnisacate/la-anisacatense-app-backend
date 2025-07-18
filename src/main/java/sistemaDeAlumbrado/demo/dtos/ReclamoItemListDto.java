package sistemaDeAlumbrado.demo.dtos;

import lombok.Data;
import sistemaDeAlumbrado.demo.entities.Reclamo;
import sistemaDeAlumbrado.demo.entities.ReclamoXUsuario;

import java.sql.Time;
import java.time.LocalDate;


@Data
public class ReclamoItemListDto {
    private Long id;
    private String nombreSubTipoReclamo;
    private String nombreTipoReclamo;
    private String nombreEstado;
    private String nombreBarrio;
    private String nombrePoste;
    private String nombreAnimal;
    private String nombreTecnico1;
    private String apellidoTecnico1;
    private String nombreTecnico2;
    private String apellidoTecnico2;
    private LocalDate fechaArreglo;
    private Time horaArreglo;
    private String nombreVecino;
    private String apellidoVecino;
    private LocalDate fechaPrimerReclamo;
    private Time horaPrimerReclamo;


    public ReclamoItemListDto(Object[] row) {
        this.id                 = (Long) row[0];
        this.fechaArreglo       = row[1] != null ? ((java.sql.Date) row[1]).toLocalDate() : null;
        this.horaArreglo        = row[2] != null ? ((Time) row[2]) : null;

        String barrioReclamo = row[3] != null ? (String) row[3] : null;
        String barrioVecino = row[16] != null ? (String) row[16] : null;
        String barrioPoste = row[10] != null ? (String) row[10] : null;
        this.nombreBarrio = barrioReclamo != null ? barrioReclamo : (barrioVecino != null ? barrioVecino : barrioPoste);

        this.fechaPrimerReclamo = row[4] != null ? ((java.sql.Date) row[4]).toLocalDate() : null;
        this.horaPrimerReclamo = row[5] != null ? ((Time) row[5]) : null;
        this.nombreEstado = (String) row[6];
        this.nombreSubTipoReclamo = (String) row[7];
        this.nombreTipoReclamo  = (String) row[8];
        this.nombrePoste        = row[9]  != null ? (String) row[9]  : null;
        this.nombreAnimal       = row[11] != null ? (String) row[11] : null;
        this.nombreTecnico1     = row[12] != null ? (String) row[12] : null;
        this.apellidoTecnico1   = row[13] != null ? (String) row[13] : null;
        this.nombreTecnico2     = row[14] != null ? (String) row[14] : null;
        this.apellidoTecnico2   = row[15] != null ? (String) row[15] : null;

        this.nombreVecino = row[17] != null ? (String) row[17] : null;
        this.apellidoVecino = row[18] != null ? (String) row[18] : null;
    }
}

