package br.com.sime.api.services;
import br.com.sime.api.DTOs.AmbienteDTO;
import br.com.sime.api.DTOs.AmbienteSelectDTO;
import br.com.sime.api.DTOs.Requests.AmbienteRequestDTO;
import br.com.sime.api.DTOs.Responses.CodEquipamentoResponseDTO;
import br.com.sime.api.DTOs.TipoEquipamentoAmbienteDTO;
import br.com.sime.api.entities.escola.ambiente.Ambiente;
import br.com.sime.api.entities.escola.ambiente.TipoAmbiente;
import br.com.sime.api.entities.escola.equipamentos.Equipamento;
import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.*;
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


    public List<AmbienteSelectDTO> getAllAmbienteChamadoSelect() {
        return ambienteRepository.findAll()
                .stream()
                .map(a -> new AmbienteSelectDTO(
                        a.getIdAmbiente(),
                        a.getNumAmbiente(),
                        a.getTipoAmbiente().getIdTipoAmbiente(),
                        a.getTipoAmbiente().getNomeTipoAmbiente()
                ))
                .collect(Collectors.toList());
    }

//    public AmbienteRequestDTO cadastrarAmbiente(AmbienteRequestDTO request) {
//        TipoAmbiente tipoAmbiente = tipoAmbienteRepository.findById(request.idTipoAmbiente())
//                .orElseThrow(() -> new NotFoundException("Tipo Ambiente não encontrado"));
//
//
//        Ambiente ambiente = new Ambiente();
//        ambiente.setIdAmbiente(request.idAmbiente());
//        ambiente.setNumAmbiente(request.numAmbiente());
//        ambiente.setDescricaoAmbiente(request.descricaoAmbiente());
//        ambiente.setTipoAmbiente(tipoAmbiente);
//
//        //Agrupa os codigos dos equipamentos do dto
//        List<String> codigosEquipamentos = request.equipamentosList()
//                .stream()
//                .map(CodEquipamentoResponseDTO::codEquipamento)
//                .toList();
//        //Procura no banco
//        List<Equipamento> equipamentosSelecionados = equipamentoRepository.findAllByCodEquipamentoIn(codigosEquipamentos);
//
//        //Associa com Tipo Equipamento
//        Map<TipoEquipamento, List<Equipamento>> agrupados = equipamentosSelecionados.stream()
//                .collect(Collectors.groupingBy(Equipamento::getTipoEquipamento));
//
//        List<TipoEquipamento> tiposEquipamentos = new ArrayList<>(agrupados.keySet());
//        tiposEquipamentos.forEach(tipo -> tipo.setEquipamentoList(agrupados.get(tipo)));
//
//        //Salva no ambiente
//        ambiente.setTipoEquipamentoList(tiposEquipamentos);
//        ambienteRepository.save(ambiente);
//
//        List<CodEquipamentoResponseDTO> equipamentosList = ambiente.getTipoEquipamentoList()
//                .stream()
//                .flatMap(tipoEquipamento -> tipoEquipamento.getEquipamentoList().stream())
//                .map(equipamento -> new CodEquipamentoResponseDTO(
//                        equipamento.getCodEquipamento()
//                )).toList();
//
//        return new AmbienteRequestDTO(
//                ambiente.getIdAmbiente(),
//                ambiente.getNumAmbiente(),
//                ambiente.getDescricaoAmbiente(),
//                ambiente.getTipoAmbiente().getIdTipoAmbiente(),
//                ambiente.getTipoAmbiente().getNomeTipoAmbiente(),
//                equipamentosList
//        );
//    }

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
