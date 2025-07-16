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

    @PreAuthorize("hasAuthority('Admin')") //"permitAll()"
    @GetMapping
    public ResponseEntity<?> getAllFuncionarios() {
        return new ResponseEntity<>(funcionarioService.getAllFuncionarios(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Funcionario')") //"permitAll()"
    @GetMapping("/pendentes")
    public ResponseEntity<?> getAllChamadosPendentes() {
        List<Chamado> chamadosPendentes = funcionarioService.getAllChamadosPendentes();
        return new ResponseEntity<>(chamadosPendentes, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Funcionario')" ) //"permitAll()"
    @GetMapping("/concluidos")
    public ResponseEntity<?> getAllChamadosConcluidos() {
        List<Chamado> chamadosConcluidos = funcionarioService.getAllChamadosConcluidos();
        return new ResponseEntity<>(chamadosConcluidos, HttpStatus.OK);
    }

}
