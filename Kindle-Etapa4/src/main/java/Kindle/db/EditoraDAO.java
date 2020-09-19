package Kindle.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Kindle.entidades.Editora;

public class EditoraDAO implements InterfaceDAO<Editora>{

	@Override
	public void adiciona(Editora referencia) {
		new PessoaJuridicaDAO().adiciona(referencia);
		
		referencia.setIdPessoa(PessoaJuridicaDAO.buscaID(referencia));
		
		String sql = ("INSERT INTO editora (id_pessoa) VALUES (" + referencia.getIdPessoa()+ ");");
		System.out.println(sql);
		UtilDB.alterarDB(sql);
	}

	@Override
	public void remove(Editora referencia) {
		String sql = ("DELETE FROM editora WHERE id_editora = " + referencia.getIdEditora() +";");
		UtilDB.alterarDB(sql);
		
		new PessoaJuridicaDAO().remove(referencia);
	}

	@Override
	public List<Editora> lista() {
		
		List<Editora> editoras = new ArrayList<Editora>();
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			pstm = UtilDB.getConexao().prepareStatement("SELECT * FROM editora_select;");
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				Editora editora = new Editora();
				editora.setIdPessoa(rs.getInt("id_pessoa"));
				editora.setIdEditora(rs.getInt("id_editora"));
				editora.setNome(rs.getString("nome"));
				editora.setCnpj(rs.getString("cnpj"));
				editora.setEmail(rs.getString("email"));
				editora.setTelefone(rs.getString("telefone"));
				
				editoras.add(editora);
			}
			
		}catch(SQLException e) {
			System.err.println("Erro ao listar editoras!");
		}
		
		return editoras;
	}

	public void atualizaEditora(Editora editora) {
		PessoaDAO.atualizaPessoa(editora);
		PessoaJuridicaDAO.atualizaPessoaJuridica(editora);
	}
	
	public static Editora buscaEditoraPorNome(String nome) {
		Editora editora = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = UtilDB.getConexao().prepareStatement("SELECT * FROM editora_select WHERE nome = '" + nome + "';");
			rs = pstm.executeQuery();
			
			editora = new Editora();
			editora.setIdPessoa(rs.getInt("id_pessoa"));
			editora.setIdEditora(rs.getInt("id_editora"));
			editora.setNome(rs.getString("nome"));
			editora.setCnpj(rs.getString("cnpj"));
			editora.setEmail(rs.getString("email"));
			editora.setTelefone(rs.getString("telefone"));
			
		}catch(SQLException e) {
			System.err.println("Erro ao buscar editora!");
		}
		
		return editora;
	}
	
	public static Editora getEditora(int idEditora) {
		
		Editora editora = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = UtilDB.getConexao().prepareStatement("SELECT * FROM editora_select WHERE id_editora = " + idEditora +";");
			rs = pstm.executeQuery();
			
			editora = new Editora();
			editora.setIdPessoa(rs.getInt("id_pessoa"));
			editora.setIdEditora(rs.getInt("id_editora"));
			editora.setNome(rs.getString("nome"));
			editora.setCnpj(rs.getString("cnpj"));
			editora.setEmail(rs.getString("email"));
			editora.setTelefone(rs.getString("telefone"));
			
		}catch(SQLException e) {
			System.err.println("Erro ao buscar dados da editora!");
		}
		
		return editora;
	}
	
}






























