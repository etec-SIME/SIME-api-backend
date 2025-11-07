-- =====================================
-- ESCOLAS
-- =====================================
INSERT INTO Escola (cod_escola, cnpj_escola, senha_escola, cep_escola, num_endereco_escola, nome_escola)
VALUES
('E01', '12345678000195', 'senha123', '01001000', '100', 'ETEC Horácio Augusto da Silveira'),
('E02', '98765432000155', 'segura456', '02020030', '200', 'ETEC de Informática');

-- =====================================
-- TIPO PERFIL
-- =====================================
DECLARE @idGestorGeral BIGINT, @idGestorDep BIGINT, @idFuncionario BIGINT, @idUsuario BIGINT;

INSERT INTO Tipo_perfil (nome_tipo_perfil) VALUES ('Gestor Geral');
SET @idGestorGeral = SCOPE_IDENTITY();

INSERT INTO Tipo_perfil (nome_tipo_perfil) VALUES ('Gestor Departamento');
SET @idGestorDep = SCOPE_IDENTITY();

INSERT INTO Tipo_perfil (nome_tipo_perfil) VALUES ('Funcionário');
SET @idFuncionario = SCOPE_IDENTITY();

INSERT INTO Tipo_perfil (nome_tipo_perfil) VALUES ('Usuário Comum');
SET @idUsuario = SCOPE_IDENTITY();

-- =====================================
-- PERMISSAO
-- =====================================
DECLARE
    @idAtualizarPrioridadeChamado BIGINT,
    @idAtualizarBarraProgressoChamado BIGINT,
    @idAprovarOuReprovarChamado BIGINT,
    @idCadastrarTipoPerfil BIGINT,
    @idCadastrarLocal BIGINT,
    @idCadastrarTipoChamado BIGINT,
    @idCadastrarPerfil BIGINT,
    @idCadastrarDepartamento BIGINT,
    @idCadastrarEquipamento BIGINT,
    @idCadastrarTipoLocal BIGINT,
    @idCadastrarTipoEquipamento BIGINT;

INSERT INTO Permissao (nome_permissao, desc_permissao)
VALUES ('Atualizar Prioridade de Chamado', 'Pode atualizar a prioridade dos chamados');
SET @idAtualizarPrioridadeChamado = SCOPE_IDENTITY();

INSERT INTO Permissao (nome_permissao, desc_permissao)
VALUES ('Atualizar Barra de Progresso de Chamado', 'Pode atualizar a barra de progresso dos chamados');
SET @idAtualizarBarraProgressoChamado = SCOPE_IDENTITY();

INSERT INTO Permissao (nome_permissao, desc_permissao)
VALUES ('Aprovar ou Reprovar Chamado', 'Pode aprovar ou reprovar chamados pendentes');
SET @idAprovarOuReprovarChamado = SCOPE_IDENTITY();

INSERT INTO Permissao (nome_permissao, desc_permissao)
VALUES ('Cadastrar Tipo de Perfil', 'Pode cadastrar novos tipos de perfil de acesso');
SET @idCadastrarTipoPerfil = SCOPE_IDENTITY();

INSERT INTO Permissao (nome_permissao, desc_permissao)
VALUES ('Cadastrar Local', 'Pode cadastrar e gerenciar locais físicos da escola');
SET @idCadastrarLocal = SCOPE_IDENTITY();

INSERT INTO Permissao (nome_permissao, desc_permissao)
VALUES ('Cadastrar Tipo de Chamado', 'Pode cadastrar novos tipos de chamados');
SET @idCadastrarTipoChamado = SCOPE_IDENTITY();

INSERT INTO Permissao (nome_permissao, desc_permissao)
VALUES ('Cadastrar Perfil', 'Pode criar e gerenciar perfis de usuários');
SET @idCadastrarPerfil = SCOPE_IDENTITY();

INSERT INTO Permissao (nome_permissao, desc_permissao)
VALUES ('Cadastrar Departamento', 'Pode cadastrar e editar departamentos da escola');
SET @idCadastrarDepartamento = SCOPE_IDENTITY();

INSERT INTO Permissao (nome_permissao, desc_permissao)
VALUES ('Cadastrar Equipamento', 'Pode cadastrar e gerenciar equipamentos nos ambientes');
SET @idCadastrarEquipamento = SCOPE_IDENTITY();

