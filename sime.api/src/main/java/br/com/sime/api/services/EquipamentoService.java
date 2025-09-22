package br.com.sime.api.services;

import br.com.sime.api.DTOs.EquipamentoDTO;
import br.com.sime.api.DTOs.EquipamentoResponseDTO;
import br.com.sime.api.entities.escola.equipamentos.Equipamento;
import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.EquipamentoRepository;
import br.com.sime.api.repositories.TipoEquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipamentoService {
    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Autowired
    private TipoEquipamentoRepository tipoEquipamentoRepository;

    public List<EquipamentoResponseDTO> getAllEquipamentos(){
        try {
            List<Equipamento> equipamentoList = equipamentoRepository.findAll();

             return equipamentoList.stream()
                    .map( equipamento ->{
                        EquipamentoResponseDTO equipamentoResponseDTO = new EquipamentoResponseDTO();
                        equipamentoResponseDTO.setCodEquipamento(equipamento.getCodEquipamento());
                        equipamentoResponseDTO.setIdTipoEquipamento(equipamento.getTipoEquipamento().getIdTipoEquipamento());
                        return equipamentoResponseDTO;}).collect(Collectors.toList());

        }catch (Exception e){
            throw new RuntimeException("Erro ao buscar os equipamentos:"+ e.getMessage(), e);
        }
    }

    public Equipamento getEquipamentoById(Long idEquipamento){
        return equipamentoRepository.findById(idEquipamento)
                .orElseThrow(() -> new NotFoundException("Equipamento de ID: " + idEquipamento + "não encontrado"));
    }

    //getEquipamentoByTipoEquipamento
    //getEquipamentoByAmbiente

    public EquipamentoResponseDTO cadastrarEquipamento(EquipamentoDTO dto){
        TipoEquipamento tipoEquipamento = tipoEquipamentoRepository.findById(dto.getIdTipoEquipamento())
                .orElseThrow(() -> new NotFoundException("Tipo equipamento de código: " + dto.getIdTipoEquipamento() + "não encontrado"));

        Equipamento equipamento = new Equipamento();
        equipamento.setCodEquipamento(dto.getCodEquipamento());
        equipamento.setTipoEquipamento(tipoEquipamento);
        equipamentoRepository.save(equipamento);

        EquipamentoResponseDTO equipamentoResponseDTO = new EquipamentoResponseDTO();
        equipamentoResponseDTO.setCodEquipamento(equipamento.getCodEquipamento());
        equipamentoResponseDTO.setIdTipoEquipamento(equipamento.getTipoEquipamento().getIdTipoEquipamento());

        return equipamentoResponseDTO;
    }

    public EquipamentoResponseDTO editarEquipamento(Long codEquipamento, EquipamentoDTO equipamentoDTO){
        Equipamento equipamento = equipamentoRepository.findById(codEquipamento)
                .orElseThrow(() -> new NotFoundException("Equipamento de código: " + codEquipamento + "não encontrado"));

        TipoEquipamento tipoEquipamento = tipoEquipamentoRepository.findById(equipamentoDTO.getIdTipoEquipamento())
                        .orElseThrow(() -> new NotFoundException("Tipo equipamenro de ID: " + equipamentoDTO.getIdTipoEquipamento()+ "não encontrado"));

        equipamento.setCodEquipamento(equipamentoDTO.getCodEquipamento());
        equipamento.setTipoEquipamento(tipoEquipamento);

        equipamentoRepository.save(equipamento);

        EquipamentoResponseDTO equipamentoResponseDTO = new EquipamentoResponseDTO();
        equipamentoResponseDTO.setCodEquipamento(equipamento.getCodEquipamento());
        equipamentoResponseDTO.setIdTipoEquipamento(equipamento.getTipoEquipamento().getIdTipoEquipamento());
        return equipamentoResponseDTO;
    }
}
