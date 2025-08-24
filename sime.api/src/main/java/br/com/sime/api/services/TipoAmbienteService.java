package br.com.sime.api.services;
import br.com.sime.api.DTOs.DepartamentoDTO;
import br.com.sime.api.DTOs.TipoAmbienteDTO;
import br.com.sime.api.entities.escola.ambiente.Ambiente;
import br.com.sime.api.entities.escola.ambiente.Tipo_Ambiente;
import br.com.sime.api.entities.outros.Departamento;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.AmbienteRepository;
import br.com.sime.api.repositories.TipoAmbienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoAmbienteService {

    @Autowired
    private TipoAmbienteRepository tipoAmbienteRepository;

    @Autowired
    private AmbienteRepository ambienteRepository;


    public List<Tipo_Ambiente> getAllTipoAmbiente(){
        try{
            return tipoAmbienteRepository.findAll();
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao buscar os tipos de ambientes: " + e.getMessage(), e);
        }
    }

    public Tipo_Ambiente getTipoAmbienteById(Long idTipoAmbiente){
        return tipoAmbienteRepository.findById(idTipoAmbiente)
                .orElseThrow(() -> new NotFoundException("Ambiente de ID: " + idTipoAmbiente + "não encontrado"));
    }

    public Tipo_Ambiente criarTipoAmbiente(TipoAmbienteDTO dto) {

        Tipo_Ambiente tipoAmbiente = new Tipo_Ambiente();
        tipoAmbiente.setNomeTipoAmbiente(dto.getNomeTipoAmbiente());

        return tipoAmbienteRepository.save(tipoAmbiente);
    }

    public Tipo_Ambiente editarTipoAmbiente(Long idTipoAmbiente, TipoAmbienteDTO dto){
        Tipo_Ambiente tipoAmbiente = tipoAmbienteRepository.findById(idTipoAmbiente)
                .orElseThrow(() -> new NotFoundException("Departamento de ID: " + idTipoAmbiente + "não encontrado"));

        tipoAmbiente.setNomeTipoAmbiente(dto.getNomeTipoAmbiente());

        return tipoAmbienteRepository.save(tipoAmbiente);
    }

}