INSERT INTO Permissao (nome_permissao, desc_permissao)
VALUES ('Cadastrar Tipo de Local', 'Pode cadastrar e gerenciar tipos de locais físicos');
SET @idCadastrarTipoLocal = SCOPE_IDENTITY();

INSERT INTO Permissao (nome_permissao, desc_permissao)
VALUES ('Cadastrar Tipo de Equipamento', 'Pode cadastrar e gerenciar tipos de equipamentos');
SET @idCadastrarTipoEquipamento = SCOPE_IDENTITY();

-- =====================================
-- POSSUI (Permissão x Tipo Perfil)
-- =====================================
-- Gestor Geral
INSERT INTO Possui (id_permissao, id_tipo_perfil) VALUES
(@idAtualizarPrioridadeChamado, @idGestorGeral),
(@idAtualizarBarraProgressoChamado, @idGestorGeral),
(@idAprovarOuReprovarChamado, @idGestorGeral),
(@idCadastrarTipoPerfil, @idGestorGeral),
(@idCadastrarLocal, @idGestorGeral),
(@idCadastrarTipoChamado, @idGestorGeral),
(@idCadastrarPerfil, @idGestorGeral),
(@idCadastrarDepartamento, @idGestorGeral),
(@idCadastrarEquipamento, @idGestorGeral),
(@idCadastrarTipoLocal, @idGestorGeral),
(@idCadastrarTipoEquipamento, @idGestorGeral);

-- =====================================
-- CADASTRA (Escola x Tipo Perfil)
-- =====================================
INSERT INTO Cadastra (cod_escola, id_tipo_perfil) VALUES
('E01', @idGestorGeral),
('E01', @idGestorDep),
('E01', @idFuncionario),
('E01', @idUsuario)

-- =====================================
-- USUARIOS
-- =====================================
INSERT INTO Usuario (rm_usuario, chamados_abertos, chamados_concluidos, email_usuario, nome_usuario, senha_usuario, telefone_usuario, cpf_usuario, id_tipo_perfil)
VALUES
('200002', '01', '03', 'gestor@escola.com', 'Carlos Gestor', 'senhaGestor', '11970002222', '22222222222', @idGestorGeral),
('200003', '02', '01', 'gestor.dep@escola.com', 'Fernanda Dep', 'senhaDep', '11970003333', '33333333333', @idGestorDep),
('200004', '03', '00', 'funcionario@escola.com', 'Rafael Func', 'senhaFunc', '11970004444', '44444444444', @idFuncionario),
('200005', '00', '00', 'usuario@escola.com', 'João Usuário', 'senhaUser', '11970005555', '55555555555', @idUsuario);

-- =====================================
-- DEPARTAMENTOS
-- =====================================
DECLARE @idDeptEletrica BIGINT, @idDeptTI BIGINT, @idDeptEstrutural BIGINT;

INSERT INTO Departamento (nome_departamento, desc_departamento) VALUES ('Manutenção Elétrica', 'Responsável por reparos em sistemas elétricos');
SET @idDeptEletrica = SCOPE_IDENTITY();

INSERT INTO Departamento (nome_departamento, desc_departamento) VALUES ('TI', 'Responsável por computadores e redes');
SET @idDeptTI = SCOPE_IDENTITY();

INSERT INTO Departamento (nome_departamento, desc_departamento) VALUES ('Estrutual', 'Responsável pela estrutura dos ambientes');
SET @idDeptEstrutural = SCOPE_IDENTITY();

-- =====================================
-- CONDUZ
-- =====================================
INSERT INTO Conduz (rm_usuario, id_departamento) VALUES
('200002', @idDeptTI),
('200003', @idDeptEletrica),
('200004', @idDeptEstrutural);

-- =====================================
-- TIPO AMBIENTE
-- =====================================
DECLARE @idTipoSala BIGINT, @idTipoLab BIGINT, @idTipoBiblioteca BIGINT, @idTipoQuadra BIGINT, @idTipoBanheiro BIGINT;

