package br.com.sime.api.security.filter;

import br.com.sime.api.security.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Essa classe é um filtro de autenticação JWT que intercepta as requisições HTTP. Resumidamente,
// ela verifica se o token JWT está presente no cabeçalho da requisição, valida o token e, se for válido,
// autentica o usuário no contexto de segurança do Spring Security.
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserDetailsService escolaDetailsService;

    public JwtAuthFilter(
            JwtService jwtService,
            @Qualifier("usuarioDetailsService") UserDetailsService userDetailsService,
            @Qualifier("escolaDetailsService") UserDetailsService escolaDetailsService
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.escolaDetailsService = escolaDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String username = jwtService.extractUsername(jwt); // ou RM ou CNPJ
        final String entidade = jwtService.extractEntidade(jwt); // "ESCOLA" ou "USUARIO"

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println(">>> jwt: " + jwt);
            System.out.println(">>> entidade extraída: " + entidade);
            System.out.println(">>> username extraído: " + username);

            UserDetails userDetails = "ESCOLA".equalsIgnoreCase(entidade) ?
                    escolaDetailsService.loadUserByUsername(username) :
                    userDetailsService.loadUserByUsername(username);

            System.out.println(">>> userDetails class: " + userDetails.getClass().getName());
            System.out.println("Authorities escola: " + userDetails.getAuthorities());

            if (!jwtService.isTokenValid(jwt, userDetails)) {
                throw new BadCredentialsException("Token JWT inválido ou expirado");
            }

            List<GrantedAuthority> authorities = new ArrayList<>(userDetails.getAuthorities());
            System.out.println(">>> entidade no filtro: " + entidade);

            authorities.add(new SimpleGrantedAuthority("ENTIDADE_" + entidade.toUpperCase())); // Adiciona autoridade personalizada

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    authorities
            );
            System.out.println(">>> authToken principal: " + authToken.getPrincipal().getClass().getName());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            System.out.println(">>> context principal: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().getName());

            System.out.println(">>> Authorities finais:");
            for (GrantedAuthority authority : authorities) {
                System.out.println(" - " + authority.getAuthority());
            }
        }

        filterChain.doFilter(request, response);
    }
}
