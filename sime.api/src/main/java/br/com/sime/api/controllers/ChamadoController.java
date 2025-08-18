package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.ChamadoCardDTO;
import br.com.sime.api.DTOs.ChamadoRequestDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.enums.PrioridadeChamadoEnum;
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

    @GetMapping("/prioridade")
    public ResponseEntity<List<ChamadoCardDTO>> getChamadosByPrioridade(@RequestParam("prioridade") PrioridadeChamadoEnum prioridade) {
        List<ChamadoCardDTO> chamados = chamadoService.getByPrioridadeChamado(prioridade);
        return ResponseEntity.ok(chamados);
    }
}
