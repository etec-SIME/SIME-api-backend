package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.LoginDTO;
import br.com.sime.api.DTOs.TokenDTO;
import br.com.sime.api.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping
    public ResponseEntity<?> getAllUsuarios() {
        try {
            return new ResponseEntity<>(usuarioService.getAllUsuarios(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao buscar usuários: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO login) {
        TokenDTO token = usuarioService.login(login);
        return new ResponseEntity<>(new TokenDTO(token.getToken()), HttpStatus.OK);
    }
}
