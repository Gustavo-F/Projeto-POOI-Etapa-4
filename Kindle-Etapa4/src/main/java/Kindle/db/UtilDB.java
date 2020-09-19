package Kindle.db;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class UtilDB {

	private static Connection conexao;

	private static Connection abrirConexao() {
		
		try {
			Class.forName("org.sqlite.JDBC");
			conexao = DriverManager.getConnection("jdbc:sqlite:Kindle.sqlite");
		}catch(SQLException e) {
			System.err.println("Erro ao abrir conexao.");
		}catch(ClassNotFoundException e2) {
			System.err.println("Biblioteca SQLite não está funcionando corretamente.");
		}
		
		return conexao;
	}
	
	public static Connection getConexao() {
		try {
			if(conexao == null)
				abrirConexao();
			
			if(conexao.isClosed())
				abrirConexao();
			
		}catch(SQLException e) {
			System.err.println("Erro ao obter conexao.");
		}
		
		return conexao;
	}
	
	public static void fecharConexao() {
		if(conexao == null)
			return;
		
		try {
			if(!conexao.isClosed())
				conexao.close();
		}catch(SQLException e) {
			System.err.println("Erro ao fechar conexao.");
		}
	}
	
	public static void alterarDB(String sql) {
		
		Connection conexao = getConexao();
		Statement stm = null;
		
		try {
			stm = conexao.createStatement();
			stm.executeUpdate(sql);
			stm.close();
			System.out.println("Executei: " + sql);
		}catch(SQLException e) {
			System.err.println("Erro ao executar query.");
		}
		
	}
	
	public static void iniDB() {
		criaTabelaGenero();
		criaTabelaPessoa();
		criaTabelaPessoaFisica();
		criaTabelaPessoaJuridica();
		criaTabelaEditora();
		criaTabelaEscritor();
		criaTabelaUsuario();
		criaTabelaLivro();
		criaTabelaGeneroLivro();
		criaTabelaEscritorLivro();
		criaTabelaLivroUsuario();
		registrosIniciais();
	}
	
	private static void criaTabelaGenero() {
		String sql = ("DROP TABLE IF EXISTS genero;\r\n" + 
				"CREATE TABLE genero(\r\n" + 
				"genero_nome VARCHAR(50)NOT NULL PRIMARY KEY\r\n" + 
				");");
		alterarDB(sql);
	}
	
	private static void criaTabelaPessoa() {
		String sql = ("DROP TABLE IF EXISTS pessoa;\r\n" + 
				"CREATE TABLE pessoa(\r\n" + 
				"	id_pessoa INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" + 
				"	nome VARCHAR(50) NOT NULL,\r\n" + 
				"	sobrenome VARCHAR(30),\r\n" + 
				"	email VARCHAR(100) NOT NULL UNIQUE,\r\n" + 
				"	telefone VARCHAR(15) NOT NULL\r\n" + 
				");");
		alterarDB(sql);
	}
	
	private static void criaTabelaPessoaFisica() {
		String sql = ("DROP TABLE IF EXISTS pessoa_fisica;\r\n" + 
				"CREATE TABLE pessoa_fisica(\r\n" + 
				"	id_pessoa INTEGER NOT NULL PRIMARY KEY,\r\n" + 
				"	cpf VARCHAR(11) NOT NULL UNIQUE,\r\n" + 
				"	FOREIGN KEY (id_pessoa)\r\n" + 
				"		REFERENCES pessoa (id_pessoa)\r\n" + 
				");");
		alterarDB(sql);
	}
	
	private static void criaTabelaPessoaJuridica() {
		String sql = ("DROP TABLE IF EXISTS pessoa_juridica;\r\n" + 
				"CREATE TABLE pessoa_juridica(\r\n" + 
				"	id_pessoa INTEGER NOT NULL PRIMARY KEY,\r\n" + 
				"	cnpj VARCHAR(14) NOT NULL UNIQUE,\r\n" + 
				"	FOREIGN KEY (id_pessoa)\r\n" + 
				"		REFERENCES pessoa (id_pessoa)\r\n" + 
				");");
		alterarDB(sql);
	}
	
	private static void criaTabelaEditora() {
		String sql = ("DROP TABLE IF EXISTS editora;\r\n" + 
				"CREATE TABLE editora(\r\n" + 
				"	id_pessoa INTEGER NOT NULL,\r\n" + 
				"	id_editora INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" + 
				"	FOREIGN KEY (id_pessoa)\r\n" + 
				"		REFERENCES pessoa_juridica (id_pessoa)\r\n" + 
				");");
		
		alterarDB(sql);
	}
	
	private static void criaTabelaEscritor() {
		String sql = ("DROP TABLE IF EXISTS escritor;\r\n" + 
				"CREATE TABLE escritor(\r\n" + 
				"	id_pessoa INTEGER NOT NULL,\r\n" + 
				"	id_escritor INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" + 
				"	FOREIGN KEY (id_pessoa)\r\n" + 
				"		REFERENCES pessoa_fisica (id_pessoa)\r\n" + 
				");");
		
		alterarDB(sql);
	}
	
	private static void criaTabelaUsuario() {
		String sql = ("DROP TABLE IF EXISTS usuario;\r\n" + 
				"CREATE TABLE usuario(\r\n" + 
				"	id_pessoa INTEGER NOT NULL,\r\n" + 
				"	login VARCHAR(20) NOT NULL PRIMARY KEY,\r\n" + 
				"	senha VARCHAR(20) NOT NULL,\r\n" + 
				"	permissao CHARACTER NOT NULL,\r\n" + 
				"	FOREIGN KEY (id_pessoa)\r\n" + 
				"		REFERENCES pessoa_fisica (id_pessoa)\r\n" + 
				");");
		alterarDB(sql);
	}
	
	private static void criaTabelaLivro() {
		String sql = ("DROP TABLE IF EXISTS livro;\r\n" + 
				"CREATE TABLE livro(\r\n" + 
				"	id_livro INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" + 
				"	titulo VARCHAR(100) NOT NULL UNIQUE,\r\n" + 
				"	paginas INTEGER,\r\n" + 
				"	id_editora INTEGER NOT NULL,\r\n" + 
				"	FOREIGN KEY (id_editora)\r\n" + 
				"		REFERENCES editora (id_editora)\r\n" + 
				");");
		alterarDB(sql);
	}
	
	private static void criaTabelaGeneroLivro() {
		String sql = ("DROP TABLE IF EXISTS genero_livro;\r\n" + 
				"CREATE TABLE genero_livro(\r\n" + 
				"	genero_nome VARCHAR(50) NOT NULL,\r\n" + 
				"	id_livro INTEGER NOT NULL,\r\n" + 
				"	PRIMARY KEY (genero_nome, id_livro),\r\n" + 
				"	FOREIGN KEY (genero_nome)\r\n" + 
				"		REFERENCES genero (genero_nome),\r\n" + 
				"	FOREIGN KEY (id_livro)\r\n" + 
				"		REFERENCES livro (id_livro)\r\n" + 
				");");
		alterarDB(sql);
	}
	
	private static void criaTabelaEscritorLivro() {
		String sql = ("DROP TABLE IF EXISTS escritor_livro;\r\n" + 
				"CREATE TABLE escritor_livro(\r\n" + 
				"	id_escritor INTEGER NOT NULL,\r\n" + 
				"	id_livro INTEGER NOT NULL,\r\n" + 
				"	PRIMARY KEY (id_escritor, id_livro),\r\n" + 
				"	FOREIGN KEY (id_escritor)\r\n" + 
				"		REFERENCES escritor (id_escritor),\r\n" + 
				"	FOREIGN KEY (id_livro)\r\n" + 
				"		REFERENCES livro (id_livro)\r\n" + 
				");");
		alterarDB(sql);
	}
	
	private static void criaTabelaLivroUsuario() {
		String sql = ("DROP TABLE IF EXISTS livro_usuario;\r\n" + 
				"CREATE TABLE livro_usuario(\r\n" + 
				"	id_livro INTEGER NOT NULL,\r\n" + 
				"	login VARCHAR(20) NOT NULL,\r\n" + 
				"	PRIMARY KEY (id_livro, login),\r\n" + 
				"	FOREIGN KEY (id_livro)\r\n" + 
				"		REFERENCES livro (id_livro),\r\n" + 
				"	FOREIGN KEY (login)\r\n" + 
				"		REFERENCES usuario (login)\r\n" + 
				");");
		alterarDB(sql);
	}
	
	private static void registrosIniciais() {
		String sql = ("INSERT INTO genero (genero_nome) VALUES\r\n" + 
				"('Romance'), ('Suspence'), ('Ação'), ('Aventura'), ('Ficção Científica');");
		alterarDB(sql);
		
		sql = ("INSERT INTO pessoa (nome, email, telefone) VALUES \r\n" + 
				"('Saraiva', 'contato@saraiva.com.br', '1120518946'),\r\n" + 
				"('Pearson', 'contato@pearson.com', '11998561478');\r\n" + 
				"INSERT INTO pessoa_juridica (id_pessoa, cnpj) VALUES\r\n" + 
				"(1, '14785236914785'),\r\n" + 
				"(2, '12345678912345'); \r\n" + 
				"INSERT INTO editora (id_pessoa) VALUES\r\n" + 
				"(1), (2);");
		alterarDB(sql);
		
		sql = ("DROP VIEW IF EXISTS editora_select;\r\n" + 
				"CREATE VIEW editora_select AS \r\n" + 
				"	SELECT pe.id_pessoa, e.id_editora, nome, cnpj, email, telefone\r\n" + 
				"	FROM editora AS e\r\n" + 
				"	INNER JOIN pessoa_juridica AS pj ON pj.id_pessoa = e.id_pessoa\r\n" + 
				"	INNER JOIN pessoa AS pe ON pe.id_pessoa = pj.id_pessoa;");
		alterarDB(sql);
		
		sql = ("INSERT INTO pessoa (nome, sobrenome, email, telefone) VALUES \r\n" + 
				"('Gustavo', 'Ferreira', 'gustavo@gmail.com', '94992599119'),\r\n" + 
				"('Admin', 'Admin', 'admin@admin','00000000000');\r\n" + 
				"INSERT INTO pessoa_fisica (id_pessoa, cpf) VALUES \r\n" + 
				"(3, '05173681292'), (4, '000000000000');\r\n" + 
				"INSERT INTO usuario (id_pessoa, login, senha, permissao) VALUES\r\n" + 
				"(3, 'gustavo', '123456', 'U'),\r\n" + 
				"(4, 'admin', 'admin', 'A');");
		alterarDB(sql);
		
		sql = ("DROP VIEW IF EXISTS usurio_select;\r\n" + 
				"CREATE VIEW usuario_select AS\r\n" + 
				"	SELECT us.id_pessoa, nome, sobrenome, cpf, telefone, email, login, senha, permissao\r\n" + 
				"	FROM usuario AS us\r\n" + 
				"	INNER JOIN pessoa_fisica AS pf ON pf.id_pessoa = us.id_pessoa\r\n" + 
				"	INNER JOIN pessoa AS pe ON pe.id_pessoa = pf.id_pessoa;");
		alterarDB(sql);
		
		sql = ("INSERT INTO pessoa (nome, sobrenome, email, telefone) VALUES\r\n" + 
				"('John', 'Green', 'john@outlook.com', '47988554422'),\r\n" + 
				"('Jorge', 'Matheus', 'jorge@gmail.com', '11966225588');\r\n" + 
				"INSERT INTO pessoa_fisica(id_pessoa, cpf) VALUES\r\n" + 
				"(5, '78945612311'), (6, '96325874155');\r\n" + 
				"INSERT INTO escritor (id_pessoa) VALUES \r\n" + 
				"(5), (6);");
		alterarDB(sql);
		
		sql = ("DROP VIEW IF EXISTS escritor_select;\r\n" + 
				"CREATE VIEW escritor_select AS \r\n" + 
				"	SELECT es.id_pessoa, id_escritor, nome, sobrenome, cpf, telefone, email\r\n" + 
				"	FROM escritor AS es \r\n" + 
				"	INNER JOIN pessoa_fisica AS pf ON pf.id_pessoa = es.id_pessoa\r\n" + 
				"	INNER JOIN pessoa AS pe ON pe.id_pessoa = pf.id_pessoa;");
		alterarDB(sql);
		
		sql = ("DROP VIEW IF EXISTS livro_select;\r\n" + 
				"CREATE VIEW livro_select AS \r\n" + 
				"	SELECT id_livro, titulo, paginas, e.id_editora\r\n" + 
				"	FROM livro AS l\r\n" + 
				"	INNER JOIN editora AS e ON e.id_editora = l.id_editora;");
		alterarDB(sql);
		
		sql = ("INSERT INTO livro (titulo, paginas, id_editora) VALUES\r\n" + 
				"('As Aventuras de Artur', 458, 2),\r\n" + 
				"('Estrelas', 666, 1),\r\n" + 
				"('Céu', 584, 1);");
		alterarDB(sql);
		
		sql = ("INSERT INTO livro_usuario (id_livro, login) VALUES\r\n" + 
				"(1, 'gustavo'), (3, 'gustavo');");
		alterarDB(sql);
		
		sql = ("INSERT INTO genero_livro (genero_nome, id_livro) VALUES \r\n" + 
				"('Aventura', 1), ('Ação', 1);");
		alterarDB(sql);
		
		sql = ("INSERT INTO escritor_livro (id_escritor, id_livro) VALUES \r\n" + 
				"(1, 1),(2, 1), (2, 3);	");
		alterarDB(sql);
	}
	
}



























