package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.ChamadoDTO;
import br.com.sime.api.DTOs.LoginDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.services.UsuarioService;
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
        boolean loginSucesso = usuarioService.login(dto.getRmUsuario(), dto.getSenhaUsuario(), dto.getIdTipoPerfil(), dto.getCodEscola());
        return loginSucesso
                ? new ResponseEntity<>(true, HttpStatus.OK)
                : new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/{rmUsuario}/chamado")
    public ResponseEntity<Chamado> criarChamado(@RequestBody ChamadoDTO chamadoDTO, @PathVariable String rmUsuario) {
        Chamado newChamado = usuarioService.criarChamado(
                rmUsuario,
                chamadoDTO.getIdTipoPerfil(),
                chamadoDTO.getTituloChamado(),
                chamadoDTO.getDescChamado(),
                chamadoDTO.getLocalChamado()
        );

        return newChamado != null
                ? new ResponseEntity<>(newChamado, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
