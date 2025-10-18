package br.com.sime.api.repositories;

import br.com.sime.api.entities.chamados.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    List<Chamado> findByStatusAtualGeralChamado(String status);

    @Query("SELECT c FROM Chamado c WHERE c.tipoChamado.departamento.idDepartamento IN :idsDepartamentos")
    List<Chamado> findByDepartamentoIds(@Param("idsDepartamentos") List<Long> idsDepartamentos);

    List<Chamado> findAllByPrioridadeChamado(String prioridade);
    List<Chamado> findAllByPrioridadeChamadoAndStatusAtualGeralChamado(String prioridade, String status);
}
