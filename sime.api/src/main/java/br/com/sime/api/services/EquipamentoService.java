package br.com.sime.api.services;

import br.com.sime.api.DTOs.Requests.EquipamentoRequestDTO;
import br.com.sime.api.DTOs.Responses.CodEquipamentoResponseDTO;
import br.com.sime.api.DTOs.Responses.EquipamentoResponseDTO;
import br.com.sime.api.entities.escola.ambiente.Ambiente;
import br.com.sime.api.entities.escola.equipamentos.Equipamento;
import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.AmbienteRepository;
import br.com.sime.api.repositories.EquipamentoRepository;
import br.com.sime.api.repositories.TipoEquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EquipamentoService {
    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Autowired
    private TipoEquipamentoRepository tipoEquipamentoRepository;

    @Autowired
    private AmbienteRepository ambienteRepository;

    public List<EquipamentoResponseDTO> getAllEquipamentos(){
        try {
            List<Equipamento> equipamentoList = equipamentoRepository.findAll();

             return equipamentoList.stream()
                    .map( equipamento -> (
                        new EquipamentoResponseDTO(
                                equipamento.getCodEquipamento(),
                                equipamento.getTipoEquipamento().getIdTipoEquipamento()
                        ))).collect(Collectors.toList());

        } catch (Exception e){
            throw new RuntimeException("Erro ao buscar os equipamentos:"+ e.getMessage(), e);
        }
    }

    public Equipamento getEquipamentoByCodEquipamento(String codEquipamento){
        return equipamentoRepository.findByCodEquipamento(codEquipamento).orElseThrow(() -> new NotFoundException("Equipamento de ID: " + codEquipamento + "não encontrado"));
    }

    public List<CodEquipamentoResponseDTO> getEquipamentosSemAmbiente(){
        List<Ambiente> ambientes = ambienteRepository.findAll();
        List<Equipamento> equipamentos = equipamentoRepository.findAll();

        Set<String> equipamentosComAmbiente = ambientes.stream()
                .flatMap(ambiente -> ambiente.getEquipamentoList().stream())
                .map(Equipamento::getCodEquipamento)
                .collect(Collectors.toSet());

        List<CodEquipamentoResponseDTO> equipamentosSemAmbiente =  equipamentos.stream()
                .filter(equipamento -> !equipamentosComAmbiente.contains(equipamento.getCodEquipamento()))
                .map(equipamento -> new CodEquipamentoResponseDTO(equipamento.getCodEquipamento(), equipamento.getTipoEquipamento().getIdTipoEquipamento()))
                .collect(Collectors.toList());

        return equipamentosSemAmbiente;
    }

    public EquipamentoRequestDTO cadastrarEquipamento(EquipamentoRequestDTO dto){
        TipoEquipamento tipoEquipamento = tipoEquipamentoRepository.findById(dto.idTipoEquipamento())
                .orElseThrow(() -> new NotFoundException("Tipo equipamento de código: " + dto.idTipoEquipamento() + " não encontrado"));

        Equipamento equipamento = new Equipamento();
        equipamento.setCodEquipamento(dto.codEquipamento());
        equipamento.setTipoEquipamento(tipoEquipamento);

        equipamentoRepository.save(equipamento);

        return new EquipamentoRequestDTO(
                equipamento.getCodEquipamento(),
                equipamento.getTipoEquipamento().getIdTipoEquipamento()
        );
    }

    public EquipamentoResponseDTO editarEquipamento(Long codEquipamento, EquipamentoRequestDTO equipamentoDTO){
        Equipamento equipamento = equipamentoRepository.findById(codEquipamento)
                .orElseThrow(() -> new NotFoundException("Equipamento de código: " + codEquipamento + "não encontrado"));

        TipoEquipamento tipoEquipamento = tipoEquipamentoRepository.findById(equipamentoDTO.idTipoEquipamento())
                        .orElseThrow(() -> new NotFoundException("Tipo equipamenro de ID: " + equipamentoDTO.idTipoEquipamento()+ "não encontrado"));

        equipamento.setCodEquipamento(equipamentoDTO.codEquipamento());
        equipamento.setTipoEquipamento(tipoEquipamento);

        equipamentoRepository.save(equipamento);

        return new EquipamentoResponseDTO(
                equipamento.getCodEquipamento(),
                equipamento.getTipoEquipamento().getIdTipoEquipamento()
        );
    }
}
