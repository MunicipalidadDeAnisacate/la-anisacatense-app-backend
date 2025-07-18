package sistemaDeAlumbrado.demo.response;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.entities.UsuarioEntity;

@Data
@Builder
public class TecnicoDetailsResponse {
    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private String mail;
    private String telefono;
    private String cuadrilla;
    private String nombreCalle;
    private String nroCalle;
    private String lote;
    private String manzana;

    public static TecnicoDetailsResponse from(UsuarioEntity tecnico){
        return TecnicoDetailsResponse
                .builder()
                .id(tecnico.getId())
                .dni(tecnico.getDni())
                .nombre(tecnico.getNombre())
                .apellido(tecnico.getApellido())
                .mail(tecnico.getMail())
                .telefono(tecnico.getTelefono())
                .cuadrilla(tecnico.getCuadrilla().getNombre())
                .nombreCalle(tecnico.getNombreCalle())
                .nroCalle(tecnico.getNumeroCalle())
                .lote(tecnico.getLote())
                .manzana(tecnico.getManzana())
                .build();
    }
}
