package Kindle.db;

import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import Kindle.entidades.Usuario;

@SuppressWarnings("unused")
public class UsuarioDAO implements InterfaceDAO<Usuario>{

	@Override
	public void adiciona(Usuario referencia) {
		PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
		
		pessoaFisicaDAO.adiciona(referencia);
		referencia.setIdPessoa(PessoaFisicaDAO.buscaID(referencia));
		
		String sql = ("INSERT INTO usuario (id_pessoa, login, senha, permissao) VALUES (" + referencia.getIdPessoa() +", "
				+ "'" + referencia.getLogin() + "', '" + referencia.getSenha() + "', '" + referencia.getPermissao() + "');");
		
		UtilDB.alterarDB(sql);
	}

	@Override
	public void remove(Usuario referencia) {
		
	}

	@Override
	public List<Usuario> lista() {
		return null;
	}
	
	public Usuario buscaUsuario(String login) {
		
		Usuario usuario = null;
		Connection conexao = UtilDB.getConexao();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		String sql = ("SELECT * FROM usuario_select WHERE login = '" + login + "';");
		System.out.println(sql);
		
		try {
			pstm = conexao.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			usuario = new Usuario();
			usuario.setIdPessoa(rs.getInt("id_pessoa"));
			usuario.setNome(rs.getString("nome"));
			usuario.setSobrenome(rs.getString("sobrenome"));
			usuario.setCPF(rs.getString("cpf"));
			usuario.setTelefone(rs.getString("telefone"));
			usuario.setEmail(rs.getString("email"));
			usuario.setLogin(rs.getString("login"));
			usuario.setSenha(rs.getString("senha"));
			usuario.setPermissao(rs.getString("permissao"));
			
		}catch(SQLException e) {
			System.err.println("Erro ao buscar dados do usuário.");
			return null;
		}
		
		return usuario;
	}

}
