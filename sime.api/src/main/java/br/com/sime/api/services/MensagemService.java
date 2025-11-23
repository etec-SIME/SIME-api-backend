package br.com.sime.api.services;

import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.usuarios.Mensagem;
import br.com.sime.api.enums.TipoMensagemEnum;
import br.com.sime.api.repositories.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MensagemService {

    @Autowired
    MensagemRepository mensagemRepository;

    public Mensagem criarMensagemAutomatica(Chamado chamado) {
        Mensagem mensagem = new Mensagem();
        mensagem.setChamado(chamado);
        mensagem.setTipoMensagem(TipoMensagemEnum.AUTOMATICA.getDescricao());
        mensagem.setDataEnvio(LocalDateTime.now());

        return mensagemRepository.save(mensagem);
    }

    public Mensagem criarMensagemPersonalizada(Chamado chamado, String texto) {
        Mensagem mensagem = new Mensagem();
        mensagem.setChamado(chamado);
        mensagem.setTipoMensagem(TipoMensagemEnum.PERSONALIZADA.getDescricao());
        mensagem.setTexto(texto);
        mensagem.setDataEnvio(LocalDateTime.now());

        return mensagemRepository.save(mensagem);
    }
}
