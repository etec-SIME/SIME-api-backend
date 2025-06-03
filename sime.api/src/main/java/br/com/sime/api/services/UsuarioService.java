package br.com.sime.api.services;

import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.outros.TipoPerfil;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.enums.PrioridadeChamadoEnum;
import br.com.sime.api.enums.StatusChamadoEnum;
import br.com.sime.api.exceptions.EscolaNotFoundException;
import br.com.sime.api.exceptions.SenhaIncorretaException;
import br.com.sime.api.exceptions.TipoPerfilNotFoundException;
import br.com.sime.api.exceptions.UsuarioNotFoundException;
import br.com.sime.api.repositories.TipoPerfilRepository;
import br.com.sime.api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoPerfilRepository tipoPerfilRepository;

    public List<Usuario> getAllUsuarios() {
        try {
            return usuarioRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuários: " + e.getMessage(), e);
        }
    }

    public boolean login(String rmUsuario, String senhaUsuario, Long idTipoPerfil, String codEscola) {
        TipoPerfil tipoPerfil = getTipoPerfilOrThrow(idTipoPerfil);

        boolean getEscola = tipoPerfil.getEscolaList()
                .stream()
                .anyMatch(escola -> escola.getCodEscola().equals(codEscola));

        if (!getEscola)
            throw new EscolaNotFoundException("Escola com código: " + codEscola + " não encontrada para o tipo de perfil: " + idTipoPerfil);

        return usuarioRepository.findUsuarioTipoPerfilAndEscola(rmUsuario, idTipoPerfil, codEscola)
            .map(usuario -> {
                if (!usuario.getSenhaUsuario().equals(senhaUsuario)) {
                    throw new SenhaIncorretaException("Senha incorreta para o usuário: " + rmUsuario);
                }
                return true;
            })
            .orElseThrow(() -> new UsuarioNotFoundException("Usuário com RM: " + rmUsuario + " não encontrado"));
    }

    public Chamado criarChamado(String rmUsuario, Long idTipoPerfil, String tituloChamado, String descChamado, String localChamado) {
        TipoPerfil tipoPerfil = getTipoPerfilOrThrow(idTipoPerfil);

        Usuario usuario = usuarioRepository.findByRmUsuarioAndTipoPerfil(rmUsuario, tipoPerfil)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado: " + rmUsuario));

        Chamado chamado = new Chamado();
        chamado.setTituloChamado(tituloChamado);
        chamado.setDescChamado(descChamado);
        chamado.setLocalChamado(localChamado);
        chamado.setUsuario(usuario);
        chamado.setStatusChamado(StatusChamadoEnum.PENDENTE);
        chamado.setPrioridadeChamado(PrioridadeChamadoEnum.ALTA_PRIORIDADE); //Revisar

        return chamado;
    }

    private TipoPerfil getTipoPerfilOrThrow(Long idTipoPerfil) {
        return tipoPerfilRepository.findById(idTipoPerfil)
                .orElseThrow(() -> new TipoPerfilNotFoundException("Tipo de perfil não encontrado: " + idTipoPerfil));
    }
}
