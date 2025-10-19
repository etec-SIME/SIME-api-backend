package br.com.sime.api.repositories;

import br.com.sime.api.entities.escola.equipamentos.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    @Query("SELECT e FROM Equipamento e LEFT JOIN FETCH e.tipoEquipamento WHERE e.codEquipamento = :cod")
    Optional<Equipamento> findByCodEquipamentoWithTipoEquipamento(@Param("cod") String cod);

    List<Equipamento> findAllByCodEquipamentoIn(List<String> codigos);
}
