package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.ChamadoRequestDTO;
import br.com.sime.api.DTOs.LoginDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> getAllUsuarios() {
        try {
            return new ResponseEntity<>(usuarioService.getAllUsuarios(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao buscar usuários: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody LoginDTO dto) {
        usuarioService.login(dto.getRmUsuario(), dto.getSenhaUsuario(), dto.getIdTipoPerfil(), dto.getCodEscola());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/{rmUsuario}/chamado")
    public ResponseEntity<?> criarChamado(@PathVariable String rmUsuario, @Valid @RequestBody ChamadoRequestDTO ChamadoDTO) {
        Chamado newChamado = usuarioService.criarChamado(rmUsuario, ChamadoDTO);
        return new ResponseEntity<>(newChamado, HttpStatus.CREATED);
    }
}
