package sistemaDeAlumbrado.demo.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import sistemaDeAlumbrado.demo.entities.UsuarioEntity;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    public String getToken(UserDetails user){
        return getToken(new HashMap<String, Object>(), user);
    }

    private String getToken(HashMap<String, Object> extraClaims, UserDetails user){
        var claims = new HashMap<String, Object>();

        if (user instanceof UsuarioEntity) {
            UsuarioEntity usuario = (UsuarioEntity) user;
            claims.put("rol", usuario.getTipoUsuario().getNombreTipo());
            if (usuario.getTipoUsuario().getNombreTipo().equals("tecnico")) {
                claims.put("cuadrilla", usuario.getCuadrilla() == null ? null : usuario.getCuadrilla().getId());
            }
            claims.put("nombre", usuario.getNombre());
            claims.put("apellido", usuario.getApellido());
            claims.put("id", usuario.getId());
        } else {
            claims.put("rol", "sin rol");
        }

        return Jwts
                .builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*20))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes=Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();

    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token)
    {
        return getExpiration(token).before(new Date());
    }
}