INSERT INTO Tipo_Ambiente (nome_tipo_ambiente) VALUES ('Sala de Aula'); SET @idTipoSala = SCOPE_IDENTITY();
INSERT INTO Tipo_Ambiente (nome_tipo_ambiente) VALUES ('Laboratório de Informática'); SET @idTipoLab = SCOPE_IDENTITY();
INSERT INTO Tipo_Ambiente (nome_tipo_ambiente) VALUES ('Biblioteca'); SET @idTipoBiblioteca = SCOPE_IDENTITY();
INSERT INTO Tipo_Ambiente (nome_tipo_ambiente) VALUES ('Quadra Esportiva'); SET @idTipoQuadra = SCOPE_IDENTITY();
INSERT INTO Tipo_Ambiente (nome_tipo_ambiente) VALUES ('Banheiro'); SET @idTipoBanheiro = SCOPE_IDENTITY();

-- =====================================
-- AMBIENTE
-- =====================================
DECLARE @idAmb101 BIGINT, @idAmb202 BIGINT, @idAmb303 BIGINT, @idAmb404 BIGINT, @idAmb505 BIGINT;

INSERT INTO Ambiente (num_ambiente, desc_ambiente, id_tipo_ambiente) VALUES (101, 'Sala de aula do 3º ano', @idTipoSala); SET @idAmb101 = SCOPE_IDENTITY();
INSERT INTO Ambiente (num_ambiente, desc_ambiente, id_tipo_ambiente) VALUES (202, 'Laboratório de Redes e Computadores', @idTipoLab); SET @idAmb202 = SCOPE_IDENTITY();
INSERT INTO Ambiente (num_ambiente, desc_ambiente, id_tipo_ambiente) VALUES (303, 'Biblioteca Central', @idTipoBiblioteca); SET @idAmb303 = SCOPE_IDENTITY();
INSERT INTO Ambiente (num_ambiente, desc_ambiente, id_tipo_ambiente) VALUES (404, 'Quadra poliesportiva coberta', @idTipoQuadra); SET @idAmb404 = SCOPE_IDENTITY();
INSERT INTO Ambiente (num_ambiente, desc_ambiente, id_tipo_ambiente) VALUES (505, 'Banheiro do 2º andar', @idTipoBanheiro); SET @idAmb505 = SCOPE_IDENTITY();

-- =====================================
-- TIPO CHAMADO
-- =====================================
DECLARE @idChamEletrico BIGINT, @idChamComputador BIGINT, @idChamEstrutura BIGINT, @idChamHidraulico BIGINT;

INSERT INTO Tipo_Chamado (nome_tipo_chamado, id_departamento) VALUES ('Problema Elétrico', @idDeptEletrica); SET @idChamEletrico = SCOPE_IDENTITY();
INSERT INTO Tipo_Chamado (nome_tipo_chamado, id_departamento) VALUES ('Problema de Computador', @idDeptTI); SET @idChamComputador = SCOPE_IDENTITY();
INSERT INTO Tipo_Chamado (nome_tipo_chamado, id_departamento) VALUES ('Limpeza ou Estrutura', @idDeptEstrutural); SET @idChamEstrutura = SCOPE_IDENTITY();
INSERT INTO Tipo_Chamado (nome_tipo_chamado, id_departamento) VALUES ('Problema Hidráulico', @idDeptEstrutural); SET @idChamHidraulico = SCOPE_IDENTITY();

-- =====================================
-- TIPO EQUIPAMENTO
-- =====================================
DECLARE @idEqPC BIGINT, @idEqProjetor BIGINT, @idEqAr BIGINT, @idEqWifi BIGINT, @idEqEstante BIGINT, @idEqBasquete BIGINT, @idEqPia BIGINT;

