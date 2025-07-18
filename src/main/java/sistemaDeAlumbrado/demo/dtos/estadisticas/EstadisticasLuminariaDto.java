package sistemaDeAlumbrado.demo.dtos.estadisticas;

import lombok.Data;

@Data
public class EstadisticasLuminariaDto {
    private Long cantSolicitudesConCambioFoco;
    private Long cantSolicitudesConCambioFotocelula;
    private Long cantSolicitudesConCambioFusible;
    private Long cantSolicitudesConOtro;

    public EstadisticasLuminariaDto(Long cambioFoco,
                                    Long cambioFusible,
                                    Long cambioFotocelula,
                                    Long cambioOtro) {
        this.cantSolicitudesConCambioFoco = cambioFoco != null ? cambioFoco : 0L;
        this.cantSolicitudesConCambioFotocelula = cambioFotocelula != null ? cambioFotocelula : 0L;
        this.cantSolicitudesConCambioFusible = cambioFusible != null ? cambioFusible : 0L;
        this.cantSolicitudesConOtro = cambioOtro != null ? cambioOtro : 0L;
    }
}
