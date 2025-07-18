package sistemaDeAlumbrado.demo.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sistemaDeAlumbrado.demo.exceptions.DuplicateDataException;
import sistemaDeAlumbrado.demo.exceptions.IncorrectOldPasswordException;
import sistemaDeAlumbrado.demo.request.CreateUsuarioComplete;
import sistemaDeAlumbrado.demo.request.UpdateUsuario;
import sistemaDeAlumbrado.demo.request.UpdateUsuarioCompleteByAdmin;
import sistemaDeAlumbrado.demo.request.usuarios.UpdateUsuarioContrasenaRequest;
import sistemaDeAlumbrado.demo.request.usuarios.UpdateUsuarioDomicilioRequest;
import sistemaDeAlumbrado.demo.request.usuarios.UpdateUsuarioPerfilRequest;
import sistemaDeAlumbrado.demo.response.TecnicoDetailsResponse;
import sistemaDeAlumbrado.demo.response.TecnicoResponse;
import sistemaDeAlumbrado.demo.response.UsuariosItemResponse;
import sistemaDeAlumbrado.demo.response.usuarios.UsuarioDomicilioResponse;
import sistemaDeAlumbrado.demo.services.UsuarioEntityService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioEntityController {
    private final UsuarioEntityService usuarioEntityService;

    @GetMapping("/roles/{tipoUsuarioId}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> findAllVecinos(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "50") int size,
                                                 @PathVariable final Integer tipoUsuarioId) {

        Pageable pageable = PageRequest.of(page, size);
        val usuariosPage = usuarioEntityService.findAllUsuariosByRol(tipoUsuarioId, pageable);

        val usuarios = usuariosPage.getContent()
                .stream()
                .map(UsuariosItemResponse::from)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("usuarios", usuarios);
        response.put("currentPage", usuariosPage.getNumber());
        response.put("totalItems", usuariosPage.getTotalElements());
        response.put("totalPages", usuariosPage.getTotalPages());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/tecnicos")
    @PreAuthorize("hasAnyRole('tecnico', 'admin')")
    public ResponseEntity<Object> findAllTecnicos() {
        val tecnicos = usuarioEntityService.findAllTecnicos()
                .stream()
                .map(TecnicoDetailsResponse::from);
        return ResponseEntity.ok(tecnicos);
    }


    @GetMapping("/{dni}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> findUsuarioByDni(@PathVariable String dni) {
        val usuario = usuarioEntityService.findByDni(dni);
        return ResponseEntity.ok(UsuariosItemResponse.from(usuario));
    }


    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('vecino')")
    public ResponseEntity<Object> findUsuarioById(@PathVariable Long id) {
        val usuario = usuarioEntityService.findById(id);
        return ResponseEntity.ok(UsuariosItemResponse.from(usuario));
    }


    @GetMapping("/domicilio/id/{id}")
    @PreAuthorize("hasRole('vecino')")
    public ResponseEntity<Object> findDomicilioUsuarioById(@PathVariable Long id) {
        val usuario = usuarioEntityService.findById(id);
        return ResponseEntity.ok(UsuarioDomicilioResponse.from(usuario));
    }


    @GetMapping("/tecnicos/{tecnicoId}/{cuadrillaId}")
    @PreAuthorize("hasAnyRole('tecnico', 'admin')")
    public ResponseEntity<Object> findAllTecnicosByCuadrilla(@PathVariable Long tecnicoId,
                                                  @PathVariable Integer cuadrillaId) {
        val tecnicos = usuarioEntityService.findTecnicos(tecnicoId, cuadrillaId)
                .stream().map(TecnicoResponse::from);
        return ResponseEntity.ok(tecnicos);
    }


    @DeleteMapping("/{eliminadorId}/{eliminadoId}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> deleteUsuario(@PathVariable Long eliminadorId, @PathVariable Long eliminadoId) {
        usuarioEntityService.deleteUserFisico(eliminadorId, eliminadoId);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/mailstelefonos/nuevos/{idVecino}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> setNewMailTelefono(@PathVariable Long idVecino,
                                                     @RequestBody UpdateUsuario dto){
        usuarioEntityService.setNewMailTelefono(idVecino, dto.getMail(), dto.getMail());
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/admin/edit")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> patchUsuarioByAdmin(@RequestBody UpdateUsuarioCompleteByAdmin request){
        usuarioEntityService.updateUsuarioByAdmin(
                request.getId(),
                request.getNewDni(),
                request.getNewPassword(),
                request.getNewNombre(),
                request.getNewApellido(),
                request.getTipoUsuarioId(),
                request.getCuadrillaId(),
                request.getNewMail(),
                request.getNewTelefono(),
                request.getNewFechaNacimiento(),
                request.getBarrioId(),
                request.getNewNombreCalle(),
                request.getNewNumeroCalle(),
                request.getNewManzana(),
                request.getNewLote()
        );
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/perfil/edit")
    @PreAuthorize("hasRole('vecino')")
    public ResponseEntity<Object> patchPerfilUsuario(@RequestBody @Valid UpdateUsuarioPerfilRequest request){
        usuarioEntityService.updatePerfilUsuario(
                request.getId(),
                request.getNewNombre(),
                request.getNewApellido(),
                request.getNewMail(),
                request.getNewTelefono(),
                request.getNewFechaNacimiento()
        );
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/domicilio/edit")
    @PreAuthorize("hasRole('vecino')")
    public ResponseEntity<Object> patchDomicilioUsuario(@RequestBody @Valid UpdateUsuarioDomicilioRequest request){
        usuarioEntityService.updateDomicilioUsuario(
                request.getId(),
                request.getBarrioId(),
                request.getNewCalle(),
                request.getNewNumeroCalle(),
                request.getNewManzana(),
                request.getNewLote(),
                request.getNewLatitudDomicilio(),
                request.getNewLongitudDomicilio()
        );
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/contrasena/edit")
    @PreAuthorize("hasRole('vecino')")
    public ResponseEntity<Object> patchContrasenaUsuario(@RequestBody @Valid UpdateUsuarioContrasenaRequest request){
        try {
            usuarioEntityService.updatePasswordUsuario(
                    request.getId(),
                    request.getOldPassword(),
                    request.getNewPassword()
            );
            return ResponseEntity.ok().build();
        } catch (IncorrectOldPasswordException ex) {
            Map<String,String> body = new HashMap<>();
            body.put("code", "CURRENT_PASSWORD_INVALID");
            body.put("message", ex.getMessage());
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(body);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Object> postUsuarioByAdmin(@RequestBody CreateUsuarioComplete request){
        try {
            usuarioEntityService.createUsuario(request.getNombre(),
                    request.getApellido(),
                    request.getMail(), request.getDni(), request.getPassword(),
                    request.getTelefono(), request.getFechaNacimiento(), request.getBarrioId(), request.getNombreCalle(),
                    request.getNumeroCalle(), request.getManzana(), request.getLote(),
                    -31.693867, -64.403977,
                    request.getTipoUsuarioId(), request.getCuadrillaId());
            return ResponseEntity.accepted().build();
        } catch (DuplicateDataException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "duplicated_data");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("fields", e.getDuplicatedFields());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
