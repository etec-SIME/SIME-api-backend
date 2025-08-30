package br.com.sime.api.repositories;

import br.com.sime.api.entities.escola.equipamentos.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    Optional<Equipamento> findByCodEquipamento(String codEquipamento);
}
