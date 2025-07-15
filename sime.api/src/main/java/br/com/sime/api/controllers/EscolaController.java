package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.LoginEscolaDTO;
import br.com.sime.api.DTOs.TokenDTO;
import br.com.sime.api.services.EscolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/escolas")
public class EscolaController {
    @Autowired
    private EscolaService escolaService;

    @PostMapping("login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginEscolaDTO login) {
        TokenDTO token = escolaService.loginEscola(login);
        return new ResponseEntity<>(new TokenDTO(token.getToken()), HttpStatus.OK);
    }
}
