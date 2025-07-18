package sistemaDeAlumbrado.demo.request;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String dni;
    private String password;
}
