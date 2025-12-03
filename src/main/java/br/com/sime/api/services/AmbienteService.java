package br.com.sime.api.services;
import br.com.sime.api.DTOs.*;
import br.com.sime.api.DTOs.Requests.AmbienteRequestDTO;
import br.com.sime.api.DTOs.Responses.CodEquipamentoResponseDTO;
import br.com.sime.api.entities.escola.ambiente.Ambiente;
import br.com.sime.api.entities.escola.ambiente.TipoAmbiente;
import br.com.sime.api.entities.escola.equipamentos.Equipamento;
import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AmbienteService {
    @Autowired
    private AmbienteRepository ambienteRepository;

    @Autowired
    private TipoEquipamentoRepository tipoEquipamentoRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Autowired
    private TipoAmbienteRepository tipoAmbienteRepository;

    @Autowired
    private EscolaRepository escolaRepository;

    public List<AmbienteDTO> getAllAmbientes(){
        try {
            List<Ambiente> ambienteList = ambienteRepository.findAll();


            return ambienteList.stream()
                    .map( ambiente -> {
                                AmbienteDTO ambienteDTO = new AmbienteDTO();
                                ambienteDTO.setNumAmbiente(ambiente.getNumAmbiente());
                                ambienteDTO.setDescricaoAmbiente(ambiente.getDescricaoAmbiente());
                                ambienteDTO.setIdTipoAmbiente(ambiente.getTipoAmbiente().getIdTipoAmbiente());

                                List<CodEquipamentoResponseDTO> equipamentoListDTO = ambiente.getEquipamentoList().stream()
                                        .map(equip -> new CodEquipamentoResponseDTO(
                                                equip.getCodEquipamento(),
                                                equip.getTipoEquipamento().getIdTipoEquipamento()
                                        ))
                                        .collect(Collectors.toList());

                                ambienteDTO.setEquipamentoList(equipamentoListDTO);
                                return ambienteDTO;
                            }
                    ).collect(Collectors.toList());


        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar os ambientes: " + e.getMessage(), e);
        }
    }

    public Ambiente getAmbienteById(Long idAmbiente){
        return ambienteRepository.findById(idAmbiente)
                .orElseThrow(() -> new NotFoundException("Ambiente de ID: " + idAmbiente + "não encontrado"));
    }

    public List<AmbienteChamadoSelectDTO> getAllAmbienteChamadoSelect() {
        return ambienteRepository.findAll()
                .stream()
                .map(a -> {
                    // Agrupa equipamentos por tipo de equipamento
                    Map<TipoEquipamento, List<Equipamento>> equipamentosPorTipo =
                            a.getEquipamentoList().stream()
                                    .collect(Collectors.groupingBy(Equipamento::getTipoEquipamento));

                    // Transforma os grupos em TipoEquipamentoSelectDTO
                    List<TipoEquipamentoSelectDTO> tipoEquipamentoList = equipamentosPorTipo.entrySet().stream()
                            .map(entry -> new TipoEquipamentoSelectDTO(
                                    entry.getKey().getIdTipoEquipamento(),
                                    entry.getKey().getNomeTipoEquipamento(),
                                    entry.getKey().getTipoChamado().getIdTipoChamado(),
                                    entry.getKey().getTipoChamado().getNomeTipoChamado(),
                                    entry.getValue().stream()
                                            .map(e -> new TipoEquipamentoSelectDTO.CodEquipamentoList(
                                                    e.getCodEquipamento()
                                            ))
                                            .toList()
                            ))
                            .toList();

                    return new AmbienteChamadoSelectDTO(
                            a.getIdAmbiente(),
                            a.getNumAmbiente(),
                            a.getTipoAmbiente().getIdTipoAmbiente(),
                            a.getTipoAmbiente().getNomeTipoAmbiente(),
                            tipoEquipamentoList
                    );
                })
                .toList();
    }

    public AmbienteDTO cadastrarAmbiente(AmbienteDTO dto){

        TipoAmbiente tipoAmbiente = tipoAmbienteRepository.findById(dto.getIdTipoAmbiente())
                .orElseThrow(() -> new NotFoundException("Tipo ambiente de ID: " + dto.getIdTipoAmbiente() + "não encontrado"));

        Ambiente ambiente = new Ambiente();
        ambiente.setNumAmbiente(dto.getNumAmbiente());
        ambiente.setDescricaoAmbiente(dto.getDescricaoAmbiente());
        ambiente.setTipoAmbiente(tipoAmbiente);

        List<Equipamento> equipamentos = new ArrayList<>();

        for(CodEquipamentoResponseDTO equipDto : dto.getEquipamentoList()){

            Equipamento equipamento = equipamentoRepository.findByCodEquipamento(equipDto.codEquipamento())
                    .orElseThrow(() -> new NotFoundException("Equipamento com código: " + equipDto.codEquipamento() + " não encontrado!"));

            equipamento.setAmbiente(ambiente);
            equipamentos.add(equipamento);
        }

        ambiente.setEquipamentoList(equipamentos);

        ambienteRepository.save(ambiente);


        List<CodEquipamentoResponseDTO> equipamentoListDTO = ambiente.getEquipamentoList().stream()
                .map(equip -> new CodEquipamentoResponseDTO(
                        equip.getCodEquipamento(),
                        equip.getTipoEquipamento().getIdTipoEquipamento()
                ))
                .collect(Collectors.toList());

        AmbienteDTO ambienteDTO = new AmbienteDTO();
        ambienteDTO.setNumAmbiente(ambiente.getNumAmbiente());
        ambienteDTO.setDescricaoAmbiente(ambiente.getDescricaoAmbiente());
        ambienteDTO.setIdTipoAmbiente(ambiente.getTipoAmbiente().getIdTipoAmbiente());
        ambienteDTO.setEquipamentoList(equipamentoListDTO);

        return ambienteDTO;
    }

    public List<Equipamento> getAllEquipamentoAmbiente(Long idAmbiente){
        Ambiente ambiente = ambienteRepository.findById(idAmbiente)
                .orElseThrow(() -> new NotFoundException("Ambiente de ID: " + idAmbiente + "não encontrado"));
        return ambiente.getEquipamentoList();
    }

    public Ambiente atribuirTipoEquipamentos(Long idAmbiente, TipoEquipamentoAmbienteDTO dto){
        Ambiente ambiente =  ambienteRepository.findById(idAmbiente)
                .orElseThrow(() -> new NotFoundException("Ambiente de ID: " + idAmbiente + "não encontrado"));

        List<TipoEquipamento> tipoEquipamentos = tipoEquipamentoRepository.findAllById(dto.getIdsTipoEquipamento());

        Set<Long> idsEncontrados = tipoEquipamentos.stream()
                .map(TipoEquipamento::getIdTipoEquipamento)
                .collect(Collectors.toSet());

        List<Long> idsNaoEncontrados = dto.getIdsTipoEquipamento().stream()
                .filter( id -> !idsEncontrados.contains(id))//Verifica se o idPermissao existe
                .toList();

        if (!idsNaoEncontrados.isEmpty()) {
            throw new NotFoundException(
                    "Tipos de equipamento não encontradas",
                    "IDs inválidos: " + idsNaoEncontrados
            );
        }

        //ambiente.setTipoEquipamentoList(tipoEquipamentos);

        return ambienteRepository.save(ambiente);
    }

    public Ambiente editarAmbiente(Long idAmbiente, AmbienteDTO ambienteDTO){
        Ambiente ambiente = ambienteRepository.findById(idAmbiente)
                .orElseThrow(() -> new NotFoundException("Ambiente de ID: " + idAmbiente + "não encontrado"));

        TipoAmbiente tipoAmbiente = tipoAmbienteRepository.findById(ambienteDTO.getIdTipoAmbiente())
                .orElseThrow(() -> new NotFoundException("Tipo ambiente de ID: " + ambienteDTO.getIdTipoAmbiente() + "não encontrado"));

        ambiente.setDescricaoAmbiente(ambienteDTO.getDescricaoAmbiente());
        ambiente.setNumAmbiente(ambienteDTO.getNumAmbiente());
        ambiente.setTipoAmbiente(tipoAmbiente);

        return ambienteRepository.save(ambiente);
    }
}
