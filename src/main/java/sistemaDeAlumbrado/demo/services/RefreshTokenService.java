package sistemaDeAlumbrado.demo.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import sistemaDeAlumbrado.demo.entities.RefreshToken;
import sistemaDeAlumbrado.demo.entities.UsuarioEntity;
import sistemaDeAlumbrado.demo.repositories.RefreshTokenRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private static final Long refreshTokenDurationMs = 2592000000L; // esto es 30 dias

    private final RefreshTokenRepository refreshTokenRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public RefreshToken createRefreshToken(UserDetails user) {
        RefreshToken refreshToken = new RefreshToken();

        this.deleteByUserId((UsuarioEntity) user);

        refreshToken.setUsuario((UsuarioEntity) user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Transactional
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    public boolean verifyToken(String token){
        return this.findByToken(token).isPresent() &&
                this.findByToken(token).get().getExpiryDate().compareTo(Instant.now()) > 0;
    }

    @Transactional
    public void deleteByUserId(UsuarioEntity user) {
        refreshTokenRepository.deleteByUsuario(user);
    }
}
