package sistemaDeAlumbrado.demo.request;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class UpdateUsuario {
    private String mail;
    private String telefono;

    public static UpdateUsuario from(HashMap<String, String> hashMap){
        return UpdateUsuario.builder()
                .mail(hashMap.get("mail"))
                .telefono(hashMap.get("telefono"))
                .build();
    }
}
