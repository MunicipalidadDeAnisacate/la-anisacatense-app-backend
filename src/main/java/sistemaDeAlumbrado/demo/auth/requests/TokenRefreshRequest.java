package sistemaDeAlumbrado.demo.auth.requests;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}
