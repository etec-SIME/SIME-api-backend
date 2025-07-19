package br.com.sime.api.security.config;

import br.com.sime.api.security.config.expression.CustomMethodSecurityExpressionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

// Essa classe ativa a segurança de métodos e proteção de rotas com autenticação JWT apenas no perfil de produção
@Configuration
@Profile("prod")  // ativa essa configuração apenas em produção
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityProdConfig extends GlobalMethodSecurityConfiguration {
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new CustomMethodSecurityExpressionHandler();
    }
}
