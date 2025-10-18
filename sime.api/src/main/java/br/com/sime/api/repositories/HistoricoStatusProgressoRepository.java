package br.com.sime.api.repositories;

import br.com.sime.api.entities.chamados.historicos.HistoricoStatusProgresso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoStatusProgressoRepository extends JpaRepository<HistoricoStatusProgresso, Long> { }
