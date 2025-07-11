package br.com.sime.api.services;

import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.enums.PrioridadeChamadoEnum;
import br.com.sime.api.enums.StatusChamadoEnum;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.ChamadoRepository;
import br.com.sime.api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestorGeralService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private ChamadoService chamadoService;

    public List<Chamado> getAllChamados(){
        try{
            return chamadoRepository.findAll();
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao buscar chamados: " + e.getMessage(), e);
        }
    }

    public void definirPrioridadeChamado(String rmGestor, Long idChamado, PrioridadeChamadoEnum novaPrioridade){
        Usuario gestor = usuarioRepository.findById(rmGestor)
                .orElseThrow(() -> new NotFoundException("Gestor não encontrado com ID: " + rmGestor));

        Chamado chamado = chamadoRepository.findById(idChamado)
                .orElseThrow(() -> new NotFoundException("Chamado não encontrado com ID: "+ idChamado));

        chamado.setPrioridadeChamado(novaPrioridade.getDescricao());

        chamadoRepository.save(chamado);
    }

    public void aceitarChamado(Long idChamado){
        Chamado chamado = chamadoRepository.findById(idChamado)
                .orElseThrow(() -> new NotFoundException("Chamado não encontrado com ID: " + idChamado));

        chamado.setStatusChamado(StatusChamadoEnum.PENDENTE.getDescricao());

        chamadoRepository.save(chamado);
    }

    public String recusarChamado(Long idChamado, String msgRecusa){
        Chamado chamado = chamadoRepository.findById(idChamado)
                .orElseThrow(() -> new NotFoundException("Chamado não encontrado com ID: " + idChamado));

        chamado.setStatusChamado(StatusChamadoEnum.RECUSADO.getDescricao());

        chamadoRepository.save(chamado);

        return msgRecusa;
    }
}
