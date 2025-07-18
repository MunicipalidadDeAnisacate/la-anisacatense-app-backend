package sistemaDeAlumbrado.demo.dtos;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PosteDto {
    private Integer idPoste;
    private String nombrePoste;
    private Integer estadoPoste;
    private Double latitude;
    private Double longitude;
    private Long nroReclamo;
    private Set<InfoReclamoLuminariaUsuarioDto> usuarioDeReclamoDtos;

    public PosteDto(Integer idPoste, String nombrePoste, Integer estadoPoste, Double latitude, Double longitude) {
        this.idPoste = idPoste;
        this.nombrePoste = nombrePoste;
        this.estadoPoste = estadoPoste;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setInformacionReclamo(Long nroReclamo, Set<InfoReclamoLuminariaUsuarioDto> usuarioDeReclamoDtos) {
        this.nroReclamo = nroReclamo;
        this.usuarioDeReclamoDtos = usuarioDeReclamoDtos;
    }

    public PosteDto(Object[] rows){
        this.idPoste = ((Number) rows[0]).intValue();
        this.nombrePoste = (rows[1]) != null ? rows[1].toString() : null; ;
        this.estadoPoste = ((Number) rows[2]).intValue();
        this.latitude =  ((Number) rows[3]).doubleValue();
        this.longitude = ((Number) rows[4]).doubleValue();
        this.usuarioDeReclamoDtos = new HashSet<>();
    }

}
