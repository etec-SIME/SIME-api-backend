package br.com.sime.api.services;
import br.com.sime.api.DTOs.AmbienteDTO;
import br.com.sime.api.DTOs.AmbienteSelectDTO;
import br.com.sime.api.DTOs.TipoEquipamentoAmbienteDTO;
import br.com.sime.api.entities.escola.ambiente.Ambiente;
import br.com.sime.api.entities.escola.ambiente.TipoAmbiente;
import br.com.sime.api.entities.escola.equipamentos.Equipamento;
import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.AmbienteRepository;
import br.com.sime.api.repositories.EscolaRepository;
import br.com.sime.api.repositories.TipoAmbienteRepository;
import br.com.sime.api.repositories.TipoEquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

        public AmbienteDTO cadastrarAmbiente(AmbienteDTO dto){

        TipoAmbiente tipoAmbiente = tipoAmbienteRepository.findById(dto.getIdTipoAmbiente())
                .orElseThrow(() -> new NotFoundException("Tipo ambiente de ID: " + dto.getIdTipoAmbiente() + "não encontrado"));

        Ambiente ambiente = new Ambiente();
        ambiente.setNumAmbiente(dto.getNumAmbiente());
        ambiente.setDescricaoAmbiente(dto.getDescricaoAmbiente());
        ambiente.setTipoAmbiente(tipoAmbiente);

        ambienteRepository.save(ambiente);

        AmbienteDTO ambienteDTO = new AmbienteDTO();
        ambienteDTO.setNumAmbiente(ambiente.getNumAmbiente());
        ambienteDTO.setDescricaoAmbiente(ambiente.getDescricaoAmbiente());
        ambienteDTO.setIdTipoAmbiente(ambiente.getTipoAmbiente().getIdTipoAmbiente());

        return ambienteDTO;
    }

//    public List<Equipamento> getAllTipoEquipamentoAmbiente(Long idAmbiente){
//        Ambiente ambiente = ambienteRepository.findById(idAmbiente)
//                .orElseThrow(() -> new NotFoundException("Ambiente de ID: " + idAmbiente + "não encontrado"));
//        return ambiente.getEquipamentoList();
//    }

//    public Ambiente atribuirTipoEquipamentos(Long idAmbiente, TipoEquipamentoAmbienteDTO dto){
//        Ambiente ambiente =  ambienteRepository.findById(idAmbiente)
//                .orElseThrow(() -> new NotFoundException("Ambiente de ID: " + idAmbiente + "não encontrado"));
//
//        List<TipoEquipamento> tipoEquipamentos = tipoEquipamentoRepository.findAllById(dto.getIdsTipoEquipamento());
//
//        Set<Long> idsEncontrados = tipoEquipamentos.stream()
//                .map(TipoEquipamento::getIdTipoEquipamento)
//                .collect(Collectors.toSet());
//
//        List<Long> idsNaoEncontrados = dto.getIdsTipoEquipamento().stream()
//                .filter( id -> !idsEncontrados.contains(id))//Verifica se o idPermissao existe
//                .toList();
//
//        if (!idsNaoEncontrados.isEmpty()) {
//            throw new NotFoundException(
//                    "Tipos de equipamento não encontradas",
//                    "IDs inválidos: " + idsNaoEncontrados
//            );
//        }
//
//        ambiente.setEquipamentoList(tipoEquipamentos);
//
//        return ambienteRepository.save(ambiente);
//    }

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
