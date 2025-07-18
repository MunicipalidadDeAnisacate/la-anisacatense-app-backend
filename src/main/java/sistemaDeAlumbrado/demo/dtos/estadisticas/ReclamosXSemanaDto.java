package sistemaDeAlumbrado.demo.dtos.estadisticas;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class ReclamosXSemanaDto {
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private long cantidadResuelto;

    public ReclamosXSemanaDto(Object[] row) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        this.fechaDesde = row[0] != null ?
                LocalDate.parse(row[0].toString(), formatter) :
                null;

        this.fechaHasta = row[1] != null ?
                LocalDate.parse(row[1].toString(), formatter) :
                null;

        this.cantidadResuelto =  row[2] != null ? ((BigDecimal) row[2]).longValueExact() : 0L;
    }

    public void modificarFechaHastaUltima(LocalDate today){
        if (this.fechaHasta.isAfter(today)){
            this.setFechaHasta(today);
        }
    }
}
