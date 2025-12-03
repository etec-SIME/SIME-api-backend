package br.com.sime.api.repositories;

import br.com.sime.api.entities.outros.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
}
