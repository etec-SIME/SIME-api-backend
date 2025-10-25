package br.com.sime.api.controllers;

import br.com.sime.api.DTOs.AmbienteSelectDTO;
import br.com.sime.api.DTOs.ChamadoCardDTO;
import br.com.sime.api.DTOs.Responses.ChamadoStatusResponseDTO;
import br.com.sime.api.DTOs.Requests.ChamadoRequestDTO;
import br.com.sime.api.DTOs.Responses.ChamadoResponseDTO;
import br.com.sime.api.DTOs.TipoChamadoSelectDTO;
import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.enums.PrioridadeChamadoEnum;
import br.com.sime.api.enums.StatusGeralEnum;
import br.com.sime.api.enums.StatusProgressoEnum;
import br.com.sime.api.security.services.JwtService;
import br.com.sime.api.services.AmbienteService;
import br.com.sime.api.services.ChamadoService;
import br.com.sime.api.services.TipoChamadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{idChamado}")
    public ResponseEntity<Optional<ChamadoResponseDTO>> getDetalhesChamado(@PathVariable("idChamado") Long idChamado) {
        return new ResponseEntity<>(chamadoService.getDetalhesChamado(idChamado), HttpStatus.OK);
    }

    @GetMapping("/{idChamado}/status")
    public ResponseEntity<ChamadoStatusResponseDTO> getStatusChamado(@PathVariable("idChamado") Long idChamado) {
        return new ResponseEntity<>(chamadoService.getStatusChamado(idChamado), HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('Criar Chamado')")
    @PostMapping(value = "/criar-chamado", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> criarChamado(
            @RequestPart("chamado") @Valid ChamadoRequestDTO ChamadoDTO,
            @RequestPart(value = "files", required = false) MultipartFile[] files) {

        String rmUsuario = jwtService.getRmFromToken();
        System.out.println("Arquivos recebidos: " + (files != null ? files.length : 0));
        chamadoService.criarChamado(rmUsuario, ChamadoDTO, files);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{idChamado}/atualizar-status-progresso")
    public ResponseEntity<ChamadoStatusResponseDTO> atualizarStatusProgresso(@PathVariable("idChamado") Long idChamado, @RequestParam StatusProgressoEnum novoStatus) {
        ChamadoStatusResponseDTO chamadoAtualizado = chamadoService.atualizarStatusProgresso(idChamado, novoStatus);
        return new ResponseEntity<>(chamadoAtualizado, HttpStatus.OK);
    }

    @PutMapping("/{idChamado}/atualizar-status-geral")
    public ResponseEntity<ChamadoStatusResponseDTO> atualizarStatusGeral(@PathVariable("idChamado") Long idChamado, @RequestParam StatusGeralEnum novoStatus) {
        ChamadoStatusResponseDTO chamadoAtualizado = chamadoService.atualizarStatusGeral(idChamado, novoStatus);
        return new ResponseEntity<>(chamadoAtualizado, HttpStatus.OK);
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
    public ResponseEntity<List<ChamadoCardDTO>> getByPrioridadeStatusChamado(@RequestParam("prioridade") PrioridadeChamadoEnum prioridade, @RequestParam("status") StatusGeralEnum status) {
        List<ChamadoCardDTO> chamados = chamadoService.getByPrioridadeStatusChamado(prioridade, status);
        return new ResponseEntity<>(chamados, HttpStatus.OK);
    }
	
	@GetMapping("/chamados-ambiente")
    public ResponseEntity<List<ChamadosAmbienteResponseDTO>> getAllChamadosAmbiente() {
        List<ChamadosAmbienteResponseDTO> chamados = chamadoService.getAllChamadosAmbiente();
        return new ResponseEntity<>(chamados, HttpStatus.OK);
    }
}
