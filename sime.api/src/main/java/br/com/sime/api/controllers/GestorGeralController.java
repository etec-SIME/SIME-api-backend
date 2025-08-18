package br.com.sime.api.controllers;

import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.enums.PrioridadeChamadoEnum;
import br.com.sime.api.services.GestorGeralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gestor-geral")
public class GestorGeralController {
    @Autowired
    private GestorGeralService gestorGeralService;

    @GetMapping("/gestores/chamados")
    public ResponseEntity<List<Chamado>> getAllChamados(){
        List<Chamado> chamados = gestorGeralService.getAllChamados();
        return new ResponseEntity<>(chamados, HttpStatus.OK);
    }

    @PutMapping("/gestores/{rmGestor}/chamados/{idChamado}/prioridade")
    public ResponseEntity<Void> definirPrioridaeChamado(@PathVariable String rmGestor, @PathVariable Long idChamado, @RequestParam PrioridadeChamadoEnum novaPrioridade){
        gestorGeralService.definirPrioridadeChamado(rmGestor, idChamado, novaPrioridade);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/gestores/{rmGestor}/chamados/{idChamado}/aceitar")
    public ResponseEntity<Void> aceitarChamado(@PathVariable String rmGestor,@PathVariable Long idChamado){
        gestorGeralService.aceitarChamado(idChamado);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/gestores/{rmGestor}/chamados/{idChamado}/recusar")
    public ResponseEntity<String> recusarChamado(@PathVariable String rmGestor, @PathVariable Long idChamado, @RequestBody String msgRecusa){
        gestorGeralService.recusarChamado(idChamado, msgRecusa);
        return new ResponseEntity<>(msgRecusa, HttpStatus.OK);
    }
}
