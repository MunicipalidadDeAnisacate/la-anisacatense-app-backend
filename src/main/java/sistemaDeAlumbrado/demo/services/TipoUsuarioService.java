package sistemaDeAlumbrado.demo.services;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistemaDeAlumbrado.demo.entities.TipoUsuario;
import sistemaDeAlumbrado.demo.repositories.TipoUsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoUsuarioService {

    private final TipoUsuarioRepository tipoUsuarioRepository;

    private Optional<TipoUsuario> findById(Integer id) {
        return tipoUsuarioRepository.findById(id);
    }

    public TipoUsuario findTipoUsuario(Integer id) {
        return this.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo usuario not found"));
    }

    public TipoUsuario findTecnico() {
        return findById(2)
                .orElseThrow( () -> new RuntimeException("tecnico not found"));
    }

    public TipoUsuario findAdmin() {
        return findById(3)
                .orElseThrow( () -> new RuntimeException("administrador not found"));
    }

    public TipoUsuario findVecino() {
        return findById(1)
                .orElseThrow( () -> new RuntimeException("vecino not found"));
    }
}

