package br.com.sime.api.security.services;

import br.com.sime.api.security.interfaces.EntidadeAutenticavel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;


// Essa classe fornece serviços relacionados ao JWT, como geração e validação de tokens.
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(UserDetails userDetails) {
        String entidade = (userDetails instanceof EntidadeAutenticavel ea)
                ? ea.getEntidade()
                : "USUARIO";

        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // RM ou CNPJ
                .claim("authorities", userDetails.getAuthorities())
                .claim("entidade", entidade) // "ESCOLA" ou "USUARIO"
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 dia
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractEntidade(String token) {
        return extractClaim(token, claims -> claims.get("entidade", String.class));
    }

    public String extractRm(String token) {
        return extractClaim(token, claims -> claims.get("rmUsuario", String.class));
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Claims getClaims(String token) { return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody(); }

    public String getRmFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());
        System.out.println("authentication: " + authentication);
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return null;
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}
