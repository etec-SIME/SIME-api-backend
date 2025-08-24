package br.com.sime.api.services;

import br.com.sime.api.DTOs.ChamadoCardDTO;
import br.com.sime.api.DTOs.ChamadoRequestDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.chamados.Feedback;
import br.com.sime.api.entities.chamados.TipoChamado;
import br.com.sime.api.entities.outros.Departamento;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.enums.PrioridadeChamadoEnum;
import br.com.sime.api.enums.StatusChamadoEnum;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.ChamadoRepository;
import br.com.sime.api.repositories.FeedbackRepository;
import br.com.sime.api.repositories.TipoChamadoRepository;
import br.com.sime.api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ChamadoService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private TipoChamadoRepository tipoChamadoRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Chamado> getAllChamados() {
        try {
            return chamadoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar chamados: " + e.getMessage(), e);
        }
    }

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
        chamado.setStatusChamado(StatusChamadoEnum.AGUARDANDO_APROVACAO.getDescricao());
        chamado.setImgChamado(dto.getImgChamado());
        chamado.setTipoChamado(tipoChamado);
        chamado.setDtAberturaChamado(LocalDateTime.now());
        chamado.setStatusChamado(StatusChamadoEnum.AGUARDANDO_APROVACAO.getDescricao());
        chamado.setPrioridadeChamado(PrioridadeChamadoEnum.ALTA_PRIORIDADE.getDescricao());

        return chamadoRepository.save(chamado);
    }

    public void definirPrioridadeChamado(Chamado chamado, PrioridadeChamadoEnum prioridade) {
        chamado.setPrioridadeChamado(prioridade.getDescricao());
        chamadoRepository.save(chamado);
    }

    public List<ChamadoCardDTO> getByPrioridadeStatusChamado(PrioridadeChamadoEnum prioridade, StatusChamadoEnum status) {
        List<Chamado> chamados = chamadoRepository.findAllByPrioridadeChamadoAndStatusChamado(
                prioridade.getDescricao(), status.getDescricao());

        if (chamados.isEmpty()) {
            throw new NotFoundException(
                    "Chamado não encontrado",
                    String.format("Nenhum chamado encontrado com a prioridade [%s] e status [%s]",
                            prioridade.getDescricao(),
                            status.getDescricao())
            );
        }

        return mapToChamadoCardDTOList(chamados);
    }

    public List<ChamadoCardDTO> getByPrioridadeChamado(PrioridadeChamadoEnum prioridade) {
        List<Chamado> chamados = chamadoRepository.findAllByPrioridadeChamado(prioridade.getDescricao());

        if (chamados.isEmpty()) {
            throw new NotFoundException(
                    "Chamado não encontrado",
                    String.format("Nenhum chamado encontrado com a prioridade [%s]", prioridade.getDescricao())
            );
        }

        return mapToChamadoCardDTOList(chamados);
    }

    //Método auxiliar
    public List<ChamadoCardDTO> mapToChamadoCardDTOList(List<Chamado> chamados) {
        if (chamados.isEmpty()) {
            throw new NotFoundException("Nenhum chamado encontrado", "Nenhum chamado encontrado");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return chamados.stream()
                .map(chamado -> new ChamadoCardDTO(
                        chamado.getIdChamado(),
                        chamado.getDtAberturaChamado().format(formatter),
                        chamado.getDescChamado(),
                        chamado.getLocalChamado(),
                        chamado.getPrioridadeChamado(),
                        chamado.getStatusChamado()
                ))
                .toList();
    }

//    public void mandarResolucaoChamado(Chamado chamado, String msgResolucao) {
//        chamado.setDtConclusaoChamado(LocalDateTime.now());
//        chamado.setMsgResolucao(msgResolucao);
//        chamado.setStatusChamado(StatusChamadoEnum.Concluído);
//
//        chamadoRepository.save(chamado);
//    }

}
