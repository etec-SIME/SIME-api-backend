package br.com.sime.api.services;

import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.chamados.Feedback;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.ChamadoRepository;
import br.com.sime.api.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> getAllFeedbacks(){
        try{
            return feedbackRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException("Erro ao buscar escolas: " + e.getMessage(), e);
        }
    }

    public Feedback getFeedbackById(Long idFeedback){
        return feedbackRepository.findById(idFeedback)
                .orElseThrow(() -> new NotFoundException("Feedback de ID: " + idFeedback + "não encontrado"));
    }

    public void criarFeedback(Chamado chamado, String rmGestor, String descricaoFeedback, Usuario gestor) {
        Feedback feedback = new Feedback();
        feedback.setRemetenteFeedback(rmGestor);
        feedback.setDescFeedback(descricaoFeedback);
        feedback.setDtFeedback(LocalDateTime.now());
        feedback.setUsuario(gestor);
        feedback.setChamado(chamado);

        feedbackRepository.save(feedback);
    }

    //Editar feedback - editar descrição

}
