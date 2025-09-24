package br.com.sime.api.services;

import br.com.sime.api.DTOs.TipoChamadoDTO;
import br.com.sime.api.DTOs.Responses.TipoChamadoResponseDTO;
import br.com.sime.api.DTOs.TipoChamadoSelectDTO;
import br.com.sime.api.entities.chamados.TipoChamado;
import br.com.sime.api.entities.outros.Departamento;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.DepartamentoRepository;
import br.com.sime.api.repositories.TipoChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoChamadoService {
    @Autowired
    private TipoChamadoRepository tipoChamadoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public List<TipoChamadoResponseDTO> getAllTipoChamado(){
        try {
            List<TipoChamado> tipoChamadoList =  tipoChamadoRepository.findAll();

            return tipoChamadoList.stream().map(tipoChamado -> {
                TipoChamadoResponseDTO tipoChamadoResponseDTO = new TipoChamadoResponseDTO();
                tipoChamadoResponseDTO.setIdTipoChamado(tipoChamado.getIdTipoChamado());
                tipoChamadoResponseDTO.setNomeTipoChamado(tipoChamado.getNomeTipoChamado());
                tipoChamadoResponseDTO.setDepartamento(tipoChamado.getDepartamento());
                return tipoChamadoResponseDTO;}).collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException("Erro ao buscar os tipo de chamado: " + e.getMessage(), e);
        }
    }

    public List<TipoChamadoSelectDTO> getAllTipoChamadoSelect(){
        return tipoChamadoRepository.findAll()
                .stream()
                .map(tc -> new TipoChamadoSelectDTO(
                        tc.getIdTipoChamado(),
                        tc.getNomeTipoChamado()
                ))
                .collect(Collectors.toList());
    }

    public TipoChamadoResponseDTO criarTipoChamado(TipoChamadoDTO dto){
        Departamento departamento = departamentoRepository.findById(dto.getIdDepartamento())
                .orElseThrow(() -> new NotFoundException("Departamento de ID: " + dto.getIdDepartamento() + "não encontrado"));

        TipoChamado tipoChamado = new TipoChamado();
        tipoChamado.setNomeTipoChamado(dto.getNomeTipoChamado());
        tipoChamado.setDepartamento(departamento);
        tipoChamadoRepository.save(tipoChamado);

        TipoChamadoResponseDTO tipoChamadoResponseDTO = new TipoChamadoResponseDTO();
        tipoChamadoResponseDTO.setIdTipoChamado(tipoChamado.getIdTipoChamado());
        tipoChamadoResponseDTO.setNomeTipoChamado(tipoChamado.getNomeTipoChamado());
        tipoChamadoResponseDTO.setDepartamento(tipoChamado.getDepartamento());

        return tipoChamadoResponseDTO;
    }

    public TipoChamadoResponseDTO editarTipoChamado(Long idTipoChamado, TipoChamadoDTO tipoChamadoDTO){
        TipoChamado tipoChamado = tipoChamadoRepository.findById(idTipoChamado)
                .orElseThrow(() -> new NotFoundException("Tipo chamado de ID: " + idTipoChamado + "não encontrado"));

        Departamento departamento = departamentoRepository.findById(tipoChamadoDTO.getIdDepartamento())
                .orElseThrow(() -> new NotFoundException("Departamento de ID: " + tipoChamadoDTO.getIdDepartamento() + "não encontrado"));

        tipoChamado.setNomeTipoChamado(tipoChamadoDTO.getNomeTipoChamado());
        tipoChamado.setDepartamento(departamento);
        tipoChamadoRepository.save(tipoChamado);

        TipoChamadoResponseDTO tipoChamadoResponseDTO = new TipoChamadoResponseDTO();
        tipoChamadoResponseDTO.setIdTipoChamado(tipoChamado.getIdTipoChamado());
        tipoChamadoResponseDTO.setNomeTipoChamado(tipoChamadoDTO.getNomeTipoChamado());
        tipoChamadoResponseDTO.setDepartamento(tipoChamado.getDepartamento());

        return tipoChamadoResponseDTO;
    }
}
