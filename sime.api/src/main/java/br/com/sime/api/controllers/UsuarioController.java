package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.LoginDTO;
import br.com.sime.api.DTOs.TokenDTO;
import br.com.sime.api.DTOs.Projections.UsuarioProjection;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.services.UsuarioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasPermission('Admin')")
    @GetMapping
    public ResponseEntity<List<UsuarioProjection>> getAllUsuarios() {
        return new ResponseEntity<>(usuarioService.getAllUsuarios(), HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO login, HttpServletResponse response) {
        TokenDTO token = usuarioService.login(login);

        Cookie cookie = new Cookie("jwt", token.getToken());

        cookie.setHttpOnly(true); // 🔒 Não acessível por JS
        cookie.setSecure(false);   // 🔒 Só HTTPS (dev = false, prod = true)
        cookie.setPath("/");      // válido para toda a aplicação
        cookie.setMaxAge(60 * 60); // 1h

        response.addCookie(cookie);

        return new ResponseEntity<>(new TokenDTO(token.getToken()), HttpStatus.OK);
    }
}
