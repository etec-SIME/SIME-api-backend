package br.com.sime.api.controllers;

import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.outros.TipoPerfil;
import br.com.sime.api.services.UsuarioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public boolean login(@RequestParam String rmUsuario, @RequestParam TipoPerfil tipoPerfilUsuario, @RequestParam String senhaUsuario) {
        return usuarioService.login(rmUsuario, tipoPerfilUsuario, senhaUsuario);
    }

//    @PostMapping("/{rm}/chamado")
//    public Chamado criarChamado(@PathVariable String rm, @RequestParam TipoPerfil tipoPerfilUsuario,
//                                @RequestParam String tituloChamado, @RequestParam String descChamado,
//                                @RequestParam String localChamado) {
//        return usuarioService.criarChamado(rm, tipoPerfilUsuario, tituloChamado, descChamado, localChamado);
//    }
}
