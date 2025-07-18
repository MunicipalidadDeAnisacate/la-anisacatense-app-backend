package sistemaDeAlumbrado.demo.auth;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sistemaDeAlumbrado.demo.auth.jwt.JwtService;
import sistemaDeAlumbrado.demo.dtos.MailDto;
import sistemaDeAlumbrado.demo.entities.RefreshToken;
import sistemaDeAlumbrado.demo.entities.UsuarioEntity;
import sistemaDeAlumbrado.demo.services.MailService;
import sistemaDeAlumbrado.demo.services.RefreshTokenService;
import sistemaDeAlumbrado.demo.services.ResetPasswordTokenService;
import sistemaDeAlumbrado.demo.services.UsuarioEntityService;

import java.time.LocalDate;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioEntityService usuarioEntityService;
    private final RefreshTokenService refreshTokenService;
    private final ResetPasswordTokenService resetPasswordTokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MailService mailService;


    public HashMap<String, Object> login(final String dni,
                                 final String password) {
        HashMap<String,Object> result = new HashMap<>();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dni, password));

        UserDetails user = usuarioEntityService.loadUserByUsername(dni);

        result.put("accessToken", jwtService.getToken(user));
        result.put("refreshToken", refreshTokenService.createRefreshToken(user));

        return result;
    }


    public HashMap<String, Object> refreshtoken(String requestRefreshToken) {
        HashMap<String, Object> result = new HashMap<>();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUsuario)
                .map(user -> {
                    result.put("accessToken", jwtService.getToken(user));
                    return result;
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }


    public HashMap<String, Object> register(final String nombre,
                                            final String apellido,
                                            final String mail,
                                            final String dni,
                                            final String password,
                                            final String telefono,
                                            final LocalDate fechaNacimiento,
                                            final Integer barrioId,
                                            final String nombreCalle,
                                            final String numeroCalle,
                                            final String manzana,
                                            final String lote,
                                            final Double latitudeDomicilio,
                                            final Double longitudeDomicilio) throws MessagingException {

        HashMap<String, Object> result = new HashMap<>();

        UsuarioEntity nuevoUsuario = usuarioEntityService.createUsuario(
                nombre,
                apellido,
                mail,
                dni,
                password,
                telefono,
                fechaNacimiento,
                barrioId,
                nombreCalle,
                numeroCalle,
                manzana,
                lote,
                latitudeDomicilio,
                longitudeDomicilio,
                null,
                null
        );

        result.put("accessToken", jwtService.getToken(nuevoUsuario));
        result.put("refreshToken", refreshTokenService.createRefreshToken(nuevoUsuario));

        mailService.sendWelcomeMail(new MailDto(nuevoUsuario.getMail(), "Bienvenido a La Anisacatense", ""));

        return result;
    }


    public void generateResetPasswordTokenMail(String dni) throws MessagingException {
        UserDetails user = usuarioEntityService.loadUserByUsername(dni);
        resetPasswordTokenService.generateResetPasswordTokenMail((UsuarioEntity) user);
    }

    public void generateResetPasswordTokenSMS(String dni){
        UserDetails user = usuarioEntityService.loadUserByUsername(dni);
        resetPasswordTokenService.generateResetPasswordTokenSMS((UsuarioEntity) user);
    }

    public void resetPassword(String token, String newPassword) {
        String newPasswordEncrypt = passwordEncoder.encode(newPassword);
        resetPasswordTokenService.resetPassword(token, newPasswordEncrypt);
    }

    public HashMap<String, String> getMailYTelefonoCifrado(String dni){
        return usuarioEntityService.getMailYTelefonoCifrado(dni);
    }

    public boolean verifyRefreshToken(String token){
        return refreshTokenService.verifyToken(token);
    }

    public boolean verifyTelefono(String telefono){
        return usuarioEntityService.verifyTelefono(telefono);
    }

    public boolean verifyEmail(String email){
        return usuarioEntityService.verifyMail(email);
    }

    public boolean verifyDni(String dni){
        return usuarioEntityService.verifyDni(dni);
    }
}
