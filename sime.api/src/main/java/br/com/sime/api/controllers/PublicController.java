package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.Responses.TipoPerfilResponseDTO;
import br.com.sime.api.services.TipoPerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private TipoPerfilService tipoPerfilService;

    @GetMapping("/tipos-perfil")
    public ResponseEntity<List<TipoPerfilResponseDTO>> getTipoPerfilNomes() {
        List<TipoPerfilResponseDTO> tipoPerfis = tipoPerfilService.getTipoPerfilNomes();
        return ResponseEntity.ok(tipoPerfis);
    }
}
