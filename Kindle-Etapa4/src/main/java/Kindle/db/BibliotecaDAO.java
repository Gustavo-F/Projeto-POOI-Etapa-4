package Kindle.db;

import Kindle.entidades.Biblioteca;
import Kindle.entidades.Livro;
import Kindle.entidades.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("unused")
public class BibliotecaDAO {


	public void adicionaLivro(Usuario usuario, Livro livro) {
		String sql = ("INSERT INTO livro_usuario (id_livro, login) VALUES (" + livro.getIdLivro() + ", '" + usuario.getLogin() +"');");
		UtilDB.alterarDB(sql);
	}

	public void removeLivro(Usuario usuario, Livro livro) {
		String sql = ("DELETE FROM livro_usuario WHERE id_livro = " + livro.getIdLivro() + " AND login = '" + usuario.getLogin() +"';");
		UtilDB.alterarDB(sql);
	}

	public Biblioteca buscaLivrosBiblioteca(Usuario usuario) {
		
		Biblioteca biblioteca = new Biblioteca();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = UtilDB.getConexao().prepareStatement("SELECT id_livro FROM livro_usuario WHERE login = '" + usuario.getLogin() + "';");
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				Livro livro = new LivroDAO().buscarLivroPorID(rs.getInt("id_livro"));
				
				biblioteca.adicionaLivro(livro);
			}
			
		}catch(SQLException e) {
			System.err.println("Erro ao buscar livros da bibliteca do usuario!");
		}
		
		return biblioteca;
	}

}
