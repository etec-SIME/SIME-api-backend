package br.com.sime.api.security.config.expression;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;


//Essa classe é responsável por adicionar métodos personalizados para controlar permissões, por exemplo, método hasPermission(String authority) que retorna true para quem tem a autoridade específica, ou para a lógica de “se for escola, liberar tudo”.

//Ela é o “coração” da lógica que será avaliada nas expressões dentro dos métodos protegidos.
public class CustomSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;

    public CustomSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean hasPermission(String authority) {
        // Se for escola, sempre tem permissão
        if (super.hasAuthority("ENTIDADE_ESCOLA")) {
            return true;
        }

        return super.hasAuthority(authority);
    }

    @Override public void setFilterObject(Object filterObject) { this.filterObject = filterObject; }
    @Override public Object getFilterObject() { return this.filterObject; }
    @Override public void setReturnObject(Object returnObject) { this.returnObject = returnObject; }
    @Override public Object getReturnObject() { return this.returnObject; }
    @Override public Object getThis() { return this; }
}


