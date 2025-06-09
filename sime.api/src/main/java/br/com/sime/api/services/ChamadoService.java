package br.com.sime.api.services;

import br.com.sime.api.DTOs.ChamadoRequestDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.chamados.TipoChamado;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.enums.PrioridadeChamadoEnum;
import br.com.sime.api.enums.StatusChamadoEnum;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.ChamadoRepository;
import br.com.sime.api.repositories.TipoChamadoRepository;
import br.com.sime.api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChamadoService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private TipoChamadoRepository tipoChamadoRepository;

    public Chamado criarChamado(String rmUsuario, ChamadoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByRmUsuario(rmUsuario)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado", "Usuário não encontrado: " + rmUsuario));

        TipoChamado tipoChamado = tipoChamadoRepository.findByNomeTipoChamadoIgnoreCase(dto.getTipoChamado())
                .orElseThrow(() -> new NotFoundException("Tipo de chamado não encontrado", "Tipo: " + dto.getTipoChamado()));

        Chamado chamado = new Chamado();
        chamado.setTituloChamado(dto.getTituloChamado());
        chamado.setDescChamado(dto.getDescChamado());
        chamado.setLocalChamado(dto.getLocalChamado());
        chamado.setUsuario(usuario);
        chamado.setStatusChamado(StatusChamadoEnum.PENDENTE);
        chamado.setImgChamado(dto.getImgChamado());
        chamado.setTipoChamado(tipoChamado);
        chamado.setDtAberturaChamado(LocalDateTime.now());
        chamado.setPrioridadeChamado(PrioridadeChamadoEnum.ALTA_PRIORIDADE.getDescricao());

        return chamadoRepository.save(chamado);
    }
}
