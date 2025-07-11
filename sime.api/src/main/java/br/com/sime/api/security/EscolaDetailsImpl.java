package br.com.sime.api.security;

import br.com.sime.api.entities.escola.Escola;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class EscolaDetailsImpl implements UserDetails {

    private final Escola escola;

    public EscolaDetailsImpl(Escola escola) {
        this.escola = escola;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ESCOLA"));
    }

    @Override
    public String getPassword() { return escola.getSenhaEscola(); }
    @Override
    public String getUsername() { return escola.getSenhaEscola(); }

    @Override public boolean isAccountNonExpired()     { return true; }
    @Override public boolean isAccountNonLocked()      { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()               { return true; }
}
