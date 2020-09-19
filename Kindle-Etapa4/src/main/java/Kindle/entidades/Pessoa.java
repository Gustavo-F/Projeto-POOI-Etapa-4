package Kindle.entidades;

public abstract class Pessoa {

	private int idPessoa;
	private String nome;
	private String sobrenome;
	private String email;
	private String telefone;
	
	public Pessoa() {
	}
	
	public Pessoa(int idPessoa, String nome, String email, String telefone) {
		this.idPessoa = idPessoa;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
	}
	
	public Pessoa(int idPessoa, String nome, String sobrenome, String email, String telefone) {
		this.idPessoa = idPessoa;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.email = email;
		this.telefone = telefone;
	}
	
	public void setIdPessoa(int idPessoa) {
		this.idPessoa = idPessoa;
	}
	
	public int getIdPessoa() {
		return idPessoa;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	public String getSobrenome() {
		return sobrenome;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
}
