import java.util.ArrayList;

public class Classe {

	private String nome;
	private ArrayList<Metodo> listaMetodos;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Metodo> getListaMetodos() {
		return listaMetodos;
	}

	public void setListaMetodos(ArrayList<Metodo> listaMetodos) {
		this.listaMetodos = listaMetodos;
	}

	public int somarUsosMetodos() {

		int soma = 0;

		for (Metodo metodo: listaMetodos) {
			soma = soma + metodo.getQuantidade();
		}

		return soma;
	}

}
