package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.ChamadoRequestDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.services.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {
    @Autowired
    private ChamadoService chamadoService;

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping
    public ResponseEntity<List<Chamado>> getAllChamados() {
        return new ResponseEntity<>(chamadoService.getAllChamados(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Criar Chamado')")
    @PostMapping("/{rmUsuario}/chamado")
    public ResponseEntity<?> criarChamado(@PathVariable String rmUsuario, @Valid @RequestBody ChamadoRequestDTO ChamadoDTO) {
        Chamado chamado = chamadoService.criarChamado(rmUsuario, ChamadoDTO);
        return new ResponseEntity<>(chamado, HttpStatus.CREATED);
    }
}
