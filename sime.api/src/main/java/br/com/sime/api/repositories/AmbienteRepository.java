package br.com.sime.api.repositories;

import br.com.sime.api.entities.escola.ambiente.Ambiente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmbienteRepository extends JpaRepository<Ambiente, Long> {
}
