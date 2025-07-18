package sistemaDeAlumbrado.demo.dtos;

import lombok.Data;
import sistemaDeAlumbrado.demo.entities.Reclamo;
import sistemaDeAlumbrado.demo.entities.ReclamoXUsuario;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class InfoReclamoLuminariaDto {
    private Long reclamoId;
    private Integer nroPoste;
    private Set<InfoReclamoLuminariaUsuarioDto> infoReclamoLuminariaUsuarios;

    public InfoReclamoLuminariaDto(Reclamo reclamo, List<ReclamoXUsuario> reclamoXUsuarios){
        this.reclamoId = reclamo.getId();
        this.nroPoste = reclamo.getPosteLuz().getIdPoste();
        this.infoReclamoLuminariaUsuarios = new HashSet<>();

        reclamoXUsuarios.forEach(reclamoXUsuario -> {
            infoReclamoLuminariaUsuarios.add(new InfoReclamoLuminariaUsuarioDto(reclamoXUsuario));
        });
    }
}
