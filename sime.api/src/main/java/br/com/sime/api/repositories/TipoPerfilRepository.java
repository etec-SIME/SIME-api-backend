package br.com.sime.api.repositories;

import br.com.sime.api.entities.outros.TipoPerfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoPerfilRepository extends JpaRepository<TipoPerfil, Long> { }
