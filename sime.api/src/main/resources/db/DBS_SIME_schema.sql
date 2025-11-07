CREATE TABLE Escola (
	cod_escola CHAR(3) PRIMARY KEY,
	cnpj_escola CHAR(14),
	senha_escola VARCHAR(30),
	cep_escola CHAR(8),
	num_endereco_escola VARCHAR(255),
	nome_escola VARCHAR(100)
)

CREATE TABLE Tipo_perfil (
	id_tipo_perfil BIGINT IDENTITY(1,1) PRIMARY KEY,
	nome_tipo_perfil VARCHAR(30)
)

CREATE TABLE Permissao (
	id_permissao BIGINT IDENTITY(1,1) PRIMARY KEY,
	nome_permissao VARCHAR(50),
	desc_permissao VARCHAR(450)
)

CREATE TABLE Possui (
	id_permissao BIGINT,
	id_tipo_perfil BIGINT,
	PRIMARY KEY (id_permissao, id_tipo_perfil),
    FOREIGN KEY(id_permissao) REFERENCES Permissao (id_permissao),
    FOREIGN KEY(id_tipo_perfil) REFERENCES Tipo_perfil (id_tipo_perfil)
)

CREATE TABLE Cadastra (
    cod_escola CHAR(3),
    id_tipo_perfil BIGINT,
	PRIMARY KEY (cod_escola, id_tipo_perfil),
    FOREIGN KEY(cod_escola) REFERENCES Escola (cod_escola),
    FOREIGN KEY(id_tipo_perfil) REFERENCES Tipo_perfil (id_tipo_perfil)
)

CREATE TABLE Usuario (
	rm_usuario CHAR(6) PRIMARY KEY,
	chamados_abertos CHAR(3),
	chamados_concluidos CHAR(3),
	email_usuario VARCHAR(50),
	nome_usuario VARCHAR(30),
	senha_usuario VARCHAR(255),
	telefone_usuario CHAR(11),
	cpf_usuario CHAR(11),
	id_tipo_perfil BIGINT,
	FOREIGN KEY (id_tipo_perfil) REFERENCES Tipo_perfil(id_tipo_perfil)
)

CREATE TABLE Departamento (
	id_departamento BIGINT IDENTITY(1,1) PRIMARY KEY,
	nome_departamento VARCHAR(50),
	desc_departamento VARCHAR(450)
)

CREATE TABLE Conduz (
	rm_usuario CHAR(6),
	id_departamento BIGINT,
	PRIMARY KEY (rm_usuario, id_departamento),
	FOREIGN KEY(rm_usuario) REFERENCES Usuario (rm_usuario),
	FOREIGN KEY(id_departamento) REFERENCES Departamento (id_departamento)
)

CREATE TABLE Tipo_Ambiente (
    id_tipo_ambiente BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome_tipo_ambiente VARCHAR(50)
)

CREATE TABLE Ambiente (
	id_ambiente BIGINT IDENTITY(1,1) PRIMARY KEY,
	num_ambiente BIGINT,
	desc_ambiente VARCHAR(450),
	id_tipo_ambiente BIGINT,
	FOREIGN KEY(id_tipo_ambiente) REFERENCES Tipo_Ambiente (id_tipo_ambiente)
)

CREATE TABLE Tipo_Chamado (
	id_tipo_chamado BIGINT IDENTITY(1,1) PRIMARY KEY,
	nome_tipo_chamado VARCHAR(50),
	id_departamento BIGINT,
	FOREIGN KEY(id_departamento) REFERENCES Departamento(id_departamento)
)

CREATE TABLE Tipo_equipamento (
	id_tipo_equipamento BIGINT IDENTITY(1,1) PRIMARY KEY,
	nome_tipo_equipamento VARCHAR(50),
	id_tipo_chamado BIGINT,
	FOREIGN KEY (id_tipo_chamado) REFERENCES Tipo_Chamado (id_tipo_chamado)
)

CREATE TABLE Equipamento (
	cod_equipamento VARCHAR(255) PRIMARY KEY,
	id_tipo_equipamento BIGINT,
	id_ambiente BIGINT,
	FOREIGN KEY(id_tipo_equipamento) REFERENCES Tipo_equipamento(id_tipo_equipamento),
	FOREIGN KEY(id_ambiente) REFERENCES Ambiente(id_ambiente)
)

CREATE TABLE Chamado (
	id_chamado BIGINT IDENTITY(1,1) PRIMARY KEY,
	prioridade_chamado VARCHAR(20),
	status_atual_geral_chamado VARCHAR(50),
	status_atual_progresso_chamado VARCHAR(50),
	dt_abertura_chamado DATETIME DEFAULT GETDATE(),
	desc_chamado VARCHAR(450),
	dt_conclusao_chamado DATETIME DEFAULT GETDATE(),
	titulo_chamado VARCHAR(255),
	rm_usuario CHAR(6),
	rm_usuario_responsavel CHAR(6),
	id_tipo_chamado BIGINT,
	id_tipo_ambiente BIGINT,
	id_ambiente BIGINT,

	FOREIGN KEY(rm_usuario) REFERENCES Usuario (rm_usuario),
	FOREIGN KEY(rm_usuario_responsavel) REFERENCES Usuario (rm_usuario),
	FOREIGN KEY(id_tipo_chamado) REFERENCES Tipo_Chamado(id_tipo_chamado),
	FOREIGN KEY(id_tipo_ambiente) REFERENCES Tipo_Ambiente (id_tipo_ambiente),
	FOREIGN KEY(id_ambiente) REFERENCES Ambiente (id_ambiente),
	CHECK (prioridade_chamado IN ('Alta', 'Média', 'Baixa')),
	CHECK (status_atual_geral_chamado IN ('Aguardando Aprovação', 'Concluído', 'Pendente')),
	CHECK (status_atual_progresso_chamado IN ('Em análise', 'Aprovado', 'Análise da APM', 'Em andamento', 'Concluído'))
)

CREATE TABLE Feedback (
	id_feedback BIGINT IDENTITY(1,1) PRIMARY KEY,
	dt_feedback DATETIME DEFAULT GETDATE(),
	desc_feedback VARCHAR(450),
	destinatario_feedback VARCHAR(50),
	remetente_feedback VARCHAR(50),
	id_chamado BIGINT,
	rm_usuario CHAR(6),
	FOREIGN KEY(id_chamado) REFERENCES Chamado (id_chamado),
	FOREIGN KEY(rm_usuario) REFERENCES Usuario (rm_usuario)
)

CREATE TABLE Imagem_Chamado (
    id_imagem_chamado BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome_arquivo_imagem_chamado VARCHAR(255),
    caminho_imagem_chamado VARCHAR(255),
    id_chamado BIGINT,
    FOREIGN KEY(id_chamado) REFERENCES Chamado (id_chamado)
)

CREATE TABLE Historico_Status_Progresso (
	id_historico_status_progresso BIGINT IDENTITY(1,1) PRIMARY KEY,
	status_progresso VARCHAR(50),
	dt_alteracao DATETIME DEFAULT GETDATE(),
	dia_semana VARCHAR(20),
	id_chamado BIGINT,
	FOREIGN KEY (id_chamado) REFERENCES Chamado (id_chamado),
	CHECK (status_progresso IN ('Em análise', 'Aprovado', 'Análise da APM', 'Em andamento', 'Concluído'))
)
