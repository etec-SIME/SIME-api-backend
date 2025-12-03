package br.com.sime.api.repositories;

import br.com.sime.api.entities.escola.equipamentos.Equipamento;
import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface TipoEquipamentoRepository extends JpaRepository<TipoEquipamento, Long> { }
