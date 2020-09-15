package Kindle.entidades;

public class Editora extends PessoaJuridica{

	private int idEditora;
	
	public Editora() {
	}
	
	public Editora(int idEditora, int idPessoa, String nome, String email, String telefone) {
		super();
		this.idEditora = idEditora;
	}
	
	public void setIdEditora(int idEditora) {
		this.idEditora = idEditora;
	}
	
	public int getIdEditora() {
		return idEditora;
	}
	
}
