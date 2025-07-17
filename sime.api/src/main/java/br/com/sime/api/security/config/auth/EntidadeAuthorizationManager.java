package br.com.sime.api.security.config.auth;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

// Essa classe é responsável por verificar se o usuário autenticado é uma escola para autorização de acesso total
@Component
public class EntidadeAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier,
                                       RequestAuthorizationContext context) {

        Authentication auth = authenticationSupplier.get();

        System.out.println(">> AUTH CLASS: " + (auth != null ? auth.getPrincipal().getClass() : "null"));

        if (auth == null || !auth.isAuthenticated())
            return new AuthorizationDecision(false);

        Object principal = auth.getPrincipal();

        if (principal instanceof EntidadeAutenticavel ea) {
            System.out.println(">> ENTIDADE: " + ea.getEntidade());
            return new AuthorizationDecision("ESCOLA".equalsIgnoreCase(ea.getEntidade()));
        }

        return new AuthorizationDecision(false);
    }
}
