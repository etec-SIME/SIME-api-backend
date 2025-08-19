package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.FeedbackDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.enums.PrioridadeChamadoEnum;
import br.com.sime.api.services.GestorDepartamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Gestor Departamento", description = "Operações relacionadas ao gestor departamento")
@RestController
@RequestMapping("/gestor-departamento")
public class GestorDepartamentoController {
    @Autowired
    private GestorDepartamentoService gestorDepartamentoService;

    @Operation(summary = "Chama os usuários pelo tipo de perfil e departamneto", description = "Exibição dos usuários pelo tipo de perfil e departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Requisição realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuariosByTipoPerfilAndDepartamento(@RequestParam Long departamentoId,
                                                                                 @RequestParam Long idTipoPerfil) {
        List<Usuario> usuarios = gestorDepartamentoService.getUsuariosByTipoPerfilAndDepartamento(departamentoId, idTipoPerfil);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @Operation(summary = "Permite a vizualização do chamado com base no departamento", description = "Exibe chamados de acordo com a requisição do departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Requisição realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @GetMapping("/gestores/{rmGestor}/chamados")
    public ResponseEntity<List<Chamado>> visualizarChamadoDepartamento(@PathVariable String rmGestor) {
        List<Chamado> chamados = gestorDepartamentoService.visualizarChamadoDepartamento(rmGestor);
        return new ResponseEntity<>(chamados, HttpStatus.OK);
    }

    @Operation(summary = "Permite o envio de feedbacks", description = "Permite que os tipos de perfil de GESTORES e ESCOLA enviem feedbacks sobre os chamados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Requisição realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PutMapping("/gestores/{rmGestor}/chamados/{idChamado}/feedback")
    public ResponseEntity<String> enviarFeedback(@PathVariable String rmGestor,
                                                 @PathVariable Long idChamado,
                                                 @RequestBody String descricaoFeedback) {
        gestorDepartamentoService.enviarFeedback(idChamado, rmGestor, descricaoFeedback);
        return new ResponseEntity<>(descricaoFeedback, HttpStatus.OK);
    }

    @Operation(summary = "Define prioridade aos chamados", description = "Permite que os chamados recebam um nível de prioridade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Requisição realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PutMapping("/gestores/{rmGestor}/chamados/{idChamado}/prioridade")
    public ResponseEntity<Void> definirPrioridadeChamado(@PathVariable String rmGestor,
                                                         @PathVariable Long idChamado,
                                                         @RequestParam PrioridadeChamadoEnum novaPrioridade) {
        gestorDepartamentoService.definirPrioridadeChamado(rmGestor, idChamado, novaPrioridade);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
