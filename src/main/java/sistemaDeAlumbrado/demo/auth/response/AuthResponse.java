package sistemaDeAlumbrado.demo.auth.response;

import lombok.Data;
import sistemaDeAlumbrado.demo.entities.RefreshToken;

import java.time.Instant;


@Data
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private Instant expiryDate;

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public AuthResponse(String accessToken, RefreshToken refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken.getToken();
        this.expiryDate = refreshToken.getExpiryDate();
    }
}
