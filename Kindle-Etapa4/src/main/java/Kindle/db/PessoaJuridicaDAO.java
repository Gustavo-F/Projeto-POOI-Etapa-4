package Kindle.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Kindle.entidades.PessoaJuridica;

public class PessoaJuridicaDAO implements InterfaceDAO<PessoaJuridica>{

	@Override
	public void adiciona(PessoaJuridica referencia) {
		new PessoaDAO().adiciona(referencia);
		
		referencia.setIdPessoa(PessoaDAO.getIdPessoa());
		
		String sql = ("INSERT INTO pessoa_juridica (id_pessoa, cnpj) VALUES	"
				+ "(" + referencia.getIdPessoa() + ", '" + referencia.getCnpj() + "');");
		
		UtilDB.alterarDB(sql);
	}

	@Override
	public void remove(PessoaJuridica referencia) {
		String sql = ("DELETE FROM pessoa_juridica WHERE id_pessoa = " + referencia.getIdPessoa() + ";");
		UtilDB.alterarDB(sql);
		
		new PessoaDAO().remove(referencia);
	}

	@Override
	public List<PessoaJuridica> lista() {
		return null;
	}
	
	public static void atualizaPessoaJuridica(PessoaJuridica pessoa) {
		String sql = ("UPDATE pessoa_juridica SET cnpj = '" + pessoa.getCnpj() +"' WHERE id_pessoa = " + pessoa.getIdPessoa() +";");
		UtilDB.alterarDB(sql);
	}
	
	public static int buscaID(PessoaJuridica pessoa) {
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = UtilDB.getConexao().prepareStatement("SELECT id_pessoa FROM pessoa_juridica WHERE cnpj = '" + pessoa.getCnpj() + "';");
			rs = pstm.executeQuery();
			
			return rs.getInt("id_pessoa");
		}catch(SQLException e) {
			System.err.println("Erro ao obter ID de Pessoa Juridica!");
		}
		
		return 0;
	}

}
