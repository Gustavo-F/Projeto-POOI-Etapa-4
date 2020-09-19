DROP TABLE IF EXISTS genero;
CREATE TABLE genero(
	genero_nome VARCHAR(50)NOT NULL PRIMARY KEY
);

DROP TABLE IF EXISTS pessoa;
CREATE TABLE pessoa(
	id_pessoa INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	nome VARCHAR(50) NOT NULL,
	sobrenome VARCHAR(30),
	email VARCHAR(100) NOT NULL UNIQUE,
	telefone VARCHAR(15) NOT NULL
);

DROP TABLE IF EXISTS pessoa_fisica;
CREATE TABLE pessoa_fisica(
	id_pessoa INTEGER NOT NULL PRIMARY KEY,
	cpf VARCHAR(11) NOT NULL UNIQUE,
	FOREIGN KEY (id_pessoa)
		REFERENCES pessoa (id_pessoa)
);

DROP TABLE IF EXISTS pessoa_juridica;
CREATE TABLE pessoa_juridica(
	id_pessoa INTEGER NOT NULL PRIMARY KEY,
	cnpj VARCHAR(14) NOT NULL UNIQUE,
	FOREIGN KEY (id_pessoa)
		REFERENCES pessoa (id_pessoa)
);

DROP TABLE IF EXISTS editora;
CREATE TABLE editora(
	id_pessoa INTEGER NOT NULL,
	id_editora INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	FOREIGN KEY (id_pessoa)
		REFERENCES pessoa_juridica (id_pessoa)
);

DROP TABLE IF EXISTS escritor;
CREATE TABLE escritor(
	id_pessoa INTEGER NOT NULL,
	id_escritor INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	FOREIGN KEY (id_pessoa)
		REFERENCES pessoa_fisica (id_pessoa)
);

DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario(
	id_pessoa INTEGER NOT NULL,
	login VARCHAR(20) NOT NULL PRIMARY KEY,
	senha VARCHAR(20) NOT NULL,
	permissao CHARACTER NOT NULL,
	FOREIGN KEY (id_pessoa)
		REFERENCES pessoa_fisica (id_pessoa)
);

DROP TABLE IF EXISTS livro;
CREATE TABLE livro(
	id_livro INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	titulo VARCHAR(100) NOT NULL UNIQUE,
	paginas INTEGER,
	id_editora INTEGER NOT NULL,
	FOREIGN KEY (id_editora)
		REFERENCES editora (id_editora)
);

DROP TABLE IF EXISTS genero_livro;
CREATE TABLE genero_livro(
	genero_nome VARCHAR(50) NOT NULL,
	id_livro INTEGER NOT NULL,
	PRIMARY KEY (genero_nome, id_livro),
	FOREIGN KEY (genero_nome)
		REFERENCES genero (genero_nome),
	FOREIGN KEY (id_livro)
		REFERENCES livro (id_livro)
);

DROP TABLE IF EXISTS escritor_livro;
CREATE TABLE escritor_livro(
	id_escritor INTEGER NOT NULL,
	id_livro INTEGER NOT NULL,
	PRIMARY KEY (id_escritor, id_livro),
	FOREIGN KEY (id_escritor)
		REFERENCES escritor (id_escritor),
	FOREIGN KEY (id_livro)
		REFERENCES livro (id_livro)
);

DROP TABLE IF EXISTS livro_usuario;
CREATE TABLE livro_usuario(
	id_livro INTEGER NOT NULL,
	login VARCHAR(20) NOT NULL,
	PRIMARY KEY (id_livro, login),
	FOREIGN KEY (id_livro)
		REFERENCES livro (id_livro),
	FOREIGN KEY (login)
		REFERENCES usuario (login)
);




