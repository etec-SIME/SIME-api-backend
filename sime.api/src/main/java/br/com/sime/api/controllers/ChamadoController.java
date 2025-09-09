package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.AmbienteSelectDTO;
import br.com.sime.api.DTOs.ChamadoCardDTO;
import br.com.sime.api.DTOs.ChamadoRequestDTO;
import br.com.sime.api.DTOs.TipoChamadoSelectDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.enums.PrioridadeChamadoEnum;
import br.com.sime.api.enums.StatusChamadoEnum;
import br.com.sime.api.security.services.JwtService;
import br.com.sime.api.services.AmbienteService;
import br.com.sime.api.services.ChamadoService;
import br.com.sime.api.services.TipoChamadoService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {
    @Autowired
    private ChamadoService chamadoService;

    @Autowired
    private AmbienteService ambienteService;

    @Autowired
    private TipoChamadoService tipoChamadoService;

    @Autowired
    private JwtService jwtService;

    @PreAuthorize("hasPermission('Admin')")
    @GetMapping
    public ResponseEntity<List<Chamado>> getAllChamados() {
        return new ResponseEntity<>(chamadoService.getAllChamados(), HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('Criar Chamado')")
    @PostMapping("/criar-chamado")
    public ResponseEntity<Void> criarChamado(@Valid @RequestBody ChamadoRequestDTO ChamadoDTO) {
        String rmUsuario = jwtService.getRmFromToken();

        chamadoService.criarChamado(rmUsuario, ChamadoDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/ambientes")
    public ResponseEntity<List<AmbienteSelectDTO>> getAllAmbienteChamadoSelect() {
        List<AmbienteSelectDTO> ambientes = ambienteService.getAllAmbienteChamadoSelect();
        return new ResponseEntity<>(ambientes, HttpStatus.OK);
    }

    @GetMapping("/tipos-chamado")
    public ResponseEntity<List<TipoChamadoSelectDTO>> getAllTipoChamadoSelect() {
        List<TipoChamadoSelectDTO> tiposChamados = tipoChamadoService.getAllTipoChamadoSelect();
        return new ResponseEntity<>(tiposChamados, HttpStatus.OK);
    }

    @GetMapping("/prioridade")
    public ResponseEntity<List<ChamadoCardDTO>> getChamadosByPrioridade(@RequestParam("prioridade") PrioridadeChamadoEnum prioridade) {
        List<ChamadoCardDTO> chamados = chamadoService.getByPrioridadeChamado(prioridade);
        return new ResponseEntity<>(chamados, HttpStatus.OK);
    }

    @GetMapping("/prioridade/concluidos")
    public ResponseEntity<List<ChamadoCardDTO>> getByPrioridadeStatusChamado(@RequestParam("prioridade") PrioridadeChamadoEnum prioridade, @RequestParam("status") StatusChamadoEnum status) {
        List<ChamadoCardDTO> chamados = chamadoService.getByPrioridadeStatusChamado(prioridade, status);
        return new ResponseEntity<>(chamados, HttpStatus.OK);
    }
}
