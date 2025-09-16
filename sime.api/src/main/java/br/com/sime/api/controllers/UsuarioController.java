package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.LoginDTO;
import br.com.sime.api.DTOs.TokenDTO;
import br.com.sime.api.DTOs.Projections.UsuarioProjection;
import br.com.sime.api.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuário", description = "Operações relacionadas aos usuários em geral")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasPermission('Admin')")


    @Operation(summary = "Chama todos os usuários", description = "Exibe a lista de todos os usuários cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Requisição realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioProjection>> getAllUsuarios() {
        return new ResponseEntity<>(usuarioService.getAllUsuarios(), HttpStatus.OK);
    }

    @Operation(summary = "Realizar login", description = "Permite que os usuários realizem seu login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Login realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
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
