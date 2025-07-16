package br.com.sime.api.repositories;

import br.com.sime.api.entities.escola.Escola;
import br.com.sime.api.entities.usuarios.Permissao;
import br.com.sime.api.entities.usuarios.TipoPerfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipoPerfilRepository extends JpaRepository<TipoPerfil, Long> {
}
