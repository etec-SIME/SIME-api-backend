package br.com.sime.api.repositories;

import br.com.sime.api.entities.chamados.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> { }
