package br.com.sime.api.repositories;

import br.com.sime.api.DTOs.ChamadoCardDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.enums.StatusChamadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    List<Chamado> findByStatusChamado(String status);

    @Query("SELECT c FROM Chamado c WHERE c.tipoChamado.departamento.idDepartamento IN :idsDepartamentos")
    List<Chamado> findByDepartamentoIds(@Param("idsDepartamentos") List<Long> idsDepartamentos);

    List<Chamado> findAllByPrioridadeChamado(String prioridade);
}
