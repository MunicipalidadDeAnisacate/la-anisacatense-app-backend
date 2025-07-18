package sistemaDeAlumbrado.demo.dtos.estadisticas;

import lombok.Data;

import java.util.List;

@Data
public class EstadisticasReclamosXTipoDto {
    private Integer idTipo;
    private String nombreTipoReclamo;
    private Long cantResueltas;
    private Long cantEnProceso;
    List<EstadisticasReclamosXSubTipoDto> estadisticasReclamosXSubTipoDtoList;


    public EstadisticasReclamosXTipoDto(Integer idTipo,
                                        String nombreTipoReclamo,
                                        Long cantEnProceso,
                                        Long cantResueltas) {
        this.idTipo = idTipo;
        this.nombreTipoReclamo = nombreTipoReclamo;
        this.cantResueltas = cantResueltas;
        this.cantEnProceso = cantEnProceso;
    }

    public void setEstadisticasReclamosXSubTipo(List<EstadisticasReclamosXSubTipoDto> list){
        this.estadisticasReclamosXSubTipoDtoList = list;
    }
}
