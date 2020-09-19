package Kindle.db;

import Kindle.entidades.Escritor;

import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class EscritorDAO implements InterfaceDAO<Escritor>{

	@Override
	public void adiciona(Escritor referencia) {	
		
		new PessoaFisicaDAO().adiciona(referencia);
		
		referencia.setIdPessoa(PessoaFisicaDAO.buscaID(referencia));
		String sql = ("INSERT INTO escritor (id_pessoa) VALUES (" + referencia.getIdPessoa() + ");");
		UtilDB.alterarDB(sql);
	}

	@Override
	public void remove(Escritor referencia) {
		String sql = ("DELETE FROM escritor WHERE id_escritor = " + referencia.getIdEscritor() + ";");
		UtilDB.alterarDB(sql);
		
		new PessoaFisicaDAO().remove(referencia);
	}

	@Override
	public List<Escritor> lista() {
		
		ArrayList<Escritor> escritores = new ArrayList<Escritor>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = UtilDB.getConexao().prepareStatement("SELECT * FROM escritor_select;");
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				Escritor escritor = new Escritor();
				escritor.setIdPessoa(rs.getInt("id_pessoa"));
				escritor.setIdEscritor(rs.getInt("id_escritor"));
				escritor.setNome(rs.getString("nome"));
				escritor.setSobrenome(rs.getString("sobrenome"));
				escritor.setCPF(rs.getString("cpf"));
				escritor.setTelefone(rs.getString("telefone"));
				escritor.setEmail(rs.getString("email"));
				
				escritores.add(escritor);
			}
			
		}catch(SQLException e) {
			System.err.println("Erro ao listar escritores!");
		}
		
		return escritores;
	}
	
	public static void atualizaEscritor(Escritor escritor) {
		PessoaFisicaDAO.atualizaPessoaFisica(escritor);
		PessoaDAO.atualizaPessoa(escritor);
	}
	
	public static Escritor getEscritorPorID(int idEscritor) {
		
		Escritor escritor = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = UtilDB.getConexao().prepareStatement("SELECT * FROM escritor_select WHERE id_escritor = " + idEscritor + ";");
			rs = pstm.executeQuery();
			
			escritor = new Escritor();
			escritor.setIdPessoa(rs.getInt("id_pessoa"));
			escritor.setIdEscritor(rs.getInt("id_escritor"));
			escritor.setNome(rs.getString("nome"));
			escritor.setSobrenome(rs.getString("sobrenome"));
			escritor.setCPF(rs.getString("cpf"));
			escritor.setTelefone(rs.getString("telefone"));
			escritor.setEmail(rs.getString("email"));
			
		}catch (SQLException e) {
			System.err.println("Erro ao obter dados do escritor!");
		}
		
		return escritor;
	}
	
}





























