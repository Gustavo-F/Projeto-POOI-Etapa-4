package Kindle.db;

import Kindle.entidades.Livro;
import Kindle.entidades.Editora;
import Kindle.entidades.Escritor;
import Kindle.entidades.Genero;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class LivroDAO implements InterfaceDAO<Livro>{

	@Override
	public void adiciona(Livro referencia) {
		String sql = ("INSERT INTO livro (titulo, paginas, id_editora) VALUES ('" + referencia.getTitulo() + "',"
				+ " " + referencia.getPaginas() + ", " + referencia.getEditora().getIdEditora() + ");");
		
		UtilDB.alterarDB(sql);
	}

	@Override
	public void remove(Livro referencia) {
		String sql = ("DELETE FROM genero_livro WHERE id_livro = " + referencia.getIdLivro() +";");
		UtilDB.alterarDB(sql);
		
		sql = ("DELETE FROM escritor_livro WHERE id_livro = " + referencia.getIdLivro() +";");
		UtilDB.alterarDB(sql);
		
		sql = ("DELETE FROM livro WHERE id_livro = " + referencia.getIdLivro() +";");
		UtilDB.alterarDB(sql);
	}

	@Override
	public List<Livro> lista() {
		
		List<Livro> livros = new ArrayList<Livro>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = UtilDB.getConexao().prepareStatement("SELECT * FROM livro_select;");
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				Livro livro = new Livro();
				livro.setIdLivro(rs.getInt("id_livro"));
				livro.setTitulo(rs.getString("titulo"));
				livro.setPaginas(rs.getInt("paginas"));
				livro.setEscritores(getEscritoresLivro(livro));
				livro.setGenero(getGenerosLivro(livro));
				livro.setEditora(EditoraDAO.getEditora(rs.getInt("id_editora")));
				
				livros.add(livro);
			}
		}catch(SQLException e) {
			System.err.println("Erro ao listar livros!");
		}
		
		return livros;
	}
	
	public Livro buscarLivroPorID(int idLivro) {
		Livro livro = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = UtilDB.getConexao().prepareStatement("SELECT * FROM livro_select WHERE id_livro = " + idLivro + ";");
			rs = pstm.executeQuery();
			
			livro = new Livro();
			livro.setIdLivro(rs.getInt("id_livro"));
			livro.setTitulo(rs.getString("titulo"));
			livro.setEscritores(getEscritoresLivro(livro));
			livro.setGenero(getGenerosLivro(livro));
			livro.setPaginas(rs.getInt("paginas"));
			livro.setEditora(EditoraDAO.getEditora(rs.getInt("id_editora")));
			
		}catch(SQLException e) {
			System.err.println("Erro ao buscar livro!");
		}
		
		return livro;
	}
	
	public static ArrayList<Escritor> getEscritoresLivro(Livro livro) {
		
		ArrayList<Escritor> escritores = new ArrayList<Escritor>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			
			pstm = UtilDB.getConexao().prepareStatement("SELECT * FROM escritor_livro WHERE id_livro = " + livro.getIdLivro() + ";");
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				Escritor escritor = EscritorDAO.getEscritorPorID(rs.getInt("id_escritor"));
				escritores.add(escritor);
			}
			
		}catch(SQLException e) {
			System.err.println("Erro ao obter escritores do livro!");
		}
		
		return escritores;
	}
	
	public static ArrayList<Genero> getGenerosLivro(Livro livro) {
		
		ArrayList<Genero> generos = new ArrayList<Genero>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = UtilDB.getConexao().prepareStatement("SELECT * FROM genero_livro WHERE id_livro = " + livro.getIdLivro() + ";");
			rs = pstm .executeQuery();
			
			while(rs.next()) {
				Genero genero = new Genero(rs.getString("genero_nome"));
				
				generos.add(genero);
			}
				
			
		}catch(SQLException e) {
			System.err.println("Erro ao obter gêneros do livro!");
		}
		
		return generos;
	}
	
	private static int getIdLivro(Livro livro) {
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = UtilDB.getConexao().prepareStatement("SELECT id_livro FROM livro WHERE titulo = '" + livro.getTitulo() + "';");
			rs = pstm.executeQuery();
			
			return rs.getInt("id_livro");
		}catch(SQLException e) {
			System.err.println("Erro ao obter id do livro!");
		}
		
		return 0;	
	}
	
	public static void adicionaEscritorLivro(Escritor escritor, Livro livro) {
		livro.setIdLivro(getIdLivro(livro));
		if(livro.getIdLivro() == 0)
			return;
			
			
		String sql = ("INSERT INTO escritor_livro (id_escritor, id_livro) VALUES\n"
					+ "(" + escritor.getIdEscritor() +", " + livro.getIdLivro() +")");
		UtilDB.alterarDB(sql);
	}
	
	public static void removeEscritorLivro(Escritor escritor, Livro livro) {
		String sql = ("DELETE FROM escritor_livro WHERE id_escritor = " + escritor.getIdEscritor() + " AND id_livro = " + livro.getIdLivro() + ";");
		UtilDB.alterarDB(sql);
	}
	
	public static void adicionaGeneroLivro(Genero genero, Livro livro) {
		livro.setIdLivro(getIdLivro(livro));
		if(livro.getIdLivro() == 0)
			return;
		
		String sql = ("INSERT INTO genero_livro (genero_nome, id_livro) VALUES\n"
					+ "('" + genero.getGenero() +"', " + livro.getIdLivro() + ");");
		UtilDB.alterarDB(sql);
	}
	
	public static void removeGeneroLivro(Genero genero, Livro livro) {
		String sql = ("DELETE FROM genero_livro WHERE genero_nome = '" + genero.getGenero() +"' "
					+ "AND id_livro = " + livro.getIdLivro() +";");
		UtilDB.alterarDB(sql);
	}
	
	public static void atualizaEditora(Livro livro, Editora editora) {
		String sql = ("UPDATE livro SET id_editora = " + editora.getIdEditora() +" WHERE id_livro = " + livro.getIdLivro() +";");
		UtilDB.alterarDB(sql);
	}
	
	public static void atualizaTitulo(Livro livro, String novoTitulo) {
		String sql = ("UPDATE livro SET titulo = '" + novoTitulo + "' WHERE id_livro = " + livro.getIdLivro() +";");
		UtilDB.alterarDB(sql);
	}
	
	public static void atualizaPaginas(Livro livro, int novasPaginas) {
		String sql = ("UPDATE livro SET paginas = " + novasPaginas +" WHERE id_livro = " + livro.getIdLivro() + ";");
		UtilDB.alterarDB(sql);
	}
	
}

























