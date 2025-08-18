-- INSERTS: --

-- ESCOLA
INSERT INTO Escola VALUES 
('E01', '12345678000100', '123', '01234567', '100', 'ETEC Central');

-- TIPO PERFIL
INSERT INTO Tipo_perfil (nome_tipo_perfil) VALUES 
('Escola'),
('Administrador'),
('Técnico'),
('Funcionário');

-- PERMISSAO
INSERT INTO Permissao (nome_permissao, desc_permissao) VALUES
('Admin', 'Permite tudo'),
('Visualizar Chamado', 'Permite visualizar chamados abertos'),
('Editar Chamado', 'Permite editar qualquer chamado'),
('Criar Chamado', 'Permite criar um novo chamado');

-- POSSUI
INSERT INTO Possui VALUES 
(1, 1), 
(2, 1), 
(3, 1), 
(2, 2),
(3, 3),
(4, 1);

-- CADASTRA
INSERT INTO Cadastra VALUES 
('E01', 1), 
('E01', 2), 
('E01', 3);

-- USUARIO
INSERT INTO Usuario VALUES
('123456', '5', '10', 'admin@etec.com', 'Admin User', 'admin123', '11999999999', '12345678901', 1),
('234567', '3', '4', 'tecnico@etec.com', 'Tec User', 'tec123', '11888888888', '11222333445', 2),
('345678', '1', '0', 'aluno@etec.com', 'Aluno User', 'aluno123', '11777777777','55677888910', 3),
('456789', '1', '0', 'func@etec.com', 'Funcionário User', 'func123', '11666666666', '11677228910', 3);

-- DEPARTAMENTO
INSERT INTO Departamento (nome_departamento, desc_departamento) VALUES 
('TI', 'Departamento de Tecnologia da Informação'),
('Manutenção', 'Responsável por reparos físicos');

-- CONDUZ
INSERT INTO Conduz VALUES 
('123456', 1), 
('234567', 2);

-- TIPO AMBIENTE
INSERT INTO Tipo_ambiente (nome_tipo_ambiente) VALUES 
('Sala'),
('Laboratório');

-- AMBIENTE
INSERT INTO Ambiente (num_ambiente, desc_ambiente, id_tipo_ambiente) VALUES 
(1, 'Sala com projetor', 1),
(1, 'Laboratório com 20 computadores', 2);


-- TIPO EQUIPAMENTO
INSERT INTO Tipo_equipamento (nome_tipo_equipamento, img_tipo_equipamento, id_ambiente) VALUES 
('Projetor', 'projetor.png', 1),
('Computador', 'pc.png', 2);

-- CONTEM
INSERT INTO Contem VALUES 
(1, 1), 
(2, 2);

-- EQUIPAMENTO
INSERT INTO Equipamento (id_tipo_equipamento) VALUES 
(1), 
(2);

-- TIPO CHAMADO
INSERT INTO Tipo_Chamado (nome_tipo_chamado, id_departamento) VALUES 
('Problema Técnico', 1),
('Reparo Elétrico', 2);

-- CHAMADO
INSERT INTO Chamado (
	prioridade_chamado, status_chamado, dt_abertura_chamado, desc_chamado, dt_conclusao_chamado,
	img_chamado, local_chamado, titulo_chamado, rm_usuario, rm_usuario_responsavel, id_ambiente, id_tipo_chamado
) VALUES
('Alta Prioridade', 'Pendente', GETDATE(), 'PC não liga', NULL, 'img1.png', 'Lab de Informática',
	'Computador quebrado', '345678', '234567', 2, 1),
('Alta Prioridade', 'Concluído', GETDATE(), 'Monitor quebrado', NULL, 'img2.png', 'Lab de Informática',
	'Monitor foi rachado', '345678', '234567', 2, 2);

-- FEEDBACK
INSERT INTO Feedback (dt_feedback, desc_feedback, destinatario_feedback, remetente_feedback, id_chamado, rm_usuario) VALUES 
(GETDATE(), 'Resolvido rapidamente, obrigado!', 'Técnico', 'Funcionário', 1, '345678');
