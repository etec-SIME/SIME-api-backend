package br.com.sime.api.repositories;

import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TipoEquipamentoRepository extends JpaRepository<TipoEquipamento, Long> { }
