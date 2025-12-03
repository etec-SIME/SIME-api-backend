package br.com.sime.api.repositories;

import aj.org.objectweb.asm.commons.Remapper;
import br.com.sime.api.entities.escola.Escola;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EscolaRepository extends JpaRepository<Escola, String> {
    Optional<Escola> findByCnpjEscola(String cnpjEscola);
    Boolean existsByCnpjEscola(String cnpjEscola);
    Optional<Escola> findByCodEscola(String codEscola);
    Boolean existsByCodEscola(String codEscola);
}
