package Kindle.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Kindle.entidades.Genero;

public class GeneroDAO implements InterfaceDAO<Genero>{

	@Override
	public void adiciona(Genero referencia) {
		String sql = ("INSERT INTO genero (genero_nome) VALUES ('" + referencia.getGenero() + "');");
		UtilDB.alterarDB(sql);
	}

	@Override
	public void remove(Genero referencia) {
		String sql = ("DELETE FROM genero WHERE genero_nome = '" + referencia.getGenero() + "';");
		UtilDB.alterarDB(sql);
	}

	@Override
	public List<Genero> lista() {
		
		ArrayList<Genero> generos = new ArrayList<Genero>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = UtilDB.getConexao().prepareStatement("SELECT * FROM genero");
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				Genero genero = new Genero();
				genero.setGenero(rs.getString("genero_nome"));
				
				generos.add(genero);
			}
		}catch(SQLException e) {
			System.err.println("Erro ao listar generos!");
		}
		
		return generos;
	}
	
	public Genero buscaGenero(String generoNome) {
		
		Genero retorno = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = UtilDB.getConexao().prepareStatement("SELECT * FROM genero WHERE genero_nome = '" + generoNome + "';");
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				retorno = new Genero(rs.getString("genero_nome"));
			}
			
		}catch(SQLException e) {
			System.err.println("Erro ao buscar genero.");
		}
		
		return retorno;
	}

	public static void atualizaGenero(String novoGeneroNome, String antigoGeneroNome) {
		String sql = ("UPDATE genero SET genero_nome = '" + novoGeneroNome +"' WHERE genero_nome = '" + antigoGeneroNome + "';");
		UtilDB.alterarDB(sql);
	}
	
}





















