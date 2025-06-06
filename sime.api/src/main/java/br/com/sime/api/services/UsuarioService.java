package br.com.sime.api.services;

import br.com.sime.api.entities.outros.TipoPerfil;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.exceptions.SenhaIncorretaException;
import br.com.sime.api.repositories.ChamadoRepository;
import br.com.sime.api.repositories.TipoChamadoRepository;
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
    private ChamadoRepository chamadoRepository;

    @Autowired
    private TipoChamadoRepository tipoChamadoRepository;

    @Autowired
    private TipoPerfilRepository tipoPerfilRepository;

    public List<Usuario> getAllUsuarios() {
        try {
            return usuarioRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuários: " + e.getMessage(), e);
        }
    }

    public void login(String rmUsuario, String senhaUsuario, Long idTipoPerfil, String codEscola) {
        TipoPerfil tipoPerfil = getTipoPerfilOrThrow(idTipoPerfil);

        boolean getEscola = tipoPerfil.getEscolaList()
                .stream()
                .anyMatch(escola -> escola.getCodEscola().equals(codEscola));

        if (!getEscola)
            throw new NotFoundException("Escola não encontrada", "Escola com código: " + codEscola + " não encontrada para o tipo de perfil: " + idTipoPerfil);

        usuarioRepository.findUsuarioTipoPerfilAndEscola(rmUsuario, idTipoPerfil, codEscola)
                .map(usuario -> {
                    if (!usuario.getSenhaUsuario().equals(senhaUsuario)) {
                        throw new SenhaIncorretaException("Senha incorreta para o usuário: " + rmUsuario);
                    }
                    return true;
                })
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado", "Usuário com RM: " + rmUsuario + " não encontrado"));
    }

    private TipoPerfil getTipoPerfilOrThrow(Long idTipoPerfil) {
        return tipoPerfilRepository.findById(idTipoPerfil)
                .orElseThrow(() -> new NotFoundException("Tipo de perfil não encontrado: " + idTipoPerfil));
    }
}
