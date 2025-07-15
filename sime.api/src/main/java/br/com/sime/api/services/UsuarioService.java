package br.com.sime.api.services;

import br.com.sime.api.DTOs.LoginDTO;
import br.com.sime.api.DTOs.TokenDTO;
import br.com.sime.api.DTOs.UsuarioRequestDTO;
import br.com.sime.api.entities.outros.Departamento;
import br.com.sime.api.entities.usuarios.TipoPerfil;
import br.com.sime.api.exceptions.ConflictException;
import br.com.sime.api.repositories.DepartamentoRepository;
import br.com.sime.api.DTOs.Projections.UsuarioProjection;
import br.com.sime.api.security.UserDetailsImpl;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.exceptions.SenhaIncorretaException;
import br.com.sime.api.repositories.TipoPerfilRepository;
import br.com.sime.api.repositories.UsuarioRepository;
import br.com.sime.api.security.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
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

    public Usuario cadastrarUsuario(UsuarioRequestDTO dto)
    {

        if(!usuarioRepository.existsByRmUsuario(dto.getRmUsuario())) {throw new ConflictException("Usuário com esse RM já existe!");}

        if(!usuarioRepository.existByCpfUsuario(dto.getCpfUsuario())){throw new ConflictException("Usuário com esse CPF já existe!");}

        TipoPerfil tipoPerfil = tipoPerfilRepository.findById(dto.getIdTipoPerfil())
                .orElseThrow(() -> new NotFoundException("Id tipo perfil não encontrado", "Tipo perfil não encontrado: " + dto.getIdTipoPerfil()));


        List<Long> ids = dto.getDepartamentoIds();
        List<Departamento> departamentos = departamentoRepository.findAllById(ids);

        Set<Long> idsEncontrados = departamentos.stream()
                .map(Departamento::getIdDepartamento)
                .collect(Collectors.toSet());

        List<Long> idsNaoEncontrados = ids.stream()
                .filter( id -> !idsEncontrados.contains(id))
                .toList();

        if (!idsNaoEncontrados.isEmpty()) {
            throw new NotFoundException(
                    "Departamentos não encontrados",
                    "IDs inválidos: " + idsNaoEncontrados
            );
        }

        Usuario usuario = new Usuario();
        usuario.setRmUsuario(dto.getRmUsuario());
        usuario.setNomeUsuario(dto.getNomeUsuario());
        usuario.setTelefoneUsuario(dto.getTelefoneUsuario());
        usuario.setEmailUsuario(dto.getEmailUsuario());
        usuario.setSenhaUsuario(passwordEncoder.encode(dto.getSenhaUsuario()));
        usuario.setCpfUsuario(dto.getCpfUsuario());
        usuario.setTipoPerfil(tipoPerfil);
        usuario.setDepartamentoList(departamentos);

        return usuarioRepository.save(usuario);
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
