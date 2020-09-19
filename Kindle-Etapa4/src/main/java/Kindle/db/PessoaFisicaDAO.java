package Kindle.db;

import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import Kindle.entidades.PessoaFisica;

@SuppressWarnings("unused")
public class PessoaFisicaDAO implements InterfaceDAO<PessoaFisica>{

	@Override
	public void adiciona(PessoaFisica referencia) {
		
		PessoaDAO pessoaDAO = new PessoaDAO();
		pessoaDAO.adiciona(referencia);
		
		int id = PessoaDAO.getIdPessoa();
		referencia.setIdPessoa(id);
		
		String sql = ("INSERT INTO pessoa_fisica (id_pessoa, cpf) VALUES (" + referencia.getIdPessoa() + ", '" + referencia.getCPF() + "');");
		
		UtilDB.alterarDB(sql);
	}

	@Override
	public void remove(PessoaFisica referencia) {
		String sql = ("DELETE FROM pessoa_fisica WHERE id_pessoa = " + referencia.getIdPessoa() + ";");
		UtilDB.alterarDB(sql);
		
		new PessoaDAO().remove(referencia);
	}

	@Override
	public List<PessoaFisica> lista() {
		return null;
	}

	public static int buscaID(PessoaFisica pessoa) {
		
		Connection conexao = UtilDB.getConexao();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = conexao.prepareStatement("SELECT id_pessoa FROM pessoa_fisica WHERE cpf = '" + pessoa.getCPF() + "';");
			rs = pstm.executeQuery();
			
			return rs.getInt("id_pessoa");
		}catch(SQLException e) {
			System.err.println("Erro ao obter ID.");
		}
		
		return 0;
	}
	
	public static void atualizaPessoaFisica(PessoaFisica pessoa) {
		String sql = ("UPDATE pessoa_fisica\n"
					+ "SET cpf = '" + pessoa.getCPF() + "' WHERE id_pessoa = " + pessoa.getIdPessoa() + ";");
		UtilDB.alterarDB(sql);
	}
	
}





















