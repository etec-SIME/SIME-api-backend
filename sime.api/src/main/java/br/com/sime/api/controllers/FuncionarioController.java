package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.ChamadoRequestDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.services.FuncionarioService;
import br.com.sime.api.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping
    public ResponseEntity<?> getAllFuncionarios() {
        try {
            return new ResponseEntity<>(funcionarioService.getAllFuncionarios(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao buscar funcionários: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('Funcionario')")
    @GetMapping("/pendentes")
    public ResponseEntity<?> getAllChamadosPendentes() {
        try {
            List<Chamado> chamadosPendentes = funcionarioService.getAllChamadosPendentes();
            return new ResponseEntity<>(chamadosPendentes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao buscar chamados pendentes: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('Funcionario')")
    @GetMapping("/concluidos")
    public ResponseEntity<?> getAllChamadosConcluidos() {
        try {
            List<Chamado> chamadosConcluidos = funcionarioService.getAllChamadosConcluidos();
            return new ResponseEntity<>(chamadosConcluidos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao buscar chamados concluidos: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
