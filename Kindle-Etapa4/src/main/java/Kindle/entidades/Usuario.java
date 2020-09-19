package Kindle.entidades;

public class Usuario extends PessoaFisica{

	private String login;
	private String senha;
	private String permissao;
	
	public Usuario() {
	}
	
	public Usuario(String login, String senha, String permissao, String cpf, int idPessoa, String nome, String sobrenome, String email, String telefone) {
		super(cpf, idPessoa, nome, sobrenome, email, telefone);
		this.login = login;
		this.senha = senha;
		this.permissao = permissao;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPermissao() {
		return permissao;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}
	
	
	
}
