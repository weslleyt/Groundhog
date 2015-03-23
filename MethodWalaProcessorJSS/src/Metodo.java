public class Metodo {

	private String nome;
	private int quantidade;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public void adicionarQuantidade(int valor) {
		this.quantidade = this.quantidade + valor;
	}

}
