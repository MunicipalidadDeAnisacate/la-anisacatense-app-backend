package sistemaDeAlumbrado.demo.response.consultasCiudadanas;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.consultasCiudadanas.ConsultaDeProyectoDto;
import sistemaDeAlumbrado.demo.entities.ConsultaCiudadana;
import sistemaDeAlumbrado.demo.response.localsFormatter.LocalsFormatter;


@Data
@Builder
public class ConsultaItemResponse {
    private Long id;
    private String descripcion;
    private String titulo;
    private String fechaInicio;
    private String horaInicio;
    private String fechaCierre;
    private String horaCierre;

    public static ConsultaItemResponse from(ConsultaCiudadana consultaCiudadana){
        return ConsultaItemResponse
                .builder()
                .id(consultaCiudadana.getId())
                .titulo(consultaCiudadana.getTitulo())
                .descripcion(consultaCiudadana.getDescripcion())
                .fechaInicio(LocalsFormatter.formatearFecha(consultaCiudadana.getFechaInicio()))
                .horaInicio(LocalsFormatter.formatearHora(consultaCiudadana.getHoraInicio()))
                .fechaCierre(LocalsFormatter.formatearFecha(consultaCiudadana.getFechaCierre()))
                .horaCierre(LocalsFormatter.formatearHora(consultaCiudadana.getHoraCierre()))
                .build();
    }

    public static ConsultaItemResponse from(ConsultaDeProyectoDto eleccion){
        return ConsultaItemResponse
                .builder()
                .id(eleccion.getId())
                .titulo(eleccion.getTitulo())
                .fechaInicio(LocalsFormatter.formatearFecha(eleccion.getFechaInicio()))
                .horaInicio(LocalsFormatter.formatearHora(eleccion.getHoraInicio()))
                .fechaCierre(LocalsFormatter.formatearFecha(eleccion.getFechaCierre()))
                .horaCierre(LocalsFormatter.formatearHora(eleccion.getHoraCierre()))
                .build();
    }
}
