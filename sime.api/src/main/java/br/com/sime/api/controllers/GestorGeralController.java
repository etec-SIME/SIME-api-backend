package br.com.sime.api.controllers;

import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.enums.PrioridadeChamadoEnum;
import br.com.sime.api.services.GestorGeralService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Gestor Geral", description = "Operações relacionadas ao gestor geral")
@RestController
@RequestMapping("/gestor-geral")
public class GestorGeralController {
    @Autowired
    private GestorGeralService gestorGeralService;

    @Operation(summary = "Chama todos os chamados", description = "Chama todos os chamados cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Requisição realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @GetMapping("/gestores/chamados")
    public ResponseEntity<List<Chamado>> getAllChamados(){
        List<Chamado> chamados = gestorGeralService.getAllChamados();
        return new ResponseEntity<>(chamados, HttpStatus.OK);
    }

    @Operation(summary = "Permite definir prioridade dos chamados", description = "Permite que o gestor geral defina prioridades aos chamados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Requisição realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PutMapping("/gestores/{rmGestor}/chamados/{idChamado}/prioridade")
    public ResponseEntity<Void> definirPrioridaeChamado(@PathVariable String rmGestor, @PathVariable Long idChamado, @RequestParam PrioridadeChamadoEnum novaPrioridade){
        gestorGeralService.definirPrioridadeChamado(rmGestor, idChamado, novaPrioridade);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Permite que o gestor geral aceite chamados", description = "Permite a aceitação dos chamados pelo gestor geral")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Requisição realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PutMapping("/gestores/{rmGestor}/chamados/{idChamado}/aceitar")
    public ResponseEntity<Void> aceitarChamado(@PathVariable String rmGestor,@PathVariable Long idChamado){
        gestorGeralService.aceitarChamado(idChamado);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Permite que o gestor geral recuse chamados", description = "Permite a recusa dos chamados pelo gestor geral")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Requisição realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PutMapping("/gestores/{rmGestor}/chamados/{idChamado}/recusar")
    public ResponseEntity<String> recusarChamado(@PathVariable String rmGestor, @PathVariable Long idChamado, @RequestBody String msgRecusa){
        gestorGeralService.recusarChamado(idChamado, msgRecusa);
        return new ResponseEntity<>(msgRecusa, HttpStatus.OK);
    }
}
