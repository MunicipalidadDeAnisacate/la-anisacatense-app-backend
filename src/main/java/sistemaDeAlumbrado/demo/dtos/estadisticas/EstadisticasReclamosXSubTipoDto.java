package sistemaDeAlumbrado.demo.dtos.estadisticas;

import lombok.Data;


@Data
public class EstadisticasReclamosXSubTipoDto {
    private Integer idSubTipoReclamo;
    private String nombreSubTipoReclamo;
    private Long cantResueltas;
    private Long cantEnProceso;
    private Long cantCambioFoco;
    private Long cantCambioFotocelula;
    private Long cantCambioFusible;
    private Long cantOtro;


    public EstadisticasReclamosXSubTipoDto(){}

    public EstadisticasReclamosXSubTipoDto(Integer idSubTipoReclamo,
                                           String nombreTipoReclamo,
                                           Long cantEnProceso,
                                           Long cantResueltas) {
        this.idSubTipoReclamo = idSubTipoReclamo;
        this.nombreSubTipoReclamo = nombreTipoReclamo;
        this.cantResueltas = cantResueltas;
        this.cantEnProceso = cantEnProceso;
    }

    public void setEstaditicasReclamosLuminaria(Long cantCambioFoco,
                                                Long cantCambioFusible,
                                                Long cantCambioFotocelula,
                                                Long cantOtro){
        this.cantCambioFoco = cantCambioFoco;
        this.cantCambioFusible = cantCambioFusible;
        this.cantCambioFotocelula = cantCambioFotocelula;
        this.cantOtro = cantOtro;
    }
}
