package br.com.sime.api.repositories;

import br.com.sime.api.entities.usuarios.TipoPerfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoPerfilRepository extends JpaRepository<TipoPerfil, Long> { }
