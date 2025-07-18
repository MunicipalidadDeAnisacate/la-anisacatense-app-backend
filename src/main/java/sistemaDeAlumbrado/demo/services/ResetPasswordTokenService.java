package sistemaDeAlumbrado.demo.services;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sistemaDeAlumbrado.demo.dtos.MailDto;
import sistemaDeAlumbrado.demo.entities.RefreshToken;
import sistemaDeAlumbrado.demo.entities.ResetPasswordToken;
import sistemaDeAlumbrado.demo.entities.UsuarioEntity;
import sistemaDeAlumbrado.demo.repositories.ResetPasswordTokenRepository;

import java.io.Console;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ResetPasswordTokenService {
    private final MailService mailService;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private static final long resetTokenDurationMs = 600000; // 10 min
    private final Random random = new SecureRandom();
    private final UsuarioEntityService usuarioEntityService;
    private final TwilioService twilioService;

    private String generateToken() {
        int tokenValue = 100000 + random.nextInt(900000); // Token de 6 dígitos
        return String.valueOf(tokenValue);
    }

    @Transactional
    protected ResetPasswordToken saveToken(UsuarioEntity usuario) {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setToken(generateToken());
        resetPasswordToken.setUsuario(usuario);
        resetPasswordToken.setUsed(false);
        resetPasswordToken.setExpiryDate(Instant.now().plusMillis(resetTokenDurationMs)); // Expira en 10 min
        return resetPasswordTokenRepository.save(resetPasswordToken);
    }

    // Enviar el correo
    private void sendResetTokenEmail(String email, String token) throws MessagingException {
        String subject = "Código de recuperación de contraseña";
        mailService.sendResetTokenMail(new MailDto(email, subject, token));
    }

    private void sendResetTokenSMS(String number, String token){
        twilioService.sendResetPasswordMessage(number, token);
    }


    @Transactional
    public void generateResetPasswordTokenMail(UsuarioEntity user) throws MessagingException {
        // Guardar token y enviar correo
        ResetPasswordToken token = saveToken(user);
        sendResetTokenEmail(user.getMail(), token.getToken());
    }


    @Transactional
    public void generateResetPasswordTokenSMS(UsuarioEntity user) {
        ResetPasswordToken token = saveToken(user);
        sendResetTokenSMS(user.getTelefono(), token.getToken());
    }

    // Func. principal del reset token
    @Transactional
    public void resetPassword(String token, String newPasswordEncript){
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        if (resetPasswordToken.getExpiryDate().isBefore(Instant.now()) || resetPasswordToken.getUsed()) {
            throw new RuntimeException("Invalid or expired token");
        }

        this.useToken(resetPasswordToken);

        usuarioEntityService.setNewPassword(resetPasswordToken.getUsuario(),
                newPasswordEncript);
    }

    @Scheduled(cron = "0 0 0 * * *") // Ejecuta a las 00:00 todos los dias
    public void cleanExpiredTokens() {
        List<ResetPasswordToken> expiredTokens = resetPasswordTokenRepository.findByExpiryDateBefore(Instant.now());
        if (!expiredTokens.isEmpty()) {
            resetPasswordTokenRepository.deleteAll(expiredTokens);
            System.out.println("Limpieza de tokens expirados completada: " + expiredTokens.size() + " tokens eliminados.");
        }
    }

    @Transactional
    protected void useToken(ResetPasswordToken resetPasswordToken){
        resetPasswordToken.setUsed(true);
        resetPasswordTokenRepository.save(resetPasswordToken);
    }
}
