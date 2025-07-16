package br.com.sime.api.services;

import br.com.sime.api.DTOs.TipoEquipamentoDTO;
import br.com.sime.api.entities.escola.equipamentos.Equipamento;
import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import br.com.sime.api.entities.usuarios.Permissao;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.TipoEquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TipoEquipamentoService {

    @Autowired
    private TipoEquipamentoRepository tipoEquipamentoRepository;

    public List<TipoEquipamento> getAllTipoEquipamentos(){
        try {
            return tipoEquipamentoRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException("Erro ao buscar os Tipos dos equipamentos: " + e.getMessage(), e);
        }
    }

    public TipoEquipamento getTipoEquipamentosById(Long idTipoEquipamento){
        return tipoEquipamentoRepository.findById(idTipoEquipamento)
                .orElseThrow(() -> new NotFoundException("Permissao de ID: " + idTipoEquipamento + "não encontrado"));
    }

    public TipoEquipamento criarTipoEquipamento(TipoEquipamentoDTO dto){

        TipoEquipamento tipoEquipamento = new TipoEquipamento();
        tipoEquipamento.setNomeTipoEquipamento(dto.getNomeTipoEquipamento());
        tipoEquipamento.setImgTipoEquipamento(dto.getImgTipoEquipamento());

        return tipoEquipamentoRepository.save(tipoEquipamento);
    }

    public TipoEquipamento editarTipoEquipamento(Long idTipoEquipamento, TipoEquipamentoDTO tipoEquipamentoDTO){
        TipoEquipamento tipoEquipamento = tipoEquipamentoRepository.findById(idTipoEquipamento)
                .orElseThrow(()-> new NotFoundException("Tipo equipamento de ID: " + idTipoEquipamento + "não encontrado"));

        tipoEquipamento.setNomeTipoEquipamento(tipoEquipamentoDTO.getNomeTipoEquipamento());
        tipoEquipamento.setImgTipoEquipamento(tipoEquipamentoDTO.getImgTipoEquipamento());
        return tipoEquipamentoRepository.save(tipoEquipamento);
    }

}
