package sistemaDeAlumbrado.demo.auth.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String dni;
    private String password;
}
