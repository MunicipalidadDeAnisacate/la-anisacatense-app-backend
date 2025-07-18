package sistemaDeAlumbrado.demo.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sistemaDeAlumbrado.demo.entities.*;
import sistemaDeAlumbrado.demo.exceptions.DuplicateDataException;
import sistemaDeAlumbrado.demo.exceptions.IncorrectOldPasswordException;
import sistemaDeAlumbrado.demo.repositories.UsuarioEntityRepository;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UsuarioEntityService implements UserDetailsService {
    private final UsuarioEntityRepository usuarioEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final BarrioService barrioService;
    private final TipoUsuarioService tipoUsuarioService;
    private final CuadrillaService cuadrillaService;


    public UsuarioEntity findById(Long usuarioId){
        return usuarioEntityRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("usuario no encontrado"));
    }

    public UsuarioEntity getReferenceById(Long id){
        return usuarioEntityRepository.getReferenceById(id);
    }


    public UsuarioEntity findByDni(final String dni){
        return usuarioEntityRepository.findByDni(dni).orElse(null);
    }


    public List<UsuarioEntity> findAllTecnicos(){
        return usuarioEntityRepository.findAllTecnicos();
    }


    public boolean esVecino(UsuarioEntity usuario){return usuario.getTipoUsuario().getId().equals(1);}


    public boolean esTecnico(UsuarioEntity usuario){return usuario.getTipoUsuario().getId().equals(2);}


    public boolean esAdmin(UsuarioEntity usuario){return usuario.getTipoUsuario().getId().equals(3);}


    public UsuarioEntity getTecnicoOrNull(Long tecnicoId){
        if (tecnicoId != null){
            UsuarioEntity tecnico = this.findById(tecnicoId);
            if (this.esTecnico(tecnico)){
                return tecnico;
            }
        }
        return null;
    }


    public Page<UsuarioEntity> findAllUsuariosByRol(Integer tipoUsuarioId, Pageable pageable){
        return usuarioEntityRepository.findAllUsuariosByRol(tipoUsuarioId, pageable);
    }


    public List<UsuarioEntity> findTecnicos(final Long tecnicoId,
                                            final Integer cuadrillaId) {
        Set<UsuarioEntity> tecnicos = usuarioEntityRepository.getUsuarioEntitiesByTipoUsuario2(tecnicoId, cuadrillaId);
        return tecnicos.stream().toList();
    }


    public boolean verifyDni(String dni){
        return usuarioEntityRepository.existsByDni(dni);
    }


    public boolean verifyMail(String email){
        return usuarioEntityRepository.existsByMail(email);
    }

    public boolean verifyTelefono(String telefono){
        return usuarioEntityRepository.existsByTelefono(this.formatTelefono(telefono));
    }


    private void getdDuplicateFieldsExceptions(String dni, String mail, String telefono){
        List<String> duplicatedFields = new ArrayList<>();

        if (verifyDni(dni)) {
            duplicatedFields.add("Nro. de DNI");
        }
        if (verifyMail(mail)) {
            duplicatedFields.add("Mail");
        }
        if (verifyTelefono(this.formatTelefono(telefono))) {
            duplicatedFields.add("Nro. Celular");
        }

        if (!duplicatedFields.isEmpty()) {
            throw new DuplicateDataException(duplicatedFields);
        }
    }


    private String updateMailUser(String newMail, String oldMail){
        if (newMail == null || newMail.trim().equals("")){
            return oldMail;
        }

        String newMailFormatted = newMail.trim().toLowerCase();

        if (newMailFormatted.equals(oldMail)){
            return oldMail;
        }

        if (verifyMail(newMailFormatted)){
            List<String> duplicated = new ArrayList<>();
            duplicated.add("Mail");
            throw new DuplicateDataException(duplicated);
        }

        return newMailFormatted;
    }

    private String updateTelefonoUser(String newTelefono, String oldTelefono){
        if (newTelefono == null || newTelefono.trim().equals("")){
            return oldTelefono;
        }

        String newTelefonoFormatted = this.formatTelefono(newTelefono.trim());

        if (newTelefonoFormatted.equals(oldTelefono)){
            return oldTelefono;
        }

        if (verifyTelefono(newTelefonoFormatted)){
            List<String> duplicated = new ArrayList<>();
            duplicated.add("Nro. Celular");
            throw new DuplicateDataException(duplicated);
        }

        return newTelefonoFormatted;
    }

    private String updateDniUser(String newDni, String oldDni){
        if (newDni == null || newDni.trim().equals("")){
            return oldDni;
        }

        String newDniFormatted = newDni.trim();

        if (newDniFormatted.equals(oldDni)){
            return oldDni;
        }

        if (verifyDni(newDniFormatted)){
            List<String> duplicated = new ArrayList<>();
            duplicated.add("Nro. de DNI");
            throw new DuplicateDataException(duplicated);
        }

        return newDniFormatted;
    }


    @Transactional
    public UsuarioEntity createUsuario(final String nombre, final String apellido,
                                       final String mail, final String dni, final String password,
                                       final String telefono, final LocalDate fechaNacimiento,
                                       final Integer barrioId,
                                       final String nombreCalle, final String numeroCalle,
                                       final String manzana, final String lote,
                                       final Double latitudeDomicilio, final Double longitudeDomicilio,
                                       final Integer tipoUsuarioId, final Integer cuadrillaId) {

        this.getdDuplicateFieldsExceptions(dni, mail, telefono);

        TipoUsuario tipoUsuario = tipoUsuarioService.findVecino();

        if (tipoUsuarioId != null){
            tipoUsuario = tipoUsuarioService.findTipoUsuario(tipoUsuarioId);
        }

        Cuadrilla cuadrilla = null;
        if (tipoUsuario.getNombreTipo().equals("tecnico")){
            cuadrilla = cuadrillaService.findCuadrilla(cuadrillaId);
        }

        // validacion para asegurarse de que al menos uno de los pares esté completo
        boolean calleCompleta = nombreCalle != null && !nombreCalle.isEmpty() && numeroCalle != null && !numeroCalle.isEmpty();
        boolean manzanaCompleta = manzana != null && !manzana.isEmpty() && lote != null && !lote.isEmpty();

        if (!calleCompleta && !manzanaCompleta) {
            throw new RuntimeException("Debe proporcionar un par de valores válidos para dirección (calle o manzana y lote).");
        }

        // validacion para asegurarse de que no haya pares parcialmente completos
        if ((nombreCalle != null && !nombreCalle.isEmpty() && (numeroCalle == null || numeroCalle.isEmpty())) ||
                (numeroCalle != null && !numeroCalle.isEmpty() && (nombreCalle == null || nombreCalle.isEmpty())) ||
                (manzana != null && !manzana.isEmpty() && (lote == null || lote.isEmpty())) ||
                (lote != null && !lote.isEmpty() && (manzana == null || manzana.isEmpty()))) {
            throw new RuntimeException("Debe proporcionar ambos valores de calle y número, o ambos valores de manzana y lote.");
        }

        Barrio barrio = barrioService.findBarrioById(barrioId);

        String passwordEncode = passwordEncoder.encode(password);

        UsuarioEntity usuario = new UsuarioEntity(
                nombre,
                apellido,
                mail.trim().toLowerCase(),
                dni,
                passwordEncode,
                tipoUsuario,
                this.formatTelefono(telefono),
                fechaNacimiento,
                barrio,
                nombreCalle,
                numeroCalle,
                manzana,
                lote,
                latitudeDomicilio,
                longitudeDomicilio,
                cuadrilla);

        return usuarioEntityRepository.save(usuario);
    }


    @Transactional
    public void updateUsuarioByAdmin(final Long id,
                              final String newDni,
                              final String newPassword,
                              final String newNombre,
                              final String newApellido,
                              final Integer tipoUsuarioId,
                              final Integer cuadrillaId,
                              final String newMail,
                              final String newTelefono,
                              final LocalDate newFechaNacimiento,
                              final Integer barrioId,
                              final String newNombreCalle,
                              final String newNumeroCalle,
                              final String newManzana,
                              final String newLote){

        UsuarioEntity usuario = this.findById(id);

        usuario.setDni(this.updateDniUser(newDni, usuario.getDni()));
        usuario.setPassword(newPassword != null ? passwordEncoder.encode(newPassword) : usuario.getPassword());
        usuario.setNombre(newNombre != null ? newNombre : usuario.getNombre());
        usuario.setApellido(newApellido != null ? newApellido : usuario.getApellido());
        usuario.setMail(this.updateMailUser(newMail, usuario.getMail()));
        usuario.setTelefono(this.updateTelefonoUser(newTelefono, usuario.getTelefono()));
        usuario.setFechaNacimiento(newFechaNacimiento != null ? newFechaNacimiento : usuario.getFechaNacimiento());
        if (barrioId != null){
            usuario.setBarrio(barrioService.findBarrioById(barrioId));
        }
        usuario.setNombreCalle(newNombreCalle != null ? newNombreCalle : usuario.getNombreCalle());
        usuario.setNumeroCalle(newNumeroCalle != null ? newNumeroCalle : usuario.getNumeroCalle());
        usuario.setManzana(newManzana != null ? newManzana : usuario.getManzana());
        usuario.setLote(newLote != null ? newLote : usuario.getLote());

        if (tipoUsuarioId != null || cuadrillaId != null){
            usuario = this.changeRolUsuario(usuario, tipoUsuarioId, cuadrillaId);
        }

        usuarioEntityRepository.save(usuario);
    }


    @Transactional
    public void updatePerfilUsuario(final Long id,
                              final String newNombre,
                              final String newApellido,
                              final String newMail,
                              final String newTelefono,
                              final LocalDate newFechaNacimiento){

        UsuarioEntity usuario = this.findById(id);

        usuario.setNombre(newNombre != null ? newNombre : usuario.getNombre());

        usuario.setApellido(newApellido != null ? newApellido : usuario.getApellido());

        usuario.setMail(this.updateMailUser(newMail, usuario.getMail()));

        usuario.setTelefono(this.updateTelefonoUser(newTelefono, usuario.getTelefono()));

        usuario.setFechaNacimiento(newFechaNacimiento != null ? newFechaNacimiento : usuario.getFechaNacimiento());

        usuarioEntityRepository.save(usuario);
    }


    @Transactional
    public void updatePasswordUsuario(final Long id,
                                      final String oldPassword,
                                      final String newPassword){

        if (newPassword.isBlank() || newPassword.isEmpty()){
            throw new IllegalArgumentException("New password cannot be blank or empty");
        }

        if (oldPassword == null || oldPassword.isBlank()) {

            throw new IllegalArgumentException("Old password cannot be blank");
        }

        UsuarioEntity usuario = this.findById(id);

        if (!passwordEncoder.matches(oldPassword, usuario.getPassword())){
            throw new IncorrectOldPasswordException();
        }

        String newPasswordEncode = passwordEncoder.encode(newPassword);
        usuario.setPassword(newPasswordEncode);
    }


    @Transactional
    public void updateDomicilioUsuario(final Long id,
                                       final Integer barrioId,
                                       final String newCalle,
                                       final String newNumeroCalle,
                                       final String newManzana,
                                       final String newLote,
                                       final Double newLatitudDomicilio,
                                       final Double newLongitudDomicilio){

        boolean calleNumValid = newCalle != null && newNumeroCalle != null;
        boolean mzLtValid = newManzana != null && newLote != null;

        if (!calleNumValid && !mzLtValid) {
            throw new RuntimeException("Domicilio inválido: debes completar calle+numero o manzana+lote.");
        }

        UsuarioEntity usuario = this.findById(id);

        usuario.setBarrio(barrioService.findBarrioById(barrioId));
        usuario.setNombreCalle(newCalle);
        usuario.setNumeroCalle(newNumeroCalle);
        usuario.setManzana(newManzana);
        usuario.setLote(newLote);
        usuario.setLatitudeDomicilio(newLatitudDomicilio);
        usuario.setLongitudeDomicilio(newLongitudDomicilio);

        usuarioEntityRepository.save(usuario);
    }


    public HashMap<String, String> getMailYTelefonoCifrado(String dni){
        UsuarioEntity usuario = usuarioEntityRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("usuario no encontrado"));

        HashMap<String,String> mailYnombre = new HashMap<>();

        String mailEnmascarado = this.enmascararMail(usuario.getMail());
        String telefonoEnmascarado = this.enmascararTelefono(usuario.getTelefono());

        mailYnombre.put("mail", mailEnmascarado);
        mailYnombre.put("telefono", telefonoEnmascarado);

        return mailYnombre;
    }


    private String enmascararMail(String mail){

        String[] partes = mail.split("@");

        String local = partes[0];

        String mailOculto = "";
        if (local.length() > 3){
            mailOculto = "*".repeat(local.length() - 3) + local.substring(local.length() - 3);
        } else {
            mailOculto = local;
        }

        return mailOculto + "@" + partes[1];
    }


    private String enmascararTelefono(String telefono) {
        String ultimos3 =  telefono.length() >= 3 ? telefono.substring(telefono.length() - 3) : telefono;
        return "*******" + ultimos3;
    }


    @Transactional
    public void setNewMailTelefono(Long idUsuario, String newMail, String newTelefono){
        UsuarioEntity usuario = usuarioEntityRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("usuario no encontrado"));

        if (newMail.isBlank() || newTelefono.isBlank()){
            throw new RuntimeException("usuario no encontrado");
        }

        if (!usuario.getMail().equals(newMail)){
            usuario.setMail(newMail);
        }

        if (!usuario.getTelefono().equals(newTelefono)){
            usuario.setTelefono(newTelefono);
        }

        usuarioEntityRepository.save(usuario);
    }


    @Override
    public UserDetails loadUserByUsername(String dni){
        return usuarioEntityRepository.findByDni(dni)
                .orElseThrow(()-> new UsernameNotFoundException("user not found"));
    }


    @Transactional
    public void setNewPassword(UsuarioEntity user, String newPassword){
        user.setPassword(newPassword);
        usuarioEntityRepository.save(user);
    }


    // funcion para cambiar de rol, la activa el metodo update usuario
    @Transactional
    protected UsuarioEntity changeRolUsuario(final UsuarioEntity usuario,
                                final Integer newTipoUsuarioId,
                                final Integer cuadrillaId){

        // En caso de que sea un cambio de cuadrilla
        if ( newTipoUsuarioId == null && this.esTecnico(usuario) && cuadrillaId != null ){
            Cuadrilla cuadrillaNueva = cuadrillaService.findCuadrilla(cuadrillaId);
            usuario.setCuadrilla(cuadrillaNueva);
            return usuario;
        }


        if (newTipoUsuarioId != null) {
            TipoUsuario tipoTecnico = tipoUsuarioService.findTecnico();
            TipoUsuario tipoUsuarioNuevo = tipoUsuarioService.findTipoUsuario(newTipoUsuarioId);

            // En caso de que se cambio de (admin o vecino) a tecnico 
            if (tipoUsuarioNuevo.equals(tipoTecnico) && cuadrillaId != null) {
                Cuadrilla cuadrilla = cuadrillaService.findCuadrilla(cuadrillaId);

                usuario.setTipoUsuario(tipoUsuarioNuevo);
                usuario.setCuadrilla(cuadrilla);

                return usuario;
            }

            // En caso de que el cambio sea de (tecnico o vecino) a admin
            TipoUsuario tipoAdmin = tipoUsuarioService.findAdmin();
            if (tipoUsuarioNuevo.equals(tipoAdmin)) {
                usuario.setTipoUsuario(tipoUsuarioNuevo);
                usuario.setCuadrilla(null);
                return usuario;
            }

            // En caso de que sea de (tecnico o admin) a vecino
            TipoUsuario tipoVecino = tipoUsuarioService.findVecino();
            if (tipoUsuarioNuevo.equals(tipoVecino)) {
                usuario.setTipoUsuario(tipoUsuarioNuevo);
                usuario.setCuadrilla(null);
                return usuario;
            }
        }

        throw new RuntimeException("cambio de rol failed");
    }


    @Transactional
    public void deleteUserFisico(final Long usuarioEliminadorId,
                                 final Long usuarioEliminadoId) {

        UsuarioEntity eliminador = this.findById(usuarioEliminadorId);

        if (!this.esAdmin(eliminador)) {
            throw new RuntimeException("El usuario que quiere eliminar no es admin.");
        }

        UsuarioEntity usuarioEliminado = this.findById(usuarioEliminadoId);

        if (this.hayProyectosDeUsuarioEnConsultasCiudadanas(usuarioEliminado)) {
            throw new RuntimeException(
                    "No se puede eliminar: tiene al menos un proyecto en consulta ciudadana."
            );
        }

        usuarioEntityRepository.deletePreferenciasDeUsuario(usuarioEliminado.getId());
        usuarioEntityRepository.deleteProyectosUsuario(usuarioEliminado.getId());
        usuarioEntityRepository.deleteResetPasswordTokenByUsuarioId(usuarioEliminado.getId());
        usuarioEntityRepository.deleteRefreshTokenByUsuarioId(usuarioEliminado.getId());
        List<Object[]> reclamosIdsSQL = usuarioEntityRepository.reclamosIdsRealizadosPorUsuario(usuarioEliminado.getId());
        List<Long> reclamosIds = this.castReclamosIds(reclamosIdsSQL);
        usuarioEntityRepository.deleteReclamoXUsuarioByUsuarioId(usuarioEliminado.getId());
        usuarioEntityRepository.deleteReclamosXTipoDeReparacion(reclamosIds);
        usuarioEntityRepository.deleteReclamosDeUsuario(reclamosIds);
        usuarioEntityRepository.deleteArregloByTecnicoId(usuarioEliminado.getId());
        usuarioEntityRepository.deleteReclamoByTecnicoId(usuarioEliminado.getId());
        usuarioEntityRepository.delete(usuarioEliminado);
    }


    private boolean hayProyectosDeUsuarioEnConsultasCiudadanas(UsuarioEntity usuario){
        List<Proyecto> proyectosDeUsr = usuarioEntityRepository.findProyectosByUsuario(usuario.getId());

        for (Proyecto proyecto : proyectosDeUsr){
            boolean existeConsultaDeProyecto = usuarioEntityRepository.existeConsultaCiudadanaDeProyecto(proyecto.getId());
            if (existeConsultaDeProyecto){
                return true;
            }
        }

        return false;
    }


    private List<Long> castReclamosIds(List<Object[]> reclamosIdsSQL){
        List<Long> reclamosIds = new ArrayList<>();

        for (Object[] reclamosId : reclamosIdsSQL){
            reclamosIds.add(((Number) reclamosId[0]).longValue());
        }

        return reclamosIds;
    }

    private String formatTelefono(String telefono){
        if (telefono.startsWith("+54")){
            return telefono;
        }
        return "+54"+telefono;
    }
}

