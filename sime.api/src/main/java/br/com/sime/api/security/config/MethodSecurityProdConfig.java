package br.com.sime.api.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

// Essa classe ativa a segurança de métodos e proteção de rotas com autenticação JWT apenas no perfil de produção
@Configuration
@Profile("prod")  // ativa essa configuração apenas em produção
@EnableMethodSecurity  // permite usar @PreAuthorize
public class MethodSecurityProdConfig { }
