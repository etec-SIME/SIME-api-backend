package br.com.sime.api.repositories;

import br.com.sime.api.entities.outros.TipoPerfil;
import br.com.sime.api.entities.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Optional<Usuario> findByRmAndTipoPerfilUsuario(String rmUsuario, TipoPerfil tipoPerfil);
}