INSERT INTO Tipo_equipamento (nome_tipo_equipamento, id_tipo_chamado) VALUES ('Computador', @idChamComputador); SET @idEqPC = SCOPE_IDENTITY();
INSERT INTO Tipo_equipamento (nome_tipo_equipamento, id_tipo_chamado)VALUES ('Projetor', @idChamEletrico); SET @idEqProjetor = SCOPE_IDENTITY();
INSERT INTO Tipo_equipamento (nome_tipo_equipamento, id_tipo_chamado)VALUES ('Ar-condicionado', @idChamEletrico); SET @idEqAr = SCOPE_IDENTITY();
INSERT INTO Tipo_equipamento (nome_tipo_equipamento, id_tipo_chamado)VALUES ('Rede Wi-Fi', @idChamComputador); SET @idEqWifi = SCOPE_IDENTITY();
INSERT INTO Tipo_equipamento (nome_tipo_equipamento, id_tipo_chamado)VALUES ('Estante de Livros', @idChamEstrutura); SET @idEqEstante = SCOPE_IDENTITY();
INSERT INTO Tipo_equipamento (nome_tipo_equipamento, id_tipo_chamado)VALUES ('Tabela de Basquete', @idChamEstrutura); SET @idEqBasquete = SCOPE_IDENTITY();
INSERT INTO Tipo_equipamento (nome_tipo_equipamento, id_tipo_chamado) VALUES ('Pia', @idChamHidraulico); SET @idEqPia = SCOPE_IDENTITY();

-- =====================================
-- EQUIPAMENTO
-- =====================================
INSERT INTO Equipamento (cod_equipamento, id_tipo_equipamento, id_ambiente) VALUES
('PC-LAB-01', @idEqPC, @idAmb202),        -- Laboratório de Informática
('PC-LAB-02', @idEqPC, @idAmb202),        -- Laboratório de Informática
('PROJ-101', @idEqProjetor, @idAmb101),   -- Sala de Aula 101
('AC-101', @idEqAr, @idAmb101),           -- Sala de Aula 101
('WIFI-202', @idEqWifi, @idAmb202),       -- Laboratório de Informática
('EST-303', @idEqEstante, @idAmb303),     -- Biblioteca Central
('BASK-404', @idEqBasquete, @idAmb404),   -- Quadra poliesportiva
('PIA-505', @idEqPia, @idAmb505);         -- Banheiro

-- =====================================
-- CHAMADO
-- =====================================
INSERT INTO Chamado
(prioridade_chamado, status_atual_geral_chamado, status_atual_progresso_chamado, dt_abertura_chamado, desc_chamado, dt_conclusao_chamado, local_chamado, titulo_chamado, rm_usuario, rm_usuario_responsavel, id_tipo_chamado, id_tipo_ambiente, id_ambiente) VALUES
('Alta', 'Aguardando Aprovação', 'Em análise', GETDATE(), 'Computador não liga', NULL, 'Laboratório - PC-LAB-01', 'PC não funciona', '200005', '200002', @idChamComputador, @idTipoLab, @idAmb202),
('Média', 'Pendente', 'Em andamento', GETDATE(), 'Projetor queimado na sala', NULL, 'Sala 101', 'Projetor não funciona', '200004', '200003', @idChamEletrico, @idTipoSala, @idAmb101),
('Baixa', 'Concluído', 'Concluído', GETDATE(), 'Estante quebrada precisa conserto', GETDATE(), 'Biblioteca Central', 'Estante danificada', '200005', '200004', @idChamEstrutura, @idTipoBiblioteca, @idAmb303);


-- =====================================
-- FEEDBACK
-- =====================================
INSERT INTO Feedback (dt_feedback, desc_feedback, destinatario_feedback, remetente_feedback, id_chamado, rm_usuario) VALUES
(GETDATE(), 'Problema resolvido rapidamente, ótimo suporte!', 'Carlos Gestor', 'João Usuário', 1, '200005'),
(GETDATE(), 'Ainda aguardando manutenção do projetor', 'Fernanda Dep', 'Rafael Func', 2, '200004'),
(GETDATE(), 'Estante consertada, ambiente normalizado', 'Rafael Func', 'João Usuário', 3, '200005');

-- =====================================
-- HISTORICO STATUS PROGRESSO
-- =====================================
INSERT INTO Historico_Status_Progresso (id_chamado, status_progresso, dt_alteracao, dia_semana)
VALUES
(1, 'Em análise', GETDATE(), FORMAT(GETDATE(), 'dddd', 'pt-BR')),
(2, 'Em andamento', GETDATE(), FORMAT(GETDATE(), 'dddd', 'pt-BR')),
(3, 'Concluído', GETDATE(), FORMAT(GETDATE(), 'dddd', 'pt-BR'));
