package br.com.sime.api.repositories;

import br.com.sime.api.entities.usuarios.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
}
