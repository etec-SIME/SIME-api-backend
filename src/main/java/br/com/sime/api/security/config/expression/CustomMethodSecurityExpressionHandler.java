package br.com.sime.api.security.config.expression;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

// Essa classe substitui o handler padrão do Spring, para que o framework saiba que deve usar a classe personalizada (CustomSecurityExpressionRoot) em vez do padrão.

// Sem essa classe, o Spring sempre usaria o handler padrão e a expressão personalizada (hasPermission) nunca existiria no contexto das anotações @PreAuthorize.
public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {
    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
            Authentication authentication,
            MethodInvocation invocation) {

        CustomSecurityExpressionRoot root = new CustomSecurityExpressionRoot(authentication);
        root.setPermissionEvaluator(getPermissionEvaluator());
        return root;
    }
}
