package br.com.sime.api.repositories;

import br.com.sime.api.entities.escola.ambiente.TipoAmbiente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoAmbienteRepository extends JpaRepository<TipoAmbiente, Long> {
}
