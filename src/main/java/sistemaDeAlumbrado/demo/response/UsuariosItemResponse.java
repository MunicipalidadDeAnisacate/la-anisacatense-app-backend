package sistemaDeAlumbrado.demo.response;

import lombok.Builder;
import lombok.Data;
import sistemaDeAlumbrado.demo.entities.UsuarioEntity;
import sistemaDeAlumbrado.demo.response.localsFormatter.LocalsFormatter;

@Builder
@Data
public class UsuariosItemResponse {
    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private String nombreTipoUsuario;
    private String nombreCuadrilla;
    private String mail;
    private String telefono;
    private String fechaNacimiento;
    private String nombreBarrio;
    private String nombreCalle;
    private String numeroCalle;
    private String manzana;
    private String lote;

    public static UsuariosItemResponse from(UsuarioEntity usuario){
        return UsuariosItemResponse
                .builder()
                .id(usuario.getId())
                .dni(usuario.getDni())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .nombreTipoUsuario(usuario.getTipoUsuario().getNombreTipo())
                .nombreCuadrilla(usuario.getCuadrilla() != null ? usuario.getCuadrilla().getNombre() : null)
                .mail(usuario.getMail())
                .telefono(usuario.getTelefono())
                .fechaNacimiento(LocalsFormatter.formatearFecha(usuario.getFechaNacimiento()))
                .nombreBarrio(usuario.getBarrio().getNombre())
                .nombreCalle(usuario.getNombreCalle() != null ? usuario.getNombreCalle() : null)
                .numeroCalle(usuario.getNumeroCalle() != null ? usuario.getNumeroCalle() : null)
                .manzana(usuario.getManzana() != null ? usuario.getManzana() : null)
                .lote(usuario.getLote() != null ? usuario.getLote() : null)
                .build();
    }
}


