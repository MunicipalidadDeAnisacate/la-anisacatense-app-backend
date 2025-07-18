package sistemaDeAlumbrado.demo.auth;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistemaDeAlumbrado.demo.auth.requests.*;
import sistemaDeAlumbrado.demo.auth.response.AuthResponse;
import sistemaDeAlumbrado.demo.entities.RefreshToken;
import sistemaDeAlumbrado.demo.exceptions.DuplicateDataException;
import sistemaDeAlumbrado.demo.request.UpdateUsuario;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        try {
            HashMap<String, Object> tokens = authService.login(request.getDni(), request.getPassword());

            return ResponseEntity.ok(new AuthResponse((String) tokens.get("accessToken"),
                                                        (RefreshToken) tokens.get("refreshToken")));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refresh(@RequestBody TokenRefreshRequest request) {
        try {
            HashMap<String, Object> tokens = authService.refreshtoken(request.getRefreshToken());
            return ResponseEntity.ok(new AuthResponse((String) tokens.get("accessToken")));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid refresh token");
        }

    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        try {
            HashMap<String, Object> tokens = authService.register(request.getNombre(),
                    request.getApellido(),
                    request.getMail(),
                    request.getDni(),
                    request.getPassword(),
                    request.getTelefono(),
                    request.getFechaNacimiento(),
                    request.getBarrioId(),
                    request.getNombreCalle(),
                    request.getNumeroCalle(),
                    request.getManzana(),
                    request.getLote(),
                    request.getLatitudeDomicilio(),
                    request.getLongitudeDomicilio());

            return ResponseEntity.ok(new AuthResponse((String) tokens.get("accessToken"),
                    (RefreshToken) tokens.get("refreshToken")));
        }catch (DuplicateDataException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "duplicated_data");
            errorResponse.put("message", e.getMessage());
            errorResponse.put("fields", e.getDuplicatedFields());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/exists")
    public ResponseEntity<Map<String, Boolean>> existeUsuario(
            @RequestParam Optional<String> email,
            @RequestParam Optional<String> telefono,
            @RequestParam Optional<String> dni
    ) {
        boolean exists = false;
        if (email.isPresent()) exists = authService.verifyEmail(email.get());
        if (!exists && telefono.isPresent()) exists = authService.verifyTelefono(telefono.get());
        if (!exists && dni.isPresent()) exists = authService.verifyDni(dni.get());
        return ResponseEntity.ok(Map.of("exists", exists));
    }


    @GetMapping("/mailstelefonos/{dni}")
    public ResponseEntity<Object> getMailTelefonoCifrado(@PathVariable String dni){
        HashMap<String,String> mailTelefono = authService.getMailYTelefonoCifrado(dni);
        return ResponseEntity.ok(UpdateUsuario.from(mailTelefono));
    }


    @PostMapping("/mail/create-reset-token")
    public ResponseEntity<Object> createResetTokenMail(@RequestBody CreateResetTokenRequest request){
        try {
            authService.generateResetPasswordTokenMail(request.getDni());
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/sms/create-reset-token")
    public ResponseEntity<Object> createResetTokenSMS(@RequestBody CreateResetTokenRequest request){
        try {
            authService.generateResetPasswordTokenSMS(request.getDni());
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordRequest request){
        try {
            authService.resetPassword(request.getToken(), request.getNewPassword());
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    // valida si el usuario tiene el refreshToken expirado
    @GetMapping("/heartbeat")
    public ResponseEntity<Object> heartbeat(@RequestParam String refreshToken){
        try {
            if (authService.verifyRefreshToken(refreshToken)) {

                return ResponseEntity.ok("Token is valid");
            } else {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired or invalid");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token validation error");
        }
    }

}
