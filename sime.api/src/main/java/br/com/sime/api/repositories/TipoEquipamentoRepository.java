package br.com.sime.api.repositories;

import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoEquipamentoRepository extends JpaRepository<TipoEquipamento, Long> {
}
