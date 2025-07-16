package br.com.sime.api.services;

import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.outros.Departamento;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.enums.PrioridadeChamadoEnum;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.ChamadoRepository;
import br.com.sime.api.repositories.FeedbackRepository;
import br.com.sime.api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestorDepartamentoService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private ChamadoService chamadoService;

    @Autowired
    private FeedbackService feedbackService;

    public List<Usuario> getUsuariosByTipoPerfilAndDepartamento(Long idTipoPerfil, Long idDepartamento) {
        try {
            return usuarioRepository.findAllByTipoPerfil_IdTipoPerfilAndDepartamentoList_IdDepartamento(idTipoPerfil, idDepartamento);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuários: " + e.getMessage(), e);
        }
    }

    public void definirPrioridadeChamado(String rmGestor, Long idChamado, PrioridadeChamadoEnum novaPrioridade) {
        Usuario gestor = usuarioRepository.findById(rmGestor)
                .orElseThrow(() -> new RuntimeException("Gestor não encontrado com ID: " + rmGestor));

        Chamado chamado = chamadoRepository.findById(idChamado)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado com ID: " + idChamado));

//        boolean autorizado = gestor.getDepartamentoList().stream()
//                .anyMatch(departamento -> chamado.getUsuario().getDepartamentoList().contains(departamento));
//
//        if (!autorizado) {
//            throw new RuntimeException("Gestor não autorizado a alterar prioridade deste chamado.");
//        }

        chamadoService.definirPrioridadeChamado(chamado, novaPrioridade);
    }

    public void enviarFeedback(Long idChamado, String rmGestor, String destinatario, String descricaoFeedback) {
        Usuario gestor = usuarioRepository.findByRmUsuario(rmGestor)
                .orElseThrow(() -> new NotFoundException("Gestor não encontrado"));

        Chamado chamado = chamadoRepository.findById(idChamado)
                .orElseThrow(() -> new NotFoundException("Chamado não encontrado"));

//        // Verifica se gestor está autorizado (se gerencia o departamento do chamado)
//        boolean autorizado = gestor.getDepartamentoList().stream()
//                .anyMatch(departamento -> departamento.equals(chamado.getTipoChamado().getDepartamento()));
//
//        if (!autorizado) {
//            throw new RuntimeException("Gestor não autorizado a enviar feedback para este chamado.");
//        }

        feedbackService.criarFeedback(chamado, rmGestor, destinatario, descricaoFeedback, gestor);
    }

//    public void concluirChamado(Long idChamado, String mensagemResolucao, String rmGestor) {
//        Chamado chamado = chamadoRepository.findById(idChamado)
//                .orElseThrow(() -> new NotFoundException("Chamado não encontrado"));
//
//        Usuario gestor = usuarioRepository.findByRmUsuario(rmGestor)
//                .orElseThrow(() -> new NotFoundException("Gestor não encontrado"));
//
////        // Verifica se gestor gerencia o departamento do chamado
////        boolean autorizado = gestor.getDepartamentoList().stream()
////                .anyMatch(dep -> dep.equals(chamado.getTipoChamado().getDepartamento()));
////
////        if (!autorizado) {
////            throw new RuntimeException("Gestor não autorizado a concluir este chamado.");
////        }
//
//        chamadoService.mandarResolucaoChamado(chamado, mensagemResolucao);
//    }

    public List<Chamado> visualizarChamadoDepartamento(String rmGestor) {
        Usuario gestor = usuarioRepository.findByRmUsuario(rmGestor)
                .orElseThrow(() -> new NotFoundException("Gestor não encontrado", "RM: " + rmGestor));

        List<Long> idsDepartamentos = gestor.getDepartamentoList().stream()
                .map(Departamento::getIdDepartamento)
                .toList();

        return chamadoRepository.findByDepartamentoIds(idsDepartamentos);
    }
}
