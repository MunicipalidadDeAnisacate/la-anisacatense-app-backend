package sistemaDeAlumbrado.demo.dtos;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class SolicitudesVariasDto {
    private Long id;
    private String nombreSubTipoReclamo;
    private Integer nroSubTipoReclamo;
    private String nombreEstado;
    private String nombreTipoReclamo;
    private Double latitudReclamo;
    private Double longitudReclamo;
    private Set<VecinoDto> vecinoDtoList;


    public SolicitudesVariasDto(Object[] data){
        this.id = ((Number) data[0]).longValue();
        this.nombreSubTipoReclamo = (String) data[1];
        this.nroSubTipoReclamo = ((Number) data[2]).intValue();
        this.nombreEstado = (String) data[3];
        this.nombreTipoReclamo = (String) data[4];
        this.latitudReclamo = ((Number) data[5]).doubleValue();
        this.longitudReclamo = ((Number) data[6]).doubleValue();
        this.vecinoDtoList = this.setVecinoSet(data);
    }


    public Set<VecinoDto> setVecinoSet(Object[] vecinoData){
        Set<VecinoDto> vecinoDtoSet = new HashSet<>();
        vecinoDtoSet.add(new VecinoDto(vecinoData));
        return vecinoDtoSet;
    }
}
