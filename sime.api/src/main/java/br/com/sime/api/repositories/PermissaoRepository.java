package br.com.sime.api.repositories;

import br.com.sime.api.entities.usuarios.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    //Permissões de um tipo perfil -
    //
    //List<Permissao> findByidTipoPerfil(Long idTipoPerfil);
}
