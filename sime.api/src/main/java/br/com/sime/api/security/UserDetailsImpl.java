package br.com.sime.api.security;

import br.com.sime.api.entities.usuarios.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/* Essa classe implementa UserDetails para fornecer informações do usuário autenticado, a utilizada pelo Spring Security.
Ela relaciona as permissões do usuário da tabela Permissao com as autoridades do Spring Security, permitindo que o sistema controle
o acesso a recursos com base nas permissões definidas no TipoPerfil.*/

@Getter
public class UserDetailsImpl implements UserDetails {
    private final Usuario usuario;

    public UserDetailsImpl(Usuario usuario, String s) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usuario.getTipoPerfil().getPermissaoList().stream()
                .map(permissao -> new SimpleGrantedAuthority(permissao.getNomePermissao()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() { return usuario.getSenhaUsuario(); }
    @Override
    public String getUsername() { return usuario.getRmUsuario(); }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
