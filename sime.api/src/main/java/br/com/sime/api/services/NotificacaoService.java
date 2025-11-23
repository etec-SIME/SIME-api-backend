package br.com.sime.api.services;

import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.usuarios.Mensagem;
import br.com.sime.api.entities.usuarios.Notificacao;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.repositories.NotificacaoRepository;
import br.com.sime.api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacaoService {

    @Autowired
    NotificacaoRepository notificacaoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    MensagemService mensagemService;

    public Notificacao enviarNotificacaoCompartilhada(Chamado chamado) {
        Mensagem mensagem = mensagemService.criarMensagemAutomatica(chamado);

        List<Usuario> usuarioList = usuarioRepository.findByTipoPerfil_Permissoes_Nome("Aprovar ou Reprovar Chamado");

        Notificacao notificacao = new Notificacao();
        notificacao.setMensagem(mensagem);
        notificacao.setVisualizacao(false);
        notificacao.setDataNotificacao(LocalDateTime.now());
        notificacao.setUsuarioList(usuarioList);

        return notificacaoRepository.save(notificacao);
    }

    public Notificacao enviarNotificacaoIndividual(Usuario usuario, Mensagem mensagem) {
        Notificacao notificacao = new Notificacao();
        notificacao.setMensagem(mensagem);
        notificacao.setVisualizacao(false);
        notificacao.setDataNotificacao(LocalDateTime.now());

        notificacao.getUsuarioList().add(usuario);

        return notificacaoRepository.save(notificacao);
    }

    public void removerNotificacaoDoChamado(Long chamadoId) {
        List<Notificacao> notificacaoList = notificacaoRepository.findByMensagem_Chamado_Id(chamadoId);

        if (!notificacaoList.isEmpty()) {
            notificacaoRepository.deleteAll(notificacaoList);
        }
    }
}
