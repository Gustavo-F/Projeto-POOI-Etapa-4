package Kindle.entidades;

public abstract class PessoaFisica extends Pessoa{

	private String cpf;
	
	public PessoaFisica() {
	}
	
	public PessoaFisica(String cpf, int idPessoa, String nome, String sobrenome, String email, String telefone) {
		super(idPessoa, nome, sobrenome, email, telefone);
		this.cpf = cpf;
	}
	
	public void setCPF(String cpf) {
		this.cpf = cpf;
	}
	
	public String getCPF() {
		return cpf;
	}
	
}
