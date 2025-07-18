package sistemaDeAlumbrado.demo.services;

import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {
    @Value("${twilio.account.sid}")
    private String ACCOUNT_SID;
    @Value("${twilio.auth.token}")
    private String AUTH_TOKEN;
    @Value("${twilio.phone.number}")
    private String PHONE_NUMBER;


    public void sendResetPasswordMessage(String phoneNumber, String token) {
        this.sendResetPassword(phoneNumber, token);
    }


    @Async
    public CompletableFuture<Void> sendFinishClaimMessageAsync(String vecinoPhoneNumber,
                                                               Long solicitudId,
                                                               String nombreTipoSolicitud,
                                                               String nombreSubTipoSolicitud) {
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message.creator(
                            new com.twilio.type.PhoneNumber(vecinoPhoneNumber),
                            new com.twilio.type.PhoneNumber(PHONE_NUMBER),
                            "Anisacatense App - Su solicitud (Nro: " + solicitudId + ")\n" +
                                    "Tipo: " + nombreTipoSolicitud + " - " + nombreSubTipoSolicitud + "\n" +
                                    "ha sido atendida exitosamente. ¡Gracias por ayudar a mejorar nuestra comunidad! \n"
                                    + "Municipalidad de Anisacate.")
                    .create();
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            System.err.println("Error enviando mensaje a " + vecinoPhoneNumber + ": " + e.getMessage());
            return CompletableFuture.completedFuture(null);
        }
    }



    private void sendResetPassword(String usuarioNumber, String token){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(
                new com.twilio.type.PhoneNumber(usuarioNumber),
                new com.twilio.type.PhoneNumber(PHONE_NUMBER),
                "Anisacatense App - Código de modificación de Contraseña\n" +
                       "Hemos recibido una solicitud para restablecer tu contraseña. " +
                        "Si no solicitaste este cambio, por favor ignora este mensaje.\n" +
                        "Tu código de recuperación es:\n"
                        + token + "\n" +
                        "Usa este código para restablecer tu contraseña.\n" +
                        "¡Gracias!")
        .create();
    }

}