package br.com.sime.api.services;

import br.com.sime.api.DTOs.LoginDTO;
import br.com.sime.api.DTOs.TokenDTO;
import br.com.sime.api.DTOs.UserInfoDTO;
import br.com.sime.api.DTOs.UsuarioRequestDTO;
import br.com.sime.api.entities.escola.Escola;
import br.com.sime.api.entities.usuarios.Permissao;
import br.com.sime.api.entities.usuarios.TipoPerfil;
import br.com.sime.api.repositories.DepartamentoRepository;
import br.com.sime.api.repositories.EscolaRepository;
import br.com.sime.api.DTOs.Projections.UsuarioProjection;
import br.com.sime.api.security.EscolaDetailsImpl;
import br.com.sime.api.security.UserDetailsImpl;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.exceptions.SenhaIncorretaException;
import br.com.sime.api.repositories.TipoPerfilRepository;
import br.com.sime.api.repositories.UsuarioRepository;
import br.com.sime.api.security.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TipoPerfilRepository tipoPerfilRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;
    @Autowired
    private EscolaRepository escolaRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioProjection> getAllUsuarios() {
        try {
            return usuarioRepository.findAllBy();
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

        Usuario usuario = usuarioRepository.findUsuarioTipoPerfilAndEscola (
                login.getRmUsuario(),
                login.getIdTipoPerfil(),
                login.getCodEscola()
        ).orElseThrow(() -> new NotFoundException("Usuário não encontrado", "Usuário com RM: " + login.getRmUsuario() + " não encontrado"));

        if (!usuario.getSenhaUsuario().equals(login.getSenhaUsuario()))
            throw new SenhaIncorretaException("Senha incorreta para o usuário: " + login.getRmUsuario());

        String token = jwtService.generateToken(new UserDetailsImpl(usuario, "USUARIO"));

        return new TokenDTO(token);
    }

    public UserInfoDTO getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetailsImpl userDetails) {
            Usuario usuario = userDetails.getUsuario();
            List<String> permissoes = usuario.getTipoPerfil()
                    .getPermissaoList()
                    .stream()
                    .map(Permissao::getNomePermissao)
                    .toList();

            return new UserInfoDTO(
                    "USUARIO",
                    usuario.getRmUsuario(),
                    permissoes
            );

        } else if (principal instanceof EscolaDetailsImpl escolaDetails) {
            Escola escola = escolaDetails.getEscola();

            List<String> permissoes = List.of("ROLE_ESCOLA");

            return new UserInfoDTO(
                    "ESCOLA",
                    escola.getCnpjEscola(),
                    permissoes
            );

        } else {
            throw new IllegalStateException("Entidade autenticada desconhecida");
        }
    }

    public void cadastrarUsuario(UsuarioRequestDTO dto)

    {
        if(usuarioRepository.existsByRmUsuario(dto.getRmUsuario())) {throw new RuntimeException("Usuário com esse RM já existe!");}
        if(usuarioRepository.existsByCpfUsuario(dto.getCpfUsuario())){throw new RuntimeException("Usuário com esse CPF já existe!");}


        TipoPerfil tipoPerfil = tipoPerfilRepository.findById(4L).orElseThrow(() -> new NotFoundException(""));

        Usuario usuario = new Usuario();
        usuario.setRmUsuario(dto.getRmUsuario());
        usuario.setNomeUsuario(dto.getNomeUsuario());
        usuario.setTelefoneUsuario(dto.getTelefoneUsuario());
        usuario.setEmailUsuario(dto.getEmailUsuario());
        usuario.setSenhaUsuario(passwordEncoder.encode(dto.getSenhaUsuario()));
        usuario.setCpfUsuario(dto.getCpfUsuario());
        usuario.setTipoPerfil(tipoPerfil);

        usuarioRepository.save(usuario);

        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO();
        usuarioRequestDTO.setRmUsuario(usuario.getRmUsuario());
        usuarioRequestDTO.setNomeUsuario(usuario.getNomeUsuario());
        usuarioRequestDTO.setTelefoneUsuario(usuario.getTelefoneUsuario());
        usuarioRequestDTO.setEmailUsuario(usuario.getEmailUsuario());
        usuarioRequestDTO.setSenhaUsuario(usuario.getSenhaUsuario());
        usuarioRequestDTO.setCpfUsuario(usuario.getCpfUsuario());

    }
}
