package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.ChamadoCardDTO;
import br.com.sime.api.DTOs.ChamadoRequestDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.enums.PrioridadeChamadoEnum;
import br.com.sime.api.enums.StatusChamadoEnum;
import br.com.sime.api.services.ChamadoService;
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

@Tag(name = "Chamados", description = "Operações relacionadas aos chamados de manutenção")
@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    @Autowired
    private ChamadoService chamadoService;

    @Operation(summary = "Chama todos os chamados", description = "Exibe a lista de todos os chamdos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "requisição realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PreAuthorize("hasPermission('Admin')")
    @GetMapping
    public ResponseEntity<List<Chamado>> getAllChamados() {
        return new ResponseEntity<>(chamadoService.getAllChamados(), HttpStatus.OK);
    }

    @Operation(summary = "Cria um novo chamado", description = "Cria um chamado com os dados enviados no corpo da requisição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Chamado criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PreAuthorize("hasPermission('Criar Chamado')")
    @PostMapping("/{rmUsuario}/chamado")
    public ResponseEntity<Chamado> criarChamado(@PathVariable String rmUsuario, @Valid @RequestBody ChamadoRequestDTO ChamadoDTO) {
        Chamado chamado = chamadoService.criarChamado(rmUsuario, ChamadoDTO);
        return new ResponseEntity<>(chamado, HttpStatus.CREATED);
    }

    @Operation(summary = "Define prioridade ao chamado", description = "Define uma prioridade ao chamado criado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Prioridade atribuida com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @GetMapping("/prioridade")
    public ResponseEntity<List<ChamadoCardDTO>> getChamadosByPrioridade(@RequestParam("prioridade") PrioridadeChamadoEnum prioridade) {
        List<ChamadoCardDTO> chamados = chamadoService.getByPrioridadeChamado(prioridade);
        return ResponseEntity.ok(chamados);
    }

    @Operation(summary = "Chama o chamado por prioridade e status", description = "Exibe a lsita de chamados separando-os por prioridade e status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Requisição exibida com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @GetMapping("/prioridade/concluidos")
    public ResponseEntity<List<ChamadoCardDTO>> getByPrioridadeStatusChamado(@RequestParam("prioridade") PrioridadeChamadoEnum prioridade, @RequestParam("status")StatusChamadoEnum status) {
        List<ChamadoCardDTO> chamados = chamadoService.getByPrioridadeStatusChamado(prioridade, status);
        return ResponseEntity.ok(chamados);
    }
}
