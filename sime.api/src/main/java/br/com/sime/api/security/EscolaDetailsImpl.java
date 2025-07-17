package br.com.sime.api.security;

import br.com.sime.api.entities.escola.Escola;
import br.com.sime.api.security.config.auth.EntidadeAutenticavel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class EscolaDetailsImpl implements UserDetails, EntidadeAutenticavel {

    private final Escola escola;
    private final String entidade;

    public EscolaDetailsImpl(Escola escola, String entidade) {
        this.escola = escola;
        this.entidade = entidade;
    }

    @Override
    public String getEntidade() {
        return "ESCOLA";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ESCOLA"));
    }

    @Override
    public String getPassword() { return escola.getSenhaEscola(); }
    @Override
    public String getUsername() { return escola.getCnpjEscola(); }

    @Override public boolean isAccountNonExpired()     { return true; }
    @Override public boolean isAccountNonLocked()      { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()               { return true; }
}
