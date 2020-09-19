package Kindle.entidades;

public abstract class PessoaJuridica extends Pessoa{

	private String cnpj;
	
	public PessoaJuridica() {
	}
	
	public PessoaJuridica(String cnpj, int idPessoa, String nome, String email, String telefone) {
		super(idPessoa, nome, email, telefone);
		this.cnpj = cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	
}
