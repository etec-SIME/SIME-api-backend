package br.com.sime.api.repositories;

import br.com.sime.api.entities.usuarios.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
}
