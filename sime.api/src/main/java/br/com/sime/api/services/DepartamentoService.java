package br.com.sime.api.services;

import br.com.sime.api.DTOs.DepartamentoDTO;
import br.com.sime.api.entities.outros.Departamento;
import br.com.sime.api.exceptions.ConflictException;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public List<Departamento> getAllDepartamentos() {
        try {
            return departamentoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar os departamentos: " + e.getMessage(), e);
        }
    }

    public Departamento getDepartamentoById(Long idDepartamento){
             return departamentoRepository.findById(idDepartamento)
                     .orElseThrow(() -> new NotFoundException("Departamento de ID: " + idDepartamento + "não encontrado"));

    }

    public Departamento criarDepartamento(DepartamentoDTO dto) {

        Departamento departamento = new Departamento();
        departamento.setNomeDepartamento(dto.getNomeDepartamento());
        departamento.setDescDepartamento(dto.getDescDepartamento());

        return departamentoRepository.save(departamento);
    }

    public Departamento editarDepartamento(Long idDepartamento, DepartamentoDTO departamentoDTO){
        Departamento departamento = departamentoRepository.findById(idDepartamento)
                .orElseThrow(() -> new NotFoundException("Departamento de ID: " + idDepartamento + "não encontrado"));

        departamento.setNomeDepartamento(departamentoDTO.getNomeDepartamento());
        departamento.setDescDepartamento(departamentoDTO.getDescDepartamento());
        return departamentoRepository.save(departamento);
    }


}
