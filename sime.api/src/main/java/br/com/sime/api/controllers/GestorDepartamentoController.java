package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.FeedbackDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.enums.PrioridadeChamadoEnum;
import br.com.sime.api.services.GestorDepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gestor-departamento")
public class GestorDepartamentoController {
    @Autowired
    private GestorDepartamentoService gestorDepartamentoService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuariosByTipoPerfilAndDepartamento(@RequestParam Long departamentoId,
                                                                                 @RequestParam Long idTipoPerfil) {
        List<Usuario> usuarios = gestorDepartamentoService.getUsuariosByTipoPerfilAndDepartamento(departamentoId, idTipoPerfil);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/gestores/{rmGestor}/chamados")
    public ResponseEntity<List<Chamado>> visualizarChamadoDepartamento(@PathVariable String rmGestor) {
        List<Chamado> chamados = gestorDepartamentoService.visualizarChamadoDepartamento(rmGestor);
        return new ResponseEntity<>(chamados, HttpStatus.OK);
    }

    @PutMapping("/gestores/{rmGestor}/chamados/{idChamado}/feedback")
    public ResponseEntity<String> enviarFeedback(@PathVariable String rmGestor,
                                                 @PathVariable Long idChamado,
                                                 @RequestBody String descricaoFeedback) {
        gestorDepartamentoService.enviarFeedback(idChamado, rmGestor, descricaoFeedback);
        return new ResponseEntity<>(descricaoFeedback, HttpStatus.OK);
    }

    @PutMapping("/gestores/{rmGestor}/chamados/{idChamado}/prioridade")
    public ResponseEntity<Void> definirPrioridadeChamado(@PathVariable String rmGestor,
                                                         @PathVariable Long idChamado,
                                                         @RequestParam PrioridadeChamadoEnum novaPrioridade) {
        gestorDepartamentoService.definirPrioridadeChamado(rmGestor, idChamado, novaPrioridade);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
