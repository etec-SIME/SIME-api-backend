package br.com.sime.api.services;

import br.com.sime.api.DTOs.LoginDTO;
import br.com.sime.api.DTOs.TokenDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.usuarios.TipoPerfil;
import br.com.sime.api.repositories.ChamadoRepository;
import br.com.sime.api.security.UserDetailsImpl;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.exceptions.SenhaIncorretaException;
import br.com.sime.api.repositories.TipoPerfilRepository;
import br.com.sime.api.repositories.UsuarioRepository;
import br.com.sime.api.security.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoPerfilRepository tipoPerfilRepository;

    @Autowired
    private JwtService jwtService;

    public List<Usuario> getAllUsuarios() {
        try {
            return usuarioRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuários: " + e.getMessage(), e);
        }
    }

    public TokenDTO login(LoginDTO login) {

        TipoPerfil tipoPerfil = tipoPerfilRepository.findById(login.getIdTipoPerfil())
                .orElseThrow(() -> new NotFoundException("Tipo de perfil não encontrado: " + login.getIdTipoPerfil()));

        boolean getEscola = tipoPerfil.getEscolaList()
                .stream()
                .anyMatch(escola -> escola.getCodEscola().equals(login.getCodEscola()));

        if (!getEscola)
            throw new NotFoundException("Escola não encontrada", "Escola com código: " + login.getCodEscola() + " não encontrada para o tipo de perfil: " + login.getIdTipoPerfil());

        Usuario usuario = usuarioRepository.findUsuarioTipoPerfilAndEscola(
                login.getRmUsuario(),
                login.getIdTipoPerfil(),
                login.getCodEscola()
        ).orElseThrow(() -> new NotFoundException("Usuário não encontrado", "Usuário com RM: " + login.getRmUsuario() + " não encontrado"));

        if (!usuario.getSenhaUsuario().equals(login.getSenhaUsuario()))
            throw new SenhaIncorretaException("Senha incorreta para o usuário: " + login.getRmUsuario());

        String token = jwtService.generateToken(new UserDetailsImpl(usuario));

        return new TokenDTO(token);
    }

}
