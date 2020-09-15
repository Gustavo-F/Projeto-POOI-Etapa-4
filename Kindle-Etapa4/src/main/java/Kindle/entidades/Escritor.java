package Kindle.entidades;

public class Escritor extends PessoaFisica{

	private int idEscritor;
	
	public Escritor(){
	}
	
	public Escritor(int idEscritor, String cpf, int idPessoa, String nome, String sobrenome, String email, String telefone) {
		super(cpf, idPessoa, nome, sobrenome, email, telefone);
		this.idEscritor = idEscritor;
	}
	
	public void setIdEscritor(int idEscritor) {
		this.idEscritor = idEscritor;
	}
	
	public int getIdEscritor() {
		return idEscritor;
	}
	
}
