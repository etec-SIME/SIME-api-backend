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

CREATE TABLE Cadastra (
    cod_escola CHAR(3),
    id_tipo_perfil BIGINT,
	PRIMARY KEY (cod_escola, id_tipo_perfil),
    FOREIGN KEY(cod_escola) REFERENCES Escola (cod_escola),
    FOREIGN KEY(id_tipo_perfil) REFERENCES Tipo_perfil (id_tipo_perfil)
)

CREATE TABLE Usuario (
	rm_usuario CHAR(6) PRIMARY KEY,
	email_usuario VARCHAR(50),
	nome_usuario VARCHAR(30),
	dt_nascimento_usuario DATETIME,
	telefone_usuario CHAR(11),
	id_tipo_perfil BIGINT,
	FOREIGN KEY (id_tipo_perfil) REFERENCES Tipo_perfil(id_tipo_perfil) 
)

CREATE TABLE Chamado (
	id_chamado BIGINT IDENTITY(1,1) PRIMARY KEY,
	dt_abertura_chamado DATETIME,
	desc_chamado VARCHAR(450),
	dt_conclusao_chamado DATETIME,
	img_chamado VARCHAR(255),
	local_chamado VARCHAR(255),
	titulo_chamado VARCHAR(255),
	tipo_chamado VARCHAR(255),
	CONSTRAINT CHK_tipo_chamado CHECK (tipo_chamado IN 
		('Manutenção de Infraestrutura', 'Reparo de Equipamento', 'Limpeza')) -- Valores provisórios
)

CREATE TABLE Gerenciar (
	rm_usuario CHAR(6),
	id_chamado BIGINT,
	PRIMARY KEY(rm_usuario, id_chamado),
	FOREIGN KEY(rm_usuario) REFERENCES Usuario (rm_usuario),
	FOREIGN KEY(id_chamado) REFERENCES Chamado (id_chamado)
)

CREATE TABLE Feedback (
	id_feedback BIGINT IDENTITY(1,1) PRIMARY KEY,
	dt_feedback DATETIME,
	desc_feedback VARCHAR(450),
	id_chamado BIGINT,
	rm_usuario CHAR(6),
	FOREIGN KEY(id_chamado) REFERENCES Chamado (id_chamado),
	FOREIGN KEY(rm_usuario) REFERENCES Usuario (rm_usuario)
)

DROP DATABASE DBS_SIME

