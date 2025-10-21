package br.com.sime.api.services;

import br.com.sime.api.DTOs.ChamadoCardDTO;
import br.com.sime.api.DTOs.Responses.ChamadoStatusResponseDTO;
import br.com.sime.api.DTOs.Requests.ChamadoRequestDTO;
import br.com.sime.api.DTOs.Responses.ChamadoResponseDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.chamados.ImagemChamado;
import br.com.sime.api.entities.chamados.TipoChamado;
import br.com.sime.api.entities.chamados.historicos.HistoricoStatusProgresso;
import br.com.sime.api.entities.escola.ambiente.Ambiente;
import br.com.sime.api.entities.escola.ambiente.TipoAmbiente;
import br.com.sime.api.entities.escola.equipamentos.Equipamento;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.enums.PrioridadeChamadoEnum;
import br.com.sime.api.enums.StatusGeralEnum;
import br.com.sime.api.enums.StatusProgressoEnum;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ChamadoService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Autowired
    private TipoChamadoRepository tipoChamadoRepository;

    @Autowired
    private TipoEquipamentoRepository tipoEquipamentoRepository;

    @Autowired
    private TipoAmbienteRepository tipoAmbienteRepository;

    @Autowired
    private AmbienteRepository ambienteRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ImagemChamadoService imagemChamadoService;

    @Autowired
    private HistoricoStatusProgressoRepository historicoStatusProgressoRepository;

    public List<Chamado> getAllChamados() {
        try {
            return chamadoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar chamados: " + e.getMessage(), e);
        }
    }

    public void criarChamado(String rmUsuario, ChamadoRequestDTO dto, MultipartFile[] files) {
        Usuario usuario = usuarioRepository.findByRmUsuario(rmUsuario)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado", "Usuário não encontrado: " + rmUsuario));

        TipoChamado tipoChamado = tipoChamadoRepository.findById(dto.idTipoChamado())
                .orElseThrow(() -> new NotFoundException("Tipo de chamado não encontrado", "Id: " + dto.idTipoChamado()));

        TipoAmbiente tipoAmbiente = tipoAmbienteRepository.findById(dto.idTipoAmbiente())
                .orElseThrow(() -> new NotFoundException("Tipo de ambiente não encontrado", "Id: " + dto.idTipoAmbiente()));

        Ambiente ambiente = ambienteRepository.findById(dto.idAmbiente())
                .orElseThrow(() -> new NotFoundException("Ambiente não encontrado", "Id: " + dto.idAmbiente()));

        Equipamento equipamento = equipamentoRepository.findByCodEquipamentoWithTipoEquipamento(dto.codEquipamento())
                .orElseThrow(() -> new NotFoundException("Equipamento não encontrado", "Código do equipamento: " + dto.codEquipamento()));

        if(!ambiente.getTipoAmbiente().getIdTipoAmbiente().equals(tipoAmbiente.getIdTipoAmbiente())) {
            throw new NotFoundException("O ambiente não corresponde ao tipo ambiente selecionado");
        }

        Chamado chamado = new Chamado();
        chamado.setTituloChamado(dto.tituloChamado());
        chamado.setDescChamado(dto.descChamado());
        chamado.setUsuario(usuario);
        chamado.setTipoChamado(tipoChamado);
        chamado.setTipoAmbiente(tipoAmbiente);
        chamado.setDtAberturaChamado(dto.dataAbertura());
        chamado.setStatusAtualGeralChamado(StatusGeralEnum.AGUARDANDO_APROVACAO.getDescricao());
        chamado.setStatusAtualProgressoChamado(StatusProgressoEnum.EM_ANALISE.getDescricao());
        chamado.setPrioridadeChamado(PrioridadeChamadoEnum.ALTA_PRIORIDADE.getDescricao());

        chamadoRepository.save(chamado);

        if (files != null && files.length > 0) {
            try {
                List<ImagemChamado> imagens = imagemChamadoService.salvarImagens(chamado.getIdChamado(), files);
                chamado.setImagemChamadoList(imagens);
                chamadoRepository.save(chamado);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao salvar imagens: " + e.getMessage(), e);
            }
        }
    }

    public ChamadoStatusResponseDTO atualizarStatusProgresso(Long idChamado, StatusProgressoEnum novoStatus) {
        Chamado chamado = chamadoRepository.findById(idChamado)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));

        chamado.setStatusAtualProgressoChamado(novoStatus.getDescricao());
        chamadoRepository.save(chamado);

        HistoricoStatusProgresso historicoStatusProgresso = new HistoricoStatusProgresso();
        historicoStatusProgresso.setChamado(chamado);
        historicoStatusProgresso.setStatusProgresso(novoStatus.getDescricao());

        LocalDateTime agora = LocalDateTime.now();
        historicoStatusProgresso.setDtAlteracao(agora);

        // Dia da semana com primeira letra maiúscula e sem "feira"
        String diaSemana = agora.getDayOfWeek()
                .getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
        diaSemana = capitalizeFirstLetter(removeFeira(diaSemana));
        historicoStatusProgresso.setDiaSemana(diaSemana);
        historicoStatusProgressoRepository.save(historicoStatusProgresso);

        chamado.getHistoricoStatusProgressoList().add(historicoStatusProgresso);
        return getStatusChamado(chamado.getIdChamado());
    }

    public ChamadoStatusResponseDTO atualizarStatusGeral(Long idChamado, StatusGeralEnum novoStatus) {
        Chamado chamado = chamadoRepository.findById(idChamado)
                .orElseThrow(() -> new NotFoundException("Chamado não encontrado"));

        chamado.setStatusAtualGeralChamado(novoStatus.getDescricao());

        chamadoRepository.save(chamado);
        return getStatusChamado(chamado.getIdChamado());
    }

    public ChamadoStatusResponseDTO getStatusChamado(Long idChamado) {
        Chamado chamado = chamadoRepository.findById(idChamado)
                .orElseThrow(() -> new NotFoundException("Chamado não encontrado"));

        var historicoChamadoList = chamado.getHistoricoStatusProgressoList()
                .stream()
                .map(h -> new ChamadoStatusResponseDTO.HistoricoChamadoList(
                        h.getStatusProgresso(),
                        h.getDiaSemana(),
                        h.getDtAlteracao()
                )).toList();

        return new ChamadoStatusResponseDTO(
                chamado.getIdChamado(),
                chamado.getStatusAtualProgressoChamado(),
                chamado.getStatusAtualGeralChamado(),
                historicoChamadoList
        );
    }

    public Optional<ChamadoResponseDTO> getDetalhesChamado(Long idChamado) {
        Optional<Chamado> chamado = chamadoRepository.findById(idChamado);

        if (chamado.isEmpty()) {
            throw new NotFoundException("Chamado não encontrado", "Nenhum chamado encontrado com o ID: " + idChamado);
        }

        return chamado.map(c -> new ChamadoResponseDTO(
                c.getIdChamado(),
                c.getTituloChamado(),
                c.getStatusAtualGeralChamado(),
                c.getDescChamado(),
                c.getTipoChamado().getNomeTipoChamado(),
                c.getPrioridadeChamado(),
                c.getDtAberturaChamado(),
                c.getDtConclusaoChamado(),
                c.getImagemChamadoList()
                        .stream()
                        .map(caminhoImagem -> caminhoImagem.getCaminho())
                        .toList()
        ));
    }

    public List<ChamadoCardDTO> getByPrioridadeStatusChamado(PrioridadeChamadoEnum prioridade, StatusGeralEnum status) {
        List<Chamado> chamados = chamadoRepository.findAllByPrioridadeChamadoAndStatusAtualGeralChamado(
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

    //Metodos Auxiliares
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
                        chamado.getPrioridadeChamado(),
                        chamado.getStatusAtualGeralChamado()
                ))
                .toList();
    }

    private String removeFeira(String dia) {
        return dia.replace("feira", "")
                .replace("-", "")
                .trim();
    }

    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

//    public void mandarResolucaoChamado(Chamado chamado, String msgResolucao) {
//        chamado.setDtConclusaoChamado(LocalDateTime.now());
//        chamado.setMsgResolucao(msgResolucao);
//        chamado.setStatusChamado(StatusChamadoEnum.Concluído);
//
//        chamadoRepository.save(chamado);
//    }

}
