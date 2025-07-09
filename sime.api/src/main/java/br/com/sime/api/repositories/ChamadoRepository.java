package br.com.sime.api.repositories;

import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.enums.StatusChamadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    List<Chamado> findByStatusChamado(StatusChamadoEnum status);
}
