package br.com.sime.api.repositories;

import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TipoEquipamentoRepository extends JpaRepository<TipoEquipamento, Long> {
    boolean existsByIdTipoEquipamentoAndTipoChamadoIdTipoChamado(Long idTipoEquipamento, Long idTipoChamado);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END " +
            "FROM TipoEquipamento t " +
            "JOIN t.ambienteList a " +
            "WHERE t.idTipoEquipamento = :idTipoEquipamento " +
            "AND a.idAmbiente = :idAmbiente")
    boolean existsByTipoEquipamentoAndAmbiente(@Param("idTipoEquipamento") Long idTipoEquipamento,
                                               @Param("idAmbiente") Long idAmbiente);

}
