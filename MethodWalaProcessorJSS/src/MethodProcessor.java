import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MethodProcessor {

	public static String LISTAGEM_PROJETOS = "/Users/weslleytorres/Google Drive/doutorado/ArtigoGroundHogJSS/Dados/ResultadoWala/MetodoRun/";
	public static String ARQUIVO_HIST_METODO = "/Users/weslleytorres/Google Drive/doutorado/ArtigoGroundHogJSS/Dados/ResultadoWala/Histogramas/HistogramaMetodo.csv";
	public static String ARQUIVO_HIST_CLASSE = "/Users/weslleytorres/Google Drive/doutorado/ArtigoGroundHogJSS/Dados/ResultadoWala/Histogramas/HistogramaClasse.csv";

	public static void main(String[] args) {

		ArrayList<File> listaProjetos = null;

		ArrayList<Pacote> listaPacotes = new ArrayList<Pacote>();

		try {
			listaProjetos = listarProjetos(LISTAGEM_PROJETOS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		processarPacote(listaPacotes, listaProjetos);

		gerarArquivoHistogramaMetodoCsv(ARQUIVO_HIST_METODO, listaPacotes);
		gerarArquivoHistogramaClasseCsv(ARQUIVO_HIST_CLASSE, listaPacotes);

	}

	public static void processarPacote(ArrayList<Pacote> listaPacotes, ArrayList<File> listaProjetos) {

		boolean contemPacote = false;
		boolean contemMetodo = false;
		boolean contemClasse = false;
		boolean adicionou = false;

		for (File projeto: listaProjetos) {
			BufferedReader in;
			try {
				in = new BufferedReader(new FileReader(projeto));

				String str;

				while ((str = in.readLine()) != null) {
					String pedacoLinha[] = str.split(",");

					if (!pedacoLinha[0].equals("Pacote")) {

						Pacote pacoteAuxiliar = carregarPacoteAuxiliar(pedacoLinha);
						contemPacote = false;
						contemMetodo = false;
						contemClasse = false;
						adicionou = false;

						if (listaPacotes.isEmpty()) {
							listaPacotes.add(pacoteAuxiliar);
						} else {

							for (Pacote pacote: listaPacotes) {
								if (pacote.getNome().equals(pacoteAuxiliar.getNome()) && !adicionou) {

									contemPacote = true;

									for (Classe classe: pacote.getListaClasses()) {
										if (classe.getNome().equals(pacoteAuxiliar.getListaClasses().get(0).getNome()) && !adicionou) {

											contemClasse = true;

											for (Metodo metodo: classe.getListaMetodos()) {
												if (metodo.getNome().equals(pacoteAuxiliar.getListaClasses().get(0).getListaMetodos().get(0).getNome())) {
													metodo.adicionarQuantidade(pacoteAuxiliar.getListaClasses().get(0).getListaMetodos().get(0)
															.getQuantidade());
													contemMetodo = true;
												}
											}
											if (!contemMetodo) {
												classe.getListaMetodos().add(pacoteAuxiliar.getListaClasses().get(0).getListaMetodos().get(0));
												adicionou = true;
												break;
											}
										}
									}
									if (!contemClasse) {
										pacote.getListaClasses().add(pacoteAuxiliar.getListaClasses().get(0));
										adicionou = true;
										break;
									}
								}
							}
							if (!contemPacote) {
								listaPacotes.add(pacoteAuxiliar);
							}
						}

					}

				}

				in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void gerarArquivoHistogramaMetodoCsv(String destino, ArrayList<Pacote> listaPacotes) {
		try {
			FileWriter writer = new FileWriter(destino);

			writer.append("Metodo");
			writer.append(',');
			writer.append("Ocorrencias");
			writer.append('\n');

			for (Pacote pacote: listaPacotes) {

				for (Classe classe: pacote.getListaClasses()) {

					for (Metodo metodo: classe.getListaMetodos()) {
						writer.append(pacote.getNome() + classe.getNome() + " " + metodo.getNome());
						writer.append(',');
						writer.append(Integer.toString(metodo.getQuantidade()));
						writer.append('\n');
					}

				}
			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void gerarArquivoHistogramaClasseCsv(String destino, ArrayList<Pacote> listaPacotes) {
		try {
			FileWriter writer = new FileWriter(destino);

			writer.append("Classe");
			writer.append(',');
			writer.append("Ocorrencias");
			writer.append('\n');

			for (Pacote pacote: listaPacotes) {

				for (Classe classe: pacote.getListaClasses()) {

					writer.append(pacote.getNome() + classe.getNome());
					writer.append(',');
					writer.append(Integer.toString(classe.somarUsosMetodos()));
					writer.append('\n');

				}
			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Pacote carregarPacoteAuxiliar(String pedacoLinha[]) {
		Pacote pacote = new Pacote();
		Classe classe = new Classe();
		Metodo metodo = new Metodo();

		ArrayList<Classe> listaClasses = new ArrayList<Classe>();
		ArrayList<Metodo> listaMetodos = new ArrayList<Metodo>();

		String pedacoPacoteClasse[] = pedacoLinha[0].split("\\/");
		String nomePacote = "";
		String nomeClasse = "";
		String nomeMetodo = "";
		int ocorrencias;

		for (int i = 0; i < (pedacoPacoteClasse.length - 1); i++) {
			nomePacote = nomePacote + pedacoPacoteClasse[i] + "/";
		}

		nomeClasse = pedacoPacoteClasse[pedacoPacoteClasse.length - 1];
		nomeMetodo = pedacoLinha[1];
		ocorrencias = Integer.valueOf(pedacoLinha[2]);

		metodo.setNome(nomeMetodo);
		metodo.setQuantidade(ocorrencias);

		classe.setNome(nomeClasse);
		pacote.setNome(nomePacote);

		listaMetodos.add(metodo);
		listaClasses.add(classe);

		classe.setListaMetodos(listaMetodos);
		pacote.setListaClasses(listaClasses);

		return pacote;
	}

	public static ArrayList<File> listarProjetos(String urlDiretorio) throws IOException {

		File diretorio = new File(urlDiretorio);
		File projetos[] = diretorio.listFiles();

		ArrayList<File> listaProjetos = new ArrayList<File>(Arrays.asList(projetos));

		removerArquivos(listaProjetos);

		return listaProjetos;
	}

	public static void removerArquivos(ArrayList<File> listaProjetos) {

		for (int i = 0; i < listaProjetos.size(); i++) {
			String nome = listaProjetos.get(i).getName();
			if (!nome.endsWith(".csv")) {
				listaProjetos.remove(i);
			}
		}

	}

}
