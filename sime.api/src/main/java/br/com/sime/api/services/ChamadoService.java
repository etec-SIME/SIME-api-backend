package br.com.sime.api.services;

import br.com.sime.api.DTOs.ChamadoRequestDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.chamados.Feedback;
import br.com.sime.api.entities.chamados.TipoChamado;
import br.com.sime.api.entities.outros.Departamento;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.enums.PrioridadeChamadoEnum;
import br.com.sime.api.enums.StatusChamadoEnum;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.ChamadoRepository;
import br.com.sime.api.repositories.FeedbackRepository;
import br.com.sime.api.repositories.TipoChamadoRepository;
import br.com.sime.api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChamadoService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private TipoChamadoRepository tipoChamadoRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Chamado> getAllChamados() {
        try {
            return chamadoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar chamados: " + e.getMessage(), e);
        }
    }

    public Chamado criarChamado(String rmUsuario, ChamadoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByRmUsuario(rmUsuario)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado", "Usuário não encontrado: " + rmUsuario));

        TipoChamado tipoChamado = tipoChamadoRepository.findByNomeTipoChamadoIgnoreCase(dto.getTipoChamado())
                .orElseThrow(() -> new NotFoundException("Tipo de chamado não encontrado", "Tipo: " + dto.getTipoChamado()));

        Chamado chamado = new Chamado();
        chamado.setTituloChamado(dto.getTituloChamado());
        chamado.setDescChamado(dto.getDescChamado());
        chamado.setLocalChamado(dto.getLocalChamado());
        chamado.setUsuario(usuario);
        chamado.setStatusChamado(StatusChamadoEnum.AGUARDANDO_APROVACAO.getDescricao());
        chamado.setImgChamado(dto.getImgChamado());
        chamado.setTipoChamado(tipoChamado);
        chamado.setDtAberturaChamado(LocalDateTime.now());
        chamado.setPrioridadeChamado(PrioridadeChamadoEnum.ALTA_PRIORIDADE.getDescricao());

        return chamadoRepository.save(chamado);
    }

    public void enviarFeedBack(Chamado chamado, String rmGestor, String descricaoFeedback, Usuario gestor) {
        Feedback feedback = new Feedback();
        feedback.setRemetenteFeedback(rmGestor);
        feedback.setDestinatarioFeedback("quicas");
        feedback.setDescFeedback(descricaoFeedback);
        feedback.setDtFeedback(LocalDateTime.now());
        feedback.setUsuario(gestor);
        feedback.setChamado(chamado);

        feedbackRepository.save(feedback);
    }
}
