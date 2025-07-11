CREATE DATABASE DBS_SIME

USE DBS_SIME

CREATE TABLE Escola (
	cod_escola CHAR(3) PRIMARY KEY,
	cnpj_escola CHAR(14),
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
	senha_usuario VARCHAR(30),
	telefone_usuario CHAR(11),
	cpf_usuario CHAR(11),
	id_tipo_perfil BIGINT,
	FOREIGN KEY (id_tipo_perfil) REFERENCES Tipo_perfil(id_tipo_perfil) 
)

SELECT u.rm_usuario, u.id_tipo_perfil, u.senha_usuario, es.cod_escola
FROM Usuario u
JOIN Tipo_perfil tp ON u.id_tipo_perfil = tp.id_tipo_perfil
JOIN Cadastra cd ON tp.id_tipo_perfil = cd.id_tipo_perfil
JOIN Escola es ON cd.cod_escola = es.cod_escola

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

CREATE TABLE Ambiente (
	id_ambiente BIGINT IDENTITY(1,1) PRIMARY KEY,
	desc_ambiente VARCHAR(450),
	nome_ambiente VARCHAR(50)
)

CREATE TABLE Tipo_equipamento (
	id_tipo_equipamento BIGINT IDENTITY(1,1) PRIMARY KEY,
	nome_tipo_equipamento VARCHAR(50),
	img_tipo_equipamento VARCHAR(255),
	id_ambiente BIGINT,
	FOREIGN KEY (id_ambiente) REFERENCES Ambiente (id_ambiente)
)

CREATE TABLE Contem (
	id_ambiente BIGINT,
	id_tipo_equipamento BIGINT,
	PRIMARY KEY (id_ambiente, id_tipo_equipamento),
	FOREIGN KEY(id_ambiente) REFERENCES Ambiente (id_ambiente),
	FOREIGN KEY(id_tipo_equipamento) REFERENCES Tipo_equipamento (id_tipo_equipamento)
)

CREATE TABLE Equipamento (
	cod_equipamento BIGINT IDENTITY(1,1) PRIMARY KEY,
	id_tipo_equipamento BIGINT,
	FOREIGN KEY(id_tipo_equipamento) REFERENCES Tipo_equipamento(id_tipo_equipamento)
)

CREATE TABLE Tipo_Chamado(
	id_tipo_chamado BIGINT IDENTITY(1,1) PRIMARY KEY,
	nome_tipo_chamado VARCHAR(50),
	id_departamento BIGINT,
	FOREIGN KEY(id_departamento) REFERENCES Departamento(id_departamento)
)

CREATE TABLE Chamado (
	id_chamado BIGINT IDENTITY(1,1) PRIMARY KEY,
	prioridade_chamado VARCHAR(20),
	status_chamado VARCHAR(20),
	dt_abertura_chamado DATETIME,
	desc_chamado VARCHAR(450),
	dt_conclusao_chamado DATETIME,
	img_chamado VARCHAR(255),
	local_chamado VARCHAR(255),
	titulo_chamado VARCHAR(255),
	rm_usuario CHAR(6),
	rm_usuario_responsavel CHAR(6),
	id_ambiente BIGINT,
	id_tipo_chamado BIGINT,

	FOREIGN KEY(rm_usuario) REFERENCES Usuario (rm_usuario),
	FOREIGN KEY(rm_usuario_responsavel) REFERENCES Usuario (rm_usuario),
	FOREIGN KEY(id_ambiente) REFERENCES Ambiente (id_ambiente),
	FOREIGN KEY(id_tipo_chamado) REFERENCES Tipo_Chamado(id_tipo_chamado),
	CHECK (prioridade_chamado IN ('Alta Prioridade', 'Média Prioridade', 'Baixa Prioridade')),
	CHECK (status_chamado IN ('Aguardando Avaliação', 'Recusado', 'Concluído', 'Pendente'))
)

CREATE TABLE Feedback (
	id_feedback BIGINT IDENTITY(1,1) PRIMARY KEY,
	dt_feedback DATETIME,
	desc_feedback VARCHAR(450),
	destinatario_feedback VARCHAR(50),
	remetente_feedback VARCHAR(50),
	id_chamado BIGINT,
	rm_usuario CHAR(6),
	FOREIGN KEY(id_chamado) REFERENCES Chamado (id_chamado),
	FOREIGN KEY(rm_usuario) REFERENCES Usuario (rm_usuario)
)

ALTER TABLE Usuario ADD DTYPE VARCHAR(31);
ALTER TABLE Usuario ADD cpf_usuario CHAR(11);

DROP DATABASE DBS_SIME
