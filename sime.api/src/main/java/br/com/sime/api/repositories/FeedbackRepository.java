package br.com.sime.api.repositories;

import br.com.sime.api.entities.chamados.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
