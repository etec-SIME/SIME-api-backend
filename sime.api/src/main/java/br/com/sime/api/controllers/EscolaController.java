package br.com.sime.api.controllers;
import br.com.sime.api.DTOs.*;
import br.com.sime.api.entities.escola.ambiente.Ambiente;
import br.com.sime.api.entities.escola.Escola;
import br.com.sime.api.entities.escola.ambiente.Tipo_Ambiente;
import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import br.com.sime.api.entities.outros.Departamento;
import br.com.sime.api.entities.usuarios.Permissao;
import br.com.sime.api.entities.usuarios.TipoPerfil;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import br.com.sime.api.DTOs.LoginEscolaDTO;
import br.com.sime.api.DTOs.TokenDTO;
import br.com.sime.api.services.EscolaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/escolas")
public class EscolaController {
    @Autowired
    private EscolaService escolaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AmbienteService ambienteService;

    @Autowired
    private TipoEquipamentoService tipoEquipamentoService;

    @Autowired
    private EquipamentoService equipamentoService;

    @Autowired
    private TipoPerfilService tipoPerfilService;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private PermissaoService permissaoService;

    @Autowired
    private TipoChamadoService tipoChamadoService;

    @Autowired
    private TipoAmbienteService tipoAmbienteService;

    @PostMapping("login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginEscolaDTO login) {
        TokenDTO token = escolaService.loginEscola(login);
        return new ResponseEntity<>(new TokenDTO(token.getToken()), HttpStatus.OK);
    }

    // --- GET ALL---

    @GetMapping()
    public ResponseEntity<List<Escola>> getAllEscolas(){
        List<Escola> escolas = escolaService.getAllEscolas();
        return new ResponseEntity<>(escolas, HttpStatus.OK);
    }

    @GetMapping("/tipo-perfil")
    public ResponseEntity<List<TipoPerfil>> getAllTipoPerfis(){
        List<TipoPerfil> tipoPerfilList = tipoPerfilService.getAllTipoPerfis();
        return new ResponseEntity<>(tipoPerfilList, HttpStatus.OK);
        }


    @GetMapping("/ambiente")
    public ResponseEntity<List<AmbienteDTO>> getAllAmbientes(){
        List<AmbienteDTO> ambientesList = ambienteService.getAllAmbientes();
        return new ResponseEntity<>(ambientesList, HttpStatus.OK);
    }

    @GetMapping("/departamento")
    public ResponseEntity<List<Departamento>> getAllDepartamentos(){
        List<Departamento> departamentosList = departamentoService.getAllDepartamentos();
        return new ResponseEntity<>(departamentosList, HttpStatus.OK);
    }

    @GetMapping("/tipo-equipamento")
    public ResponseEntity<List<TipoEquipamento>> getAllTipoEquipamento(){
        List<TipoEquipamento> tipoEquipamentoList = tipoEquipamentoService.getAllTipoEquipamentos();
        return new ResponseEntity<>(tipoEquipamentoList, HttpStatus.OK);
    }

    @GetMapping("/equipamento")
    public ResponseEntity<List<EquipamentoResponseDTO>> getAllEquipamentos(){
        List<EquipamentoResponseDTO> equipamentoResponseDTOList = equipamentoService.getAllEquipamentos();
        return new ResponseEntity<>(equipamentoResponseDTOList, HttpStatus.OK);
    }

    @GetMapping("/tipo-chamado")
    public ResponseEntity<List<TipoChamadoResponseDTO>> getAllTipoChamado() {
        List<TipoChamadoResponseDTO> tipoChamadoResponseDTOList = tipoChamadoService.getAllTipoChamado();
        return new ResponseEntity<>(tipoChamadoResponseDTOList, HttpStatus.OK);
    }

    @GetMapping("/tipo-ambiente")
    public ResponseEntity<List<Tipo_Ambiente>> getAllTipoAmbiente(){
        List<Tipo_Ambiente> tipoAmbienteList = tipoAmbienteService.getAllTipoAmbiente();
        return new ResponseEntity<>(tipoAmbienteList, HttpStatus.OK);
    }

    // --- GETs - consultar listas ---

