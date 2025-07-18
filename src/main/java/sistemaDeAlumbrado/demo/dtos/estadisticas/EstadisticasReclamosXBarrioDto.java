package sistemaDeAlumbrado.demo.dtos.estadisticas;

import lombok.Data;


@Data
public class EstadisticasReclamosXBarrioDto {
    private Integer zona;
    private String nombreBarrio;
    private Long cantResueltos;
    private Long cantEnProceso;


    public EstadisticasReclamosXBarrioDto(Integer zona,
                               String nombreBarrio,
                               Long cantReclamosEnProceso,
                               Long cantReclamosResueltos) {
        this.zona = zona;
        this.nombreBarrio = nombreBarrio;
        this.cantEnProceso = cantReclamosEnProceso;
        this.cantResueltos = cantReclamosResueltos;
    }

    public EstadisticasReclamosXBarrioDto(int zona,
                                          String nombreBarrio,
                                          int cantEnProceso,
                                          int cantResuelto) {
        this.zona            = zona;
        this.nombreBarrio    = nombreBarrio;
        this.cantEnProceso   = (long) cantEnProceso;
        this.cantResueltos   = (long) cantResuelto;
    }
}
