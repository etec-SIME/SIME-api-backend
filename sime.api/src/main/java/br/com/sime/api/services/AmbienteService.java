package br.com.sime.api.services;
import br.com.sime.api.DTOs.AmbienteDTO;
import br.com.sime.api.DTOs.TipoEquipamentoAmbienteDTO;
import br.com.sime.api.entities.escola.ambiente.Ambiente;
import br.com.sime.api.entities.escola.ambiente.Tipo_Ambiente;
import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.AmbienteRepository;
import br.com.sime.api.repositories.EscolaRepository;
import br.com.sime.api.repositories.TipoAmbienteRepository;
import br.com.sime.api.repositories.TipoEquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AmbienteService {
    @Autowired
    private AmbienteRepository ambienteRepository;

    @Autowired
    private TipoEquipamentoRepository tipoEquipamentoRepository;

    @Autowired
    private TipoAmbienteRepository tipoAmbienteRepository;

    @Autowired
    private EscolaRepository escolaRepository;

    public List<Ambiente> getAllAmbientes(){
        try{
            return ambienteRepository.findAll();
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao buscar os ambientes: " + e.getMessage(), e);
        }
    }

    public Ambiente getAmbienteById(Long idAmbiente){
        return ambienteRepository.findById(idAmbiente)
                .orElseThrow(() -> new NotFoundException("Ambiente de ID: " + idAmbiente + "não encontrado"));
    }

    public Ambiente cadastrarAmbiente( AmbienteDTO dto){

        Tipo_Ambiente tipoAmbiente = tipoAmbienteRepository.findById(dto.getIdTipoAmbiente())
                .orElseThrow(() -> new NotFoundException("Tipo ambiente de ID: " + dto.getIdTipoAmbiente() + "não encontrado"));

        Ambiente ambiente = new Ambiente();
        ambiente.setNumAmbiente(dto.getNumAmbiente());
        ambiente.setDescricaoAmbiente(dto.getDescricaoAmbiente());
        ambiente.setTipoAmbiente(tipoAmbiente);

        return ambienteRepository.save(ambiente);
    }

    public List<TipoEquipamento> getAllTipoEquipamentoAmbiente(Long idAmbiente){
        Ambiente ambiente = ambienteRepository.findById(idAmbiente)
                .orElseThrow(() -> new NotFoundException("Ambiente de ID: " + idAmbiente + "não encontrado"));
        return ambiente.getTipoEquipamentoList();
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

        ambiente.setTipoEquipamentoList(tipoEquipamentos);

        return ambienteRepository.save(ambiente);
    }

    public Ambiente editarAmbiente(Long idAmbiente, AmbienteDTO ambienteDTO){
        Ambiente ambiente = ambienteRepository.findById(idAmbiente)
                .orElseThrow(() -> new NotFoundException("Ambiente de ID: " + idAmbiente + "não encontrado"));

        Tipo_Ambiente tipoAmbiente = tipoAmbienteRepository.findById(ambienteDTO.getIdTipoAmbiente())
                .orElseThrow(() -> new NotFoundException("Tipo ambiente de ID: " + ambienteDTO.getIdTipoAmbiente() + "não encontrado"));

        ambiente.setDescricaoAmbiente(ambienteDTO.getDescricaoAmbiente());
        ambiente.setNumAmbiente(ambienteDTO.getNumAmbiente());
        ambiente.setTipoAmbiente(tipoAmbiente);

        return ambienteRepository.save(ambiente);
    }
}