    @GetMapping("/tipo-perfil/{idTipoPerfil}/permissao")
    public ResponseEntity<List<Permissao>> getAllPermissaoTipoPerfil(@PathVariable Long idTipoPerfil){
        List<Permissao> permissaoList = tipoPerfilService.getAllPermissaoTipoPerfil(idTipoPerfil);
        return new ResponseEntity<>(permissaoList, HttpStatus.OK);
    }


    @GetMapping("/ambiente/{idAmbiente}/tipo-equipamento")
    public ResponseEntity<List<TipoEquipamento>> getAllTipoEquipamentoAmbiente(@PathVariable Long idAmbiente){
        List<TipoEquipamento> tipoEquipamentoList = ambienteService.getAllTipoEquipamentoAmbiente(idAmbiente);
        return new ResponseEntity<>(tipoEquipamentoList, HttpStatus.OK);
    }

    // --- POSTs - cadastrato/criação---

    @PostMapping("/usuario")
    public ResponseEntity<?> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioDTO){
        UsuarioRequestDTO usuario = usuarioService.cadastrarUsuario(usuarioDTO);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @PostMapping("/tipo-perfil")
    public ResponseEntity<?> cadastrarTipoPerfil(@Valid @RequestBody TipoPerfilDTO tipoPerfilDTO){
        TipoPerfil tipoPerfil = tipoPerfilService.cadastrarTipoPerfil(tipoPerfilDTO);
        return new ResponseEntity<>(tipoPerfil, HttpStatus.CREATED);
    }

    @PostMapping("/ambiente")
    public ResponseEntity<?> cadastrarAmbiente(@Valid @RequestBody AmbienteDTO ambienteDTO){
        AmbienteDTO ambiente = ambienteService.cadastrarAmbiente(ambienteDTO);
        return new ResponseEntity<>(ambiente, HttpStatus.CREATED);
    }

    @PostMapping("/departamento")
    public ResponseEntity<?> criarDepartamento(@Valid @RequestBody DepartamentoDTO departamentoDTO){
        Departamento departamento = departamentoService.criarDepartamento(departamentoDTO);
        return new ResponseEntity<>(departamento, HttpStatus.CREATED);
    }

    @PostMapping("/tipo-equipamento")
    public ResponseEntity<?> criarTipoEquipamento(@Valid @RequestBody TipoEquipamentoDTO tipoEquipamentoDTO){
        TipoEquipamento tipoEquipamento = tipoEquipamentoService.criarTipoEquipamento(tipoEquipamentoDTO);
        return new ResponseEntity<>(tipoEquipamento, HttpStatus.CREATED);
    }

    @PostMapping("/equipamento")
    public ResponseEntity<EquipamentoResponseDTO> cadastrarEquipamento(@Valid @RequestBody EquipamentoDTO equipamentoDTO){
        EquipamentoResponseDTO equipamentoResponseDTO = equipamentoService.cadastrarEquipamento(equipamentoDTO);
        return new ResponseEntity<>(equipamentoResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/tipo-chamado")
    public ResponseEntity<?> criarTipoChamado(@Valid @RequestBody TipoChamadoDTO tipoChamadoDTO){
        TipoChamadoResponseDTO tipoChamadoResponseDTO = tipoChamadoService.criarTipoChamado(tipoChamadoDTO);
        return new ResponseEntity<>(tipoChamadoResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/tipo-ambiente")
    public ResponseEntity<?> criarTipoAmbiente(@Valid @RequestBody TipoAmbienteDTO tipoAmbienteDTO){
        Tipo_Ambiente tipoAmbiente = tipoAmbienteService.criarTipoAmbiente(tipoAmbienteDTO);
        return new ResponseEntity<>(tipoAmbiente, HttpStatus.CREATED);
    }

    // --- PUTs - atribuição ---

    @PutMapping("/tipo-perfil/{idTipoPerfil}/permissao")
    public ResponseEntity<?> atribuirPermissoesTipoPerfil(@PathVariable Long idTipoPerfil, @Valid @RequestBody PermissaoTipoPerfilDTO permissoesTipoPerfilDTO){
        List<Permissao> permissaoList = tipoPerfilService.atribuirPermissoes(idTipoPerfil, permissoesTipoPerfilDTO);
        return new ResponseEntity<>(permissaoList, HttpStatus.ACCEPTED);
    }

    @PutMapping("/ambiente/{idAmbiente}/tipo-equipamento")
    public ResponseEntity<?> atribuirTipoEquipamentoAmbiente(@PathVariable Long idAmbiente, @Valid @RequestBody TipoEquipamentoAmbienteDTO tipoEquipamentoAmbienteDTO){
        Ambiente ambiente = ambienteService.atribuirTipoEquipamentos(idAmbiente, tipoEquipamentoAmbienteDTO);
        return new ResponseEntity<>(ambiente, HttpStatus.ACCEPTED);
    }

    // --- PUTs - edição---

    @PutMapping("/tipo-perfil/{idTipoPerfil}")
    public ResponseEntity<TipoPerfilDTO> editarTipoPerfil(@PathVariable Long idTipoPerfil, @Valid @RequestBody TipoPerfilDTO tipoPerfilDTO){
        TipoPerfilDTO tipoPerfilAtualizado = tipoPerfilService.editarTipoPerfil(idTipoPerfil, tipoPerfilDTO);
        return new ResponseEntity<>(tipoPerfilAtualizado, HttpStatus.OK);
    }

    @PutMapping("/departamento/{idDepartamento}")
    public  ResponseEntity<?> editarDepartamento(@PathVariable Long idDepartamento, @Valid @RequestBody DepartamentoDTO departamentoDTO ){
        Departamento departamentoAtualizado = departamentoService.editarDepartamento(idDepartamento, departamentoDTO);
        return new ResponseEntity<>(departamentoAtualizado, HttpStatus.OK);
    }

    @PutMapping("/tipo-equipamento/{idTipoEquipamento}")
    public  ResponseEntity<?> editarTipoEquipamento(@PathVariable Long idTipoEquipamento,@Valid @RequestBody TipoEquipamentoDTO tipoEquipamentoDTO ){
        TipoEquipamento tipoEquipamentoAtualizado = tipoEquipamentoService.editarTipoEquipamento(idTipoEquipamento, tipoEquipamentoDTO);
        return new ResponseEntity<>(tipoEquipamentoAtualizado, HttpStatus.OK);
    }

    @PutMapping("/equipamento/{codEquipamento}")
    public  ResponseEntity<?> editarEquipamento(@PathVariable Long codEquipamento,@Valid @RequestBody EquipamentoDTO equipamentoDTO ){
        EquipamentoResponseDTO equipamentoAtualizado = equipamentoService.editarEquipamento(codEquipamento, equipamentoDTO);
        return new ResponseEntity<>(equipamentoAtualizado, HttpStatus.OK);
    }

    @PutMapping("/tipo-chamado/{idTipoChamado}")
    public  ResponseEntity<?> editarTipoChamado(@PathVariable Long idTipoChamado, @Valid @RequestBody TipoChamadoDTO tipoChamadoDTO ){
        TipoChamadoResponseDTO tipoChamadoResponseDTO = tipoChamadoService.editarTipoChamado(idTipoChamado, tipoChamadoDTO);
        return new ResponseEntity<>(tipoChamadoResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/ambiente/{idAmbiente}")
    public  ResponseEntity<?> editarAmbiente(@PathVariable Long idAmbiente, @Valid @RequestBody AmbienteDTO ambienteDTO ){
        Ambiente ambienteAtualizado = ambienteService.editarAmbiente(idAmbiente, ambienteDTO);
        return new ResponseEntity<>(ambienteAtualizado, HttpStatus.OK);
    }

    @PutMapping("/tipo-ambiente/{idTipoAmbiente}")
    public ResponseEntity<?> editarTipoAmbiente(@PathVariable Long idTipoAmbiente, @Valid @RequestBody TipoAmbienteDTO tipoAmbienteDTO){
        Tipo_Ambiente tipoAmbienteAtualizado = tipoAmbienteService.editarTipoAmbiente(idTipoAmbiente, tipoAmbienteDTO);
        return new ResponseEntity<>(tipoAmbienteAtualizado, HttpStatus.OK);
    }
}
