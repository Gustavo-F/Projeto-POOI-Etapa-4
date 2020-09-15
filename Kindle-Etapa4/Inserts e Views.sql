INSERT INTO genero (genero_nome) VALUES
('Romance'), ('Suspence');

INSERT INTO pessoa (nome, email, telefone) VALUES 
("Saraiva", "contato@saraiva.com.br", "1120518946");

INSERT INTO pessoa_juridica (id_pessoa, cnpj) VALUES
(1, "14785236914785"); 

INSERT INTO editora (id_pessoa) VALUES
(1);

DROP VIEW IF EXISTS editora_select;
CREATE VIEW editora_select AS 
	SELECT pe.id_pessoa, e.id_editora, nome, cnpj, email, telefone
	FROM editora AS e
	INNER JOIN pessoa_juridica AS pj ON pj.id_pessoa = e.id_pessoa
	INNER JOIN pessoa AS pe ON pe.id_pessoa = pj.id_pessoa;
	

SELECT *
FROM editora_select;

INSERT INTO pessoa (nome, sobrenome, email, telefone) VALUES 
('Gustavo', 'Ferreira', 'gustavo@gmail.com', '94992599119');

INSERT INTO pessoa_fisica (id_pessoa, cpf) VALUES 
(2, '05173681292');

INSERT INTO usuario (id_pessoa, login, senha, permissao) VALUES
(2, 'gustavo', '123456', 'U');

DROP VIEW IF EXISTS usurio_select;
CREATE VIEW usuario_select AS
	SELECT us.id_pessoa, nome, sobrenome, cpf, telefone, email, login, senha, permissao
	FROM usuario AS us
	INNER JOIN pessoa_fisica AS pf ON pf.id_pessoa = us.id_pessoa
	INNER JOIN pessoa AS pe ON pe.id_pessoa = pf.id_pessoa;
	
SELECT *
FROM usuario_select;

DROP VIEW IF EXISTS escritor_select;
CREATE VIEW escritor_select AS 
	SELECT es.id_pessoa, id_escritor, nome, sobrenome, cpf, telefone, email
	FROM escritor AS es 
	INNER JOIN pessoa_fisica AS pf ON pf.id_pessoa = es.id_pessoa
	INNER JOIN pessoa AS pe ON pe.id_pessoa = pf.id_pessoa;
	
SELECT *
FROM escritor_select;

DROP VIEW IF EXISTS livro_select;
CREATE VIEW livro_select AS 
	SELECT id_livro, titulo, paginas, e.id_editora
	FROM livro AS l
	INNER JOIN editora AS e ON e.id_editora = l.id_editora;
	
INSERT INTO livro (titulo, paginas, id_editora) VALUES
('As Aventuras de Artur', 458, 2);
	
	
-- DROP VIEW IF EXISTS livro_usuario_select;
-- CREATE VIEW livro_usuario_select AS
-- 	SELECT id_livro, titulo, paginas, id_editora;
	
	
	
	
	