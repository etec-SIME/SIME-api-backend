package br.com.sime.api.repositories;

import br.com.sime.api.entities.escola.Escola;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EscolaRepository extends JpaRepository<Escola, String> {
    Optional<Escola> findByCodEscola(String codEscola);
}
