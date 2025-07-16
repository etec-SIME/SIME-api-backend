package br.com.sime.api.security.services;

import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.repositories.UsuarioRepository;
import br.com.sime.api.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Essa classe implementa o UserDetailsService, que é responsável por carregar os detalhes do usuário
@Primary
@Service("usuarioDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String rm) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByRmUsuario(rm)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return new UserDetailsImpl(usuario);
    }
}
