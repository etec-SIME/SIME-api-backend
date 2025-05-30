package br.com.sime.api.services;

import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.outros.TipoPerfil;
import br.com.sime.api.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public boolean login(String rmUsuario, TipoPerfil tipoPerfilUsuario, String senhaUsuario) {
        return usuarioRepository.findByRmAndTipoPerfilUsuario(rmUsuario, tipoPerfilUsuario)
                .map(usuario -> usuario.getSenhaUsuario().equals(senhaUsuario))
                .orElse(false);
    }

    public Chamado criarChamado(String rmUsuario, TipoPerfil tipoPerfilUsuario, String tituloChamado, String descChamado, String localChamado) {
        return usuarioRepository.findByRmAndTipoPerfilUsuario(rmUsuario, tipoPerfilUsuario)
                .map(usuario -> {
                    Chamado chamado = new Chamado();
                    chamado.setTituloChamado(tituloChamado);
                    chamado.setDescChamado(descChamado);
                    chamado.setLocalChamado(localChamado);
                    chamado.setUsuario(usuario);
                    chamado.setStatusChamado(StatusChamadoEnum.ABERTO);
                    //chamado.setPrioridadeChamado(PrioridadeChamadoEnum.ALTA_PRIORIDADE);
                    return chamado;
                })
                .orElse(null);
    }

    //public void Operation() { }
}
