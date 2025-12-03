package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.Responses.TipoPerfilResponseDTO;
import br.com.sime.api.entities.escola.ambiente.Ambiente;
import br.com.sime.api.entities.escola.equipamentos.Equipamento;
import br.com.sime.api.repositories.AmbienteRepository;
import br.com.sime.api.services.EquipamentoService;
import br.com.sime.api.services.TipoPerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private TipoPerfilService tipoPerfilService;

    @Autowired
    private AmbienteRepository ambienteRepository;

    @Autowired
    private EquipamentoService equipamentoService;

    @GetMapping("/tipos-perfil")
    public ResponseEntity<List<TipoPerfilResponseDTO>> getTipoPerfilNomes() {
        List<TipoPerfilResponseDTO> tipoPerfis = tipoPerfilService.getTipoPerfilNomes();
        return ResponseEntity.ok(tipoPerfis);
    }

    @GetMapping("")
    public List<Ambiente> getAllAmbientes() {
        return ambienteRepository.findAll();
    }

    @GetMapping("teste")
    public Equipamento getEquipamentoByCodEquipamento(@RequestParam String codEquipamento) {
        return equipamentoService.getEquipamentoByCodEquipamento(codEquipamento);
    }
}
