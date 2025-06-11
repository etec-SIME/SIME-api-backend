package br.com.sime.api.services;

import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestorDepartamentoService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getUsuariosByTipoPerfilAndDepartamento(Long idTipoPerfil, Long idDepartamento) {
        try {
            return usuarioRepository.findAllByTipoPerfil_IdTipoPerfilAndDepartamentoList_IdDepartamento(idTipoPerfil, idDepartamento);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuários: " + e.getMessage(), e);
        }
    }
}
