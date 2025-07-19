package br.com.sime.api.security.config;

import br.com.sime.api.handlers.CustomAuthenticationEntryPoint;
import br.com.sime.api.security.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Essa classe configura a segurança da aplicação, definindo filtros, autenticação e autorização globalmente, porém sem a proteção de rotas
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;
    private final UserDetailsService escolaDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfiguration(
            JwtAuthFilter jwtAuthFilter,
            @Qualifier("usuarioDetailsService") UserDetailsService userDetailsService,
            @Qualifier("escolaDetailsService") UserDetailsService escolaDetailsService
    ) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
        this.escolaDetailsService = escolaDetailsService;
    }

    @Profile("dev")
    @Bean
    @Order(1)
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
        System.out.println(">> DEV SecurityFilterChain ativado");
        return http
                .cors(Customizer.withDefaults()) // habilita CORS
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // libera tudo em dev
                )
                .build();
    }

    @Profile("prod")
    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults()) // habilita CORS
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/usuarios/login", "/escolas/login").permitAll()// libera o login
                        .requestMatchers("/**").hasAnyAuthority("ENTIDADE_ESCOLA", "ENTIDADE_USUARIO")
                        .anyRequest().authenticated() // demais rotas requerem autenticação
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationManager(authenticationManager())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(
                usuarioAuthProvider(),
                escolaAuthProvider()
        );
    }

    @Bean
    public DaoAuthenticationProvider usuarioAuthProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public DaoAuthenticationProvider escolaAuthProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(escolaDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
