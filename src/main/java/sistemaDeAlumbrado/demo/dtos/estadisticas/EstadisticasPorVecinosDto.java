package sistemaDeAlumbrado.demo.dtos.estadisticas;

import lombok.Data;

import java.util.List;

@Data
public class EstadisticasPorVecinosDto {
    private Long cantidadVecinosEnLaApp;
    private Long cantidadVecinosSolicitaron;
    private List<EstadisticasVecinoCantSolicitudesDto> estadisticasVecinoCantSolicitudes;

    public EstadisticasPorVecinosDto(Long cantidadVecinosEnLaApp,
                                     Long cantidadVecinosSolicitaron,
                                     List<EstadisticasVecinoCantSolicitudesDto> list) {
        this.cantidadVecinosEnLaApp = cantidadVecinosEnLaApp;
        this.cantidadVecinosSolicitaron = cantidadVecinosSolicitaron;
        this.estadisticasVecinoCantSolicitudes = list;
    }
}
