package br.com.sime.api.repositories;

import br.com.sime.api.entities.usuarios.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findByMensagem_Chamado_Id(Long idChamado);
}
