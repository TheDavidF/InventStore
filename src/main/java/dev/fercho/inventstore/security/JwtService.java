package dev.fercho.inventstore.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;


    private SecretKey getSigningKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generarToken(UserDetails userDetails) {

        Date ahora = new Date();
        Date expiracionToken = new Date(ahora.getTime() + expiration);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(ahora)
                .expiration(expiracionToken)
                .signWith(getSigningKey())
                .compact();
    }

    public String extraerCorreo(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private boolean estaExpirado(String token) {

        Date expiracion = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

        return expiracion.before(new Date());
    }

    public boolean esTokenValido(String token, UserDetails userDetails) {
        String correo = extraerCorreo(token);

        return correo.equals(userDetails.getUsername())
                && !estaExpirado(token)
                && userDetails.isEnabled();
    }
}
