INSERT INTO genero (genero_nome) VALUES
('Romance'), ('Suspence'), ('Ação'), ('Aventura'), ('Ficção Científica');

INSERT INTO pessoa (nome, email, telefone) VALUES 
('Saraiva', 'contato@saraiva.com.br', '1120518946'),
('Pearson', 'contato@pearson.com', '11998561478');
INSERT INTO pessoa_juridica (id_pessoa, cnpj) VALUES
(1, '14785236914785'),
(2, '12345678912345'); 
INSERT INTO editora (id_pessoa) VALUES
(1), (2);

DROP VIEW IF EXISTS editora_select;
CREATE VIEW editora_select AS 
	SELECT pe.id_pessoa, e.id_editora, nome, cnpj, email, telefone
	FROM editora AS e
	INNER JOIN pessoa_juridica AS pj ON pj.id_pessoa = e.id_pessoa
	INNER JOIN pessoa AS pe ON pe.id_pessoa = pj.id_pessoa;
	

SELECT *
FROM editora_select;

INSERT INTO pessoa (nome, sobrenome, email, telefone) VALUES 
('Gustavo', 'Ferreira', 'gustavo@gmail.com', '94992599119'),
('Admin', '', 'admin@admin','');
INSERT INTO pessoa_fisica (id_pessoa, cpf) VALUES 
(3, '05173681292'), (4, '000000000000');
INSERT INTO usuario (id_pessoa, login, senha, permissao) VALUES
(3, 'gustavo', '123456', 'U'),
(4, 'admin', 'admin', 'A');

DROP VIEW IF EXISTS usurio_select;
CREATE VIEW usuario_select AS
	SELECT us.id_pessoa, nome, sobrenome, cpf, telefone, email, login, senha, permissao
	FROM usuario AS us
	INNER JOIN pessoa_fisica AS pf ON pf.id_pessoa = us.id_pessoa
	INNER JOIN pessoa AS pe ON pe.id_pessoa = pf.id_pessoa;
	
SELECT *
FROM usuario_select;

	INSERT INTO pessoa (nome, sobrenome, email, telefone) VALUES
	('John', 'Green', 'john@outlook.com', '47988554422'),
	('Jorge', 'Matheus', 'jorge@gmail.com', '11966225588');
	INSERT INTO pessoa_fisica(id_pessoa, cpf) VALUES
	(5, '78945612311'), (6, '96325874155');
	INSERT INTO escritor (id_pessoa) VALUES 
	(5), (6);

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
('As Aventuras de Artur', 458, 2),
('Estrelas', 666, 1),
('Céu', 584, 1);

INSERT INTO livro_usuario (id_livro, login) VALUES
(1, 'gustavo'), (3, 'gustavo');
	
INSERT INTO genero_livro (genero_nome, id_livro) VALUES 
('Aventura', 1), ('Ação', 1);

INSERT INTO escritor_livro (id_escritor, id_livro) VALUES 
(1, 1),(2, 1), (2, 3);	
-- DROP VIEW IF EXISTS livro_usuario_select;
-- CREATE VIEW livro_usuario_select AS
-- 	SELECT id_livro, titulo, paginas, id_editora;
	
	
	
	
	