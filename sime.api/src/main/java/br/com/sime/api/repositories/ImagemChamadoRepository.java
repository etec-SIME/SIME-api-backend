package br.com.sime.api.repositories;

import br.com.sime.api.entities.chamados.ImagemChamado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagemChamadoRepository extends JpaRepository<ImagemChamado, Long> {
}
