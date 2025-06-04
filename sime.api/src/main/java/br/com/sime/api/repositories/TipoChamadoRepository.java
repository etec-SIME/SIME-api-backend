package br.com.sime.api.repositories;

import br.com.sime.api.entities.chamados.TipoChamado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoChamadoRepository extends JpaRepository<TipoChamado, Long> {
    Optional<TipoChamado> findByNomeTipoChamadoIgnoreCase(String nomeTipoChamado);
}
