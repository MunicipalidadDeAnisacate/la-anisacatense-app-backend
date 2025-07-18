package sistemaDeAlumbrado.demo.auth.requests;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String token;
    private String newPassword;
}
