package br.com.sime.api.services;

import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.outros.Departamento;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.enums.PrioridadeChamadoEnum;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.ChamadoRepository;
import br.com.sime.api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestorDepartamentoService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private ChamadoService chamadoService;

    @Autowired
    private GestorGeralService gestorGeralService;

    public List<Usuario> getUsuariosByTipoPerfilAndDepartamento(Long idTipoPerfil, Long idDepartamento) {
        try {
            return usuarioRepository.findAllByTipoPerfil_IdTipoPerfilAndDepartamentoList_IdDepartamento(idTipoPerfil, idDepartamento);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuários: " + e.getMessage(), e);
        }
    }

    public void definirPrioridadeChamado(String rmGestor, Long idChamado, PrioridadeChamadoEnum novaPrioridade) {
        gestorGeralService.definirPrioridadeChamado(rmGestor, idChamado, novaPrioridade);
    }

    public String enviarFeedback(Long idChamado, String rmGestor, String descricaoFeedback) {
        Usuario gestor = usuarioRepository.findByRmUsuario(rmGestor)
                .orElseThrow(() -> new NotFoundException("Gestor não encontrado"));

        Chamado chamado = chamadoRepository.findById(idChamado)
                .orElseThrow(() -> new NotFoundException("Chamado não encontrado"));

        chamadoService.enviarFeedBack(chamado, rmGestor, descricaoFeedback, gestor);

        return descricaoFeedback;
    }

    public List<Chamado> visualizarChamadoDepartamento(String rmGestor) {
        Usuario gestor = usuarioRepository.findByRmUsuario(rmGestor)
                .orElseThrow(() -> new NotFoundException("Gestor não encontrado", "RM: " + rmGestor));

        List<Long> idsDepartamentos = gestor.getDepartamentoList().stream()
                .map(Departamento::getIdDepartamento)
                .toList();

        return chamadoRepository.findByDepartamentoIds(idsDepartamentos);
    }
}
