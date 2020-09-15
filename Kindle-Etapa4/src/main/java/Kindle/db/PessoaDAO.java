package Kindle.db;

import java.util.List;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import Kindle.entidades.Pessoa;

public class PessoaDAO implements InterfaceDAO<Pessoa>{

	@Override
	public void adiciona(Pessoa referencia) {
		String sql = ("INSERT INTO pessoa (nome, sobrenome, email, telefone) VALUES"
				+ " ('" + referencia.getNome() + "', '" + referencia.getSobrenome() + "',"
				+ "'" + referencia.getEmail() + "', '" + referencia.getTelefone() + "');");
		
		UtilDB.alterarDB(sql);
	}

	@Override
	public void remove(Pessoa referencia) {
		String sql = ("DELETE FROM pessoa WHERE id_pessoa = " + referencia.getIdPessoa() + ";");
		UtilDB.alterarDB(sql);
	}

	@Override
	public List<Pessoa> lista() {
		return null;
	}
	
	public static void atualizaPessoa(Pessoa pessoa) {
		String sql;
		
		if(pessoa.getSobrenome() == null) {
			sql = ("UPDATE pessoa \n"
				 + "SET nome = '" + pessoa.getNome() + "', email = '" + pessoa.getEmail() + "', telefone = '" + pessoa.getTelefone() +"'\n"
				 + "WHERE id_pessoa = " + pessoa.getIdPessoa() + ";");
		}else {
			sql = ("UPDATE pessoa \n"
				 + "SET nome = '" + pessoa.getNome() + "', sobrenome = '" + pessoa.getSobrenome() +"', "
				 + "email = '" + pessoa.getEmail() + "', telefone = '" + pessoa.getTelefone() + "'\n"
				 + "WHERE id_pessoa = " + pessoa.getIdPessoa() +";");
		}
		
		UtilDB.alterarDB(sql);
	}
	
	public static int getIdPessoa() {
		
		Connection conexao = UtilDB.getConexao();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = conexao.prepareStatement("SELECT MAX(id_pessoa) FROM pessoa;");
			rs = pstm.executeQuery();
			
			return rs.getInt("MAX(id_pessoa)");
		}catch(SQLException e) {
			System.err.println("Erro ao obter ID.");
		}
		
		return 0;
	}
	

}
