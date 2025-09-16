package br.com.sime.api.controllers;
import br.com.sime.api.DTOs.*;
import br.com.sime.api.entities.escola.ambiente.Ambiente;
import br.com.sime.api.entities.escola.Escola;
import br.com.sime.api.entities.escola.ambiente.TipoAmbiente;
import br.com.sime.api.entities.escola.equipamentos.TipoEquipamento;
import br.com.sime.api.entities.outros.Departamento;
import br.com.sime.api.entities.usuarios.Permissao;
import br.com.sime.api.entities.usuarios.TipoPerfil;
import br.com.sime.api.entities.usuarios.Usuario;
import br.com.sime.api.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Escolas", description = "Operações relacionadas a escola")
@RestController
@RequestMapping("/escola")
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

    @Operation(summary = "Login", description = "Permite a criação de logins para as escolas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Login efetuado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PostMapping("login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginEscolaDTO login) {
        TokenDTO token = escolaService.loginEscola(login);
        return new ResponseEntity<>(new TokenDTO(token.getToken()), HttpStatus.OK);
    }

    // --- GET ALL---
    @Operation(summary = "Chama escolas", description = "Permite a visualização de todas as escolas cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Escolas exibidas com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })

    @GetMapping()
    public ResponseEntity<List<Escola>> getAllEscolas(){
        List<Escola> escolas = escolaService.getAllEscolas();
        return new ResponseEntity<>(escolas, HttpStatus.OK);
    }

    @Operation(summary = "Chama os tipos de perfil", description = "Permite a visualização dos tipos de perfil cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipos de perfil exbidos com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @GetMapping("/tipo-perfil")
    public ResponseEntity<List<TipoPerfil>> getAllTipoPerfis(){
        List<TipoPerfil> tipoPerfilList = tipoPerfilService.getAllTipoPerfis();
        return new ResponseEntity<>(tipoPerfilList, HttpStatus.OK);
    }

    @Operation(summary = "Chama os ambientes", description = "Permite a visualização dos ambientes da escola cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ambientes exbidos com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @GetMapping("/ambiente")
    public ResponseEntity<List<Ambiente>> getAllAmbientes(){
        List<Ambiente> ambientesList = ambienteService.getAllAmbientes();
        return new ResponseEntity<>(ambientesList, HttpStatus.OK);
    }

    @Operation(summary = "Chama os departamentos", description = "Permite a visualização dos departamentos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Departamentos exbidos com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @GetMapping("/departamento")
    public ResponseEntity<List<Departamento>> getAllDepartamentos(){
        List<Departamento> departamentosList = departamentoService.getAllDepartamentos();
        return new ResponseEntity<>(departamentosList, HttpStatus.OK);
    }

    @Operation(summary = "Chama os tipos de equipamento", description = "Permite a visualização dos tipos de equipamento cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipos de equipamento exbidos com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @GetMapping("/tipo-equipamento")
    public ResponseEntity<List<TipoEquipamento>> getAllTipoEquipamento(){
        List<TipoEquipamento> tipoEquipamentoList = tipoEquipamentoService.getAllTipoEquipamentos();
        return new ResponseEntity<>(tipoEquipamentoList, HttpStatus.OK);
    }

    @Operation(summary = "Chama os equipamentos", description = "Permite a visualização dos equipamentos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Equipamentos exbidos com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @GetMapping("/equipamento")
    public ResponseEntity<List<EquipamentoResponseDTO>> getAllEquipamentos(){
        List<EquipamentoResponseDTO> equipamentoResponseDTOList = equipamentoService.getAllEquipamentos();
        return new ResponseEntity<>(equipamentoResponseDTOList, HttpStatus.OK);
    }

    @Operation(summary = "Chama os tipos de chamado", description = "Permite a visualização dos tipos de chamado cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipos de chamado exbidos com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @GetMapping("/tipo-chamado")
    public ResponseEntity<List<TipoChamadoResponseDTO>> getAllTipoChamado() {
        List<TipoChamadoResponseDTO> tipoChamadoResponseDTOList = tipoChamadoService.getAllTipoChamado();
        return new ResponseEntity<>(tipoChamadoResponseDTOList, HttpStatus.OK);
    }

    @GetMapping("/tipo-ambiente")
    public ResponseEntity<List<TipoAmbiente>> getAllTipoAmbiente(){
        List<TipoAmbiente> tipoAmbienteList = tipoAmbienteService.getAllTipoAmbiente();
        return new ResponseEntity<>(tipoAmbienteList, HttpStatus.OK);
    }

    // --- GETs - consultar listas ---

    @Operation(summary = "Visualizar permissões de perfis", description = "Permite a visualização das permissões dos perfis cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Permissões exbidas com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @GetMapping("/tipo-perfil/{idTipoPerfil}/permissao")
    public ResponseEntity<List<Permissao>> getAllPermissaoTipoPerfil(@PathVariable Long idTipoPerfil){
        List<Permissao> permissaoList = tipoPerfilService.getAllPermissaoTipoPerfil(idTipoPerfil);
        return new ResponseEntity<>(permissaoList, HttpStatus.OK);
    }


    @Operation(summary = "Chama os tipos de equipamento de cada ambiente", description = "Permite a visualização dos tipos de equipamento de cada ambiente cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipos de equipamento dos ambientes exbidos com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @GetMapping("/ambiente/{idAmbiente}/tipo-equipamento")
    public ResponseEntity<List<TipoEquipamento>> getAllTipoEquipamentoAmbiente(@PathVariable Long idAmbiente){
        List<TipoEquipamento> tipoEquipamentoList = ambienteService.getAllTipoEquipamentoAmbiente(idAmbiente);
        return new ResponseEntity<>(tipoEquipamentoList, HttpStatus.OK);
    }

    // --- POSTs - cadastrato/criação---

    @Operation(summary = "Cadastrar usuário", description = "Permite o cadastro de novos usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PostMapping("/usuario")
    public ResponseEntity<?> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioDTO){
        //Escola escolaLogada = escolaDetails.getEscola();
        Usuario usuario = usuarioService.cadastrarUsuario(usuarioDTO);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @Operation(summary = "Cadastrar tipo de perfil", description = "Permite o cadastro de novos tipos de perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de perfil cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PostMapping("/tipo-perfil")
    public ResponseEntity<?> cadastrarTipoPerfil(@Valid @RequestBody TipoPerfilDTO tipoPerfilDTO){
        TipoPerfil tipoPerfil = tipoPerfilService.criarTipoPerfil(tipoPerfilDTO);
        return new ResponseEntity<>(tipoPerfil, HttpStatus.CREATED);
    }

    @Operation(summary = "Cadastrar ambiente", description = "Permite o cadastro de novos ambientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ambiente cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PostMapping("/ambiente")
    public ResponseEntity<?> cadastrarAmbiente(@Valid @RequestBody AmbienteDTO ambienteDTO){
        Ambiente ambiente = ambienteService.cadastrarAmbiente(ambienteDTO);
        return new ResponseEntity<>(ambiente, HttpStatus.CREATED);
    }

    @Operation(summary = "Cadastrar departamento", description = "Permite o cadastro de novos departamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Departamento cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PostMapping("/departamento")
    public ResponseEntity<?> criarDepartamento(@Valid @RequestBody DepartamentoDTO departamentoDTO){
        Departamento departamento = departamentoService.criarDepartamento(departamentoDTO);
        return new ResponseEntity<>(departamento, HttpStatus.CREATED);
    }

    @Operation(summary = "Cadastrar tipo de equipamento", description = "Permite o cadastro de novos tipos de equipamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipos de equipamento cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PostMapping("/tipo-equipamento")
    public ResponseEntity<?> criarTipoEquipamento(@Valid @RequestBody TipoEquipamentoDTO tipoEquipamentoDTO){
        TipoEquipamento tipoEquipamento = tipoEquipamentoService.criarTipoEquipamento(tipoEquipamentoDTO);
        return new ResponseEntity<>(tipoEquipamento, HttpStatus.CREATED);
    }

    @Operation(summary = "Cadastrar equipamentos", description = "Permite o cadastro de novos equipamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Equipamento cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PostMapping("/equipamento")
    public ResponseEntity<EquipamentoResponseDTO> cadastrarEquipamento(@Valid @RequestBody EquipamentoDTO equipamentoDTO){
        EquipamentoResponseDTO equipamentoResponseDTO = equipamentoService.cadastrarEquipamento(equipamentoDTO);
        return new ResponseEntity<>(equipamentoResponseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Cadastrar tipos de chamado", description = "Permite o cadastro de novos tipos de chamado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de chamado cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PostMapping("/tipo-chamado")
    public ResponseEntity<?> criarTipoChamado(@Valid @RequestBody TipoChamadoDTO tipoChamadoDTO){
        TipoChamadoResponseDTO tipoChamadoResponseDTO = tipoChamadoService.criarTipoChamado(tipoChamadoDTO);
        return new ResponseEntity<>(tipoChamadoResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/tipo-ambiente")
    public ResponseEntity<?> criarTipoAmbiente(@Valid @RequestBody TipoAmbienteDTO tipoAmbienteDTO){
        TipoAmbiente tipoAmbiente = tipoAmbienteService.criarTipoAmbiente(tipoAmbienteDTO);
        return new ResponseEntity<>(tipoAmbiente, HttpStatus.CREATED);
    }

    // --- PUTs - atribuição ---

    @Operation(summary = "Atribuir permissões aos tipos de perfil", description = "Permite permite atribuir permissões personalizadas aos tipos de perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Permissão atribuida com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PutMapping("/tipo-perfil/{idTipoPerfil}/permissao")
    public ResponseEntity<?> atribuirPermissoesTipoPerfil(@PathVariable Long idTipoPerfil, @Valid @RequestBody PermissaoTipoPerfilDTO permissoesTipoPerfilDTO){
        TipoPerfil tipoPerfil = tipoPerfilService.atribuirPermissoes(idTipoPerfil, permissoesTipoPerfilDTO);
        return new ResponseEntity<>(tipoPerfil, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Atribuir tipo de equipamento em ambiente", description = "Permite a atribuição de tipos de equipamentos em ambientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de equipamento atribuido no ambiente com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PutMapping("/ambiente/{idAmbiente}/tipo-equipamento")
    public ResponseEntity<?> atribuirTipoEquipamentoAmbiente(@PathVariable Long idAmbiente, @Valid @RequestBody TipoEquipamentoAmbienteDTO tipoEquipamentoAmbienteDTO){
        Ambiente ambiente = ambienteService.atribuirTipoEquipamentos(idAmbiente, tipoEquipamentoAmbienteDTO);
        return new ResponseEntity<>(ambiente, HttpStatus.ACCEPTED);
    }

    // --- PUTs - edição---

    @Operation(summary = "Editar tipo de perfil", description = "Permite a edição de tipos de perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Edição efetuada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PutMapping("/tipo-perfil/{idTipoPerfil}")
    public ResponseEntity<?> editarTipoPerfil(@PathVariable Long idTipoPerfil, @Valid @RequestBody TipoPerfilDTO tipoPerfilDTO){
        TipoPerfil tipoPerfilAtualizado = tipoPerfilService.editarTipoPerfil(idTipoPerfil, tipoPerfilDTO);
        return new ResponseEntity<>(tipoPerfilAtualizado, HttpStatus.OK);
    }

    @Operation(summary = "Editar departamento", description = "Permite a edição dos departamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Edição efetuada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PutMapping("/departamento/{idDepartamento}")
    public  ResponseEntity<?> editarDepartamento(@PathVariable Long idDepartamento, @Valid @RequestBody DepartamentoDTO departamentoDTO ){
        Departamento departamentoAtualizado = departamentoService.editarDepartamento(idDepartamento, departamentoDTO);
        return new ResponseEntity<>(departamentoAtualizado, HttpStatus.OK);
    }

    @Operation(summary = "Editar tipo de equipamento", description = "Permite a edição de tipos de equipamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Edição efetuada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PutMapping("/tipo-equipamento/{idTipoEquipamento}")
    public  ResponseEntity<?> editarTipoEquipamento(@PathVariable Long idTipoEquipamento,@Valid @RequestBody TipoEquipamentoDTO tipoEquipamentoDTO ){
        TipoEquipamento tipoEquipamentoAtualizado = tipoEquipamentoService.editarTipoEquipamento(idTipoEquipamento, tipoEquipamentoDTO);
        return new ResponseEntity<>(tipoEquipamentoAtualizado, HttpStatus.OK);
    }

    @Operation(summary = "Editar equipamento", description = "Permite a edição de equipamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Edição efetuada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PutMapping("/equipamento/{codEquipamento}")
    public  ResponseEntity<?> editarEquipamento(@PathVariable Long codEquipamento,@Valid @RequestBody EquipamentoDTO equipamentoDTO ){
        EquipamentoResponseDTO equipamentoAtualizado = equipamentoService.editarEquipamento(codEquipamento, equipamentoDTO);
        return new ResponseEntity<>(equipamentoAtualizado, HttpStatus.OK);
    }

    @Operation(summary = "Editar tipo de chamado", description = "Permite a edição de tipos de chamado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Edição efetuada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PutMapping("/tipo-chamado/{idTipoChamado}")
    public  ResponseEntity<?> editarTipoChamado(@PathVariable Long idTipoChamado, @Valid @RequestBody TipoChamadoDTO tipoChamadoDTO ){
        TipoChamadoResponseDTO tipoChamadoResponseDTO = tipoChamadoService.editarTipoChamado(idTipoChamado, tipoChamadoDTO);
        return new ResponseEntity<>(tipoChamadoResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Editar ambiente", description = "Permite a edição de ambientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Edição efetuada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados")
    })
    @PutMapping("/ambiente/{idAmbiente}")
    public  ResponseEntity<?> editarAmbiente(@PathVariable Long idAmbiente, @Valid @RequestBody AmbienteDTO ambienteDTO ){
        Ambiente ambienteAtualizado = ambienteService.editarAmbiente(idAmbiente, ambienteDTO);
        return new ResponseEntity<>(ambienteAtualizado, HttpStatus.OK);
    }

    @PutMapping("/tipo-ambiente/{idTipoAmbiente}")
    public ResponseEntity<?> editarTipoAmbiente(@PathVariable Long idTipoAmbiente, @Valid @RequestBody TipoAmbienteDTO tipoAmbienteDTO){
        TipoAmbiente tipoAmbienteAtualizado = tipoAmbienteService.editarTipoAmbiente(idTipoAmbiente, tipoAmbienteDTO);
        return new ResponseEntity<>(tipoAmbienteAtualizado, HttpStatus.OK);
    }
}
