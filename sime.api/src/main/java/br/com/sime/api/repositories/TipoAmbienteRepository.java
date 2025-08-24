package br.com.sime.api.repositories;

import br.com.sime.api.entities.escola.ambiente.Tipo_Ambiente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoAmbienteRepository extends JpaRepository<Tipo_Ambiente, Long> {
}
