package sistemaDeAlumbrado.demo.dtos.estadisticas;

import lombok.Data;

@Data
public class EstadisticasVecinoCantSolicitudesDto {
    private String nombreVecino;
    private String apellidoVecino;
    private String dni;
    private Long cantSolicitudesProceso;
    private Long cantSolicitudesResueltas;

    public EstadisticasVecinoCantSolicitudesDto(String nombreVecino, String apellidoVecino,
                                                String dni, Long cantSolicitudesProceso, Long cantSolicitudesResueltas) {
        this.nombreVecino = nombreVecino;
        this.apellidoVecino = apellidoVecino;
        this.dni = dni;
        this.cantSolicitudesProceso = cantSolicitudesProceso;
        this.cantSolicitudesResueltas = cantSolicitudesResueltas;
    }
}
