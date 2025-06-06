package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.ChamadoRequestDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.services.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {
    @Autowired
    private ChamadoService chamadoService;

    @PostMapping("/{rmUsuario}/chamado")
    public ResponseEntity<?> criarChamado(@PathVariable String rmUsuario, @Valid @RequestBody ChamadoRequestDTO ChamadoDTO) {
        Chamado chamado = chamadoService.criarChamado(rmUsuario, ChamadoDTO);
        return new ResponseEntity<>(chamado, HttpStatus.CREATED);
    }
}
