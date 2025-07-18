package sistemaDeAlumbrado.demo.response;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.dtos.VecinoDto;
import sistemaDeAlumbrado.demo.entities.UsuarioEntity;

@Data
@Builder
public class VecinoResponse {
    private String dni;
    private String nombre;
    private String apellido;
    private String mail;
    private String telefono;
    private String nombreCalle;
    private String numeroCalle;
    private String manzana;
    private String lote;

    public static VecinoResponse from(UsuarioEntity aUsuario){
        return VecinoResponse.builder()
                .dni(aUsuario.getDni())
                .nombre(aUsuario.getNombre())
                .apellido(aUsuario.getApellido())
                .mail(aUsuario.getMail())
                .telefono(aUsuario.getTelefono())
                .nombreCalle(aUsuario.getNombreCalle())
                .numeroCalle(aUsuario.getNumeroCalle())
                .manzana(aUsuario.getManzana())
                .lote(aUsuario.getLote())
                .build();
    }

    public static VecinoResponse from(VecinoDto aUsuario){
        return VecinoResponse.builder()
                .dni(aUsuario.getDni())
                .nombre(aUsuario.getNombre())
                .apellido(aUsuario.getApellido())
                .mail(aUsuario.getMail())
                .telefono(aUsuario.getTelefono())
                .nombreCalle(aUsuario.getNombreCalle())
                .numeroCalle(aUsuario.getNumeroCalle())
                .manzana(aUsuario.getManzana())
                .lote(aUsuario.getLote())
                .build();
    }
}
