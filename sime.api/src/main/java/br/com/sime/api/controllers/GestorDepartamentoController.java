package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.ChamadoRequestDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.services.GestorDepartamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gestor-departamento")
public class GestorDepartamentoController {
    @Autowired
    private GestorDepartamentoService gestorDepartamentoService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuariosByTipoPerfilAndDepartamento(@RequestParam Long departamentoId,
                                                                                 @RequestParam Long idTipoPerfil) {
        List<Usuario> usuarios = gestorDepartamentoService.getUsuariosByTipoPerfilAndDepartamento(departamentoId, idTipoPerfil);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
}
