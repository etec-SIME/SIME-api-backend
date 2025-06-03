package br.com.sime.api.services;

import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.outros.TipoPerfil;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.enums.PrioridadeChamadoEnum;
import br.com.sime.api.enums.StatusChamadoEnum;
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
        try {
            TipoPerfil tipoPerfil = getTipoPerfilOrThrow(idTipoPerfil);
            boolean escolaExiste = tipoPerfil.getEscolaList()
                    .stream()
                    .anyMatch(escola -> escola.getCodEscola().equals(codEscola));

            if (!escolaExiste) return false;

            return usuarioRepository.findUsuarioTipoPerfilAndEscola(rmUsuario, idTipoPerfil, codEscola)
                    .map(usuario -> usuario.getSenhaUsuario().equals(senhaUsuario))
                    .orElse(false);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar login: " + e.getMessage(), e);
        }
    }

    public Chamado criarChamado(String rmUsuario, Long idTipoPerfil, String tituloChamado, String descChamado, String localChamado) {
        try {
            TipoPerfil tipoPerfil = getTipoPerfilOrThrow(idTipoPerfil);

            Usuario usuario = usuarioRepository.findByRmUsuarioAndTipoPerfil(rmUsuario, tipoPerfil)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + rmUsuario));

            Chamado chamado = new Chamado();
            chamado.setTituloChamado(tituloChamado);
            chamado.setDescChamado(descChamado);
            chamado.setLocalChamado(localChamado);
            chamado.setUsuario(usuario);
            chamado.setStatusChamado(StatusChamadoEnum.PENDENTE);
            chamado.setPrioridadeChamado(PrioridadeChamadoEnum.ALTA_PRIORIDADE); //Revisar

            return chamado;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar chamado: " + e.getMessage(), e);
        }
    }

    private TipoPerfil getTipoPerfilOrThrow(Long idTipoPerfil) {
        return tipoPerfilRepository.findById(idTipoPerfil)
                .orElseThrow(() -> new RuntimeException("Tipo de perfil não encontrado: " + idTipoPerfil));
    }
}
