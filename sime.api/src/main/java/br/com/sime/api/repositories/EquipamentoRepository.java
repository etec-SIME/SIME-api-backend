package br.com.sime.api.repositories;

import br.com.sime.api.entities.escola.Ambiente;
import br.com.sime.api.entities.escola.equipamentos.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
}
