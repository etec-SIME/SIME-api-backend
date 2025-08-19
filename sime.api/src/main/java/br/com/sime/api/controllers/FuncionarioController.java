package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.ChamadoRequestDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.services.FuncionarioService;
import br.com.sime.api.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Funcionários",description = "Operações relacionadas aos funcionários")
@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;

    @Operation(summary = "Chama os funcionários", description = "Chama todos os funcionários cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Requisição realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PreAuthorize("hasPermission('Admin')") //"permitAll()"
    @GetMapping
    public ResponseEntity<?> getAllFuncionarios() {
        return new ResponseEntity<>(funcionarioService.getAllFuncionarios(), HttpStatus.OK);
    }

    @Operation(summary = "Chama os chamados pendentes do funcionário", description = "Chama todos os chamados pendentes feitos pelo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Requisição realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PreAuthorize("hasPermission('Funcionario')") //"permitAll()"
    @GetMapping("/pendentes")
    public ResponseEntity<?> getAllChamadosPendentes() {
        List<Chamado> chamadosPendentes = funcionarioService.getAllChamadosPendentes();
        return new ResponseEntity<>(chamadosPendentes, HttpStatus.OK);
    }

    @Operation(summary = "Chama os chamados concluídos do funcionário", description = "Chama todos os chamados concluídos feitos pelo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Requisição realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PreAuthorize("hasPermission('Funcionario')" ) //"permitAll()"
    @GetMapping("/concluidos")
    public ResponseEntity<?> getAllChamadosConcluidos() {
        List<Chamado> chamadosConcluidos = funcionarioService.getAllChamadosConcluidos();
        return new ResponseEntity<>(chamadosConcluidos, HttpStatus.OK);
    }

}
