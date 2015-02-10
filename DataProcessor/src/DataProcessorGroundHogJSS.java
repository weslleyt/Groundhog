import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataProcessorGroundHogJSS {

	public static ArrayList<ArrayList<String>> projectsByCategory = new ArrayList<ArrayList<String>>();
	public static ArrayList<String> categoriesName = new ArrayList<String>();
	static File fileSourceFolder;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		String rootSourceFolder = "/Users/weslleytorres/Google Drive/doutorado/ArtigoGroundHogJSS/Dados/LogProjetos2015/";

		// String projectsByCategoriesFolder = "/Users/weslleytorres/Google Drive/doutorado/ArtigoGroundHogJSS/Dados/projetcsByCategory";

		String metricsNameSourceFolder = "/Users/weslleytorres/Google Drive/doutorado/ArtigoGroundHogJSS/Dados/metricsNames.txt";
		String rootDestinyFolder = "/Users/weslleytorres/Google Drive/doutorado/ArtigoGroundHogJSS/Dados/Analises";

		fileSourceFolder = new File(rootSourceFolder);
		File metricsNameFile = new File(metricsNameSourceFolder);
		// fillProjectsCategory(projectsByCategoriesFolder);

		MetricProcessFileManager.destinyFolder = rootDestinyFolder;

		// verifica qual categoria faz parte
		// compareProjectsCategory(projectsByCategoriesFolder, rootSourceFolder);

		// JUCContructsMetricProcessor juc = new JUCContructsMetricProcessor(
		// "j.u.c", true, true,true);
		// juc.process(fileSourceFolder);

		ArrayList<String> metricsNames = getMetricNames(metricsNameFile);

		// Possui a primeira metrica mas n possui a segunda
		DateDoubleMetricProcessor possuiPrimeiraMetrica = new DateDoubleMetricProcessor(true, "Hashtable", "j.u.c.WITHOUT.imports", false, true);
		possuiPrimeiraMetrica.process(fileSourceFolder);

		// LOC Por ano escolhido
		// LoCMetricProcessor loc = new LoCMetricProcessor(true, "2005");
		// loc.process(fileSourceFolder);

		// Pegar primeira Versão que utiliza JUC - sem import
		// getProjectsFirstVersionMetric("j.u.c.WITHOUT.imports", true, true, "2012", true);

		// Pegar projetos lançados em Tal ano e a ultima versao e analizada
		// generateSingleMetrics(metricsNames, true, true, true, "2005", true);
		// generateSingleMetrics(metricsNames, false, true, true, "2005", true);

		// Pegar a evolucao2
		// generateSingleMetricsEvolution(metricsNames, true,true,true,true, "2012",true);
		// generateSingleMetricsEvolution(metricsNames, false,true,true, true, "2012", true);

		// para coletar dados de 1 só projeto
		// generateSingleMetrics(metricsNames, true,true,true,"2005",false, true);
		// generateSingleMetrics(metricsNames, false,true,true,"2005",false, true);

		// ArrayList<String> teste = new ArrayList<String>();
		// teste.add("Lines of Code");
		// generateSingleMetrics(teste, false,true,true, false);

		// Para gerar as metricas gerais
		// generateSingleMetrics(metricsNames, false,true,true,false);
		// generateSingleMetrics(metricsNames, true,true,true,false);

		// soma LOC normal
		// LoCMetricProcessor loc = new LoCMetricProcessor(true);
		// loc.process(fileSourceFolder);
		// LoCMetricProcessor loc2 = new LoCMetricProcessor(false);
		// loc2.process(fileSourceFolder);

	}

	private static void generateSingleMetrics(ArrayList<String> metricsNames, boolean hasThreshold, boolean printProjectName, boolean hasMetric)
			throws IOException {

		for (int i = 0; i < metricsNames.size(); i++) {
			SingleMetricProcessor smp = new SingleMetricProcessor(metricsNames.get(i), hasThreshold, printProjectName, hasMetric);
			smp.process(fileSourceFolder);
		}
	}

	private static void generateSingleMetrics(ArrayList<String> metricsNames, boolean hasThreshold, boolean printProjectName, boolean hasMetric,
			boolean proporcionalLoc) throws IOException {

		for (int i = 0; i < metricsNames.size(); i++) {
			SingleMetricProcessor smp = new SingleMetricProcessor(metricsNames.get(i), hasThreshold, printProjectName, hasMetric, proporcionalLoc);
			smp.process(fileSourceFolder);
		}
	}

	private static void generateSingleMetrics(ArrayList<String> metricsNames, boolean hasThreshold, boolean printProjectName, boolean hasMetric,
			String year) throws IOException {

		for (int i = 0; i < metricsNames.size(); i++) {
			SingleMetricProcessor smp = new SingleMetricProcessor(metricsNames.get(i), hasThreshold, printProjectName, hasMetric, year);
			smp.process(fileSourceFolder);
		}
	}

	private static void generateSingleMetrics(ArrayList<String> metricsNames, boolean hasThreshold, boolean printProjectName, boolean hasMetric,
			String year, Boolean proporcionalLoc) throws IOException {

		for (int i = 0; i < metricsNames.size(); i++) {
			SingleMetricProcessor smp = new SingleMetricProcessor(metricsNames.get(i), hasThreshold, printProjectName, hasMetric, year, proporcionalLoc);
			smp.process(fileSourceFolder);
		}
	}

	private static void getProjectsFirstVersionMetric(String metricName, boolean concurrentProjects, boolean printProjectName, String releasedDate,
			boolean proporcionalLoc) throws IOException {

		SingleMetricProcessor smp = new SingleMetricProcessor(metricName, concurrentProjects, printProjectName, releasedDate, proporcionalLoc);
		smp.process(fileSourceFolder);

	}

	private static void generateSingleMetricsEvolution(ArrayList<String> metricsNames, boolean hasThreshold, boolean printProjectName, boolean hasMetric,
			boolean evolution, String year, Boolean proporcionalLoc) throws IOException {

		for (int i = 0; i < metricsNames.size(); i++) {
			SingleMetricProcessor smp = new SingleMetricProcessor(metricsNames.get(i), hasThreshold, printProjectName, hasMetric, evolution, year,
					proporcionalLoc);
			smp.process(fileSourceFolder);
		}
	}

	/**
	 * Metodo utilizado para coletar todas as mÃ©tricas de um sÃ³ projeto mas lembre-se de passar o caminho apenas do projeto.
	 */
	private static void generateSingleMetrics(ArrayList<String> metricsNames, boolean hasThreshold, boolean printProjectName, boolean hasMetric,
			String year, Boolean proporcionalLoc, Boolean onlyOneProject) throws IOException {

		for (int i = 0; i < metricsNames.size(); i++) {
			SingleMetricProcessor smp = new SingleMetricProcessor(metricsNames.get(i), hasThreshold, printProjectName, hasMetric, year, proporcionalLoc,
					onlyOneProject);
			smp.process(fileSourceFolder);
		}
	}

	private static void fillProjectsCategory(String sourceFolder) throws IOException {
		File fileFolder = new File(sourceFolder);
		File[] listFiles = fileFolder.listFiles();
		String extention = ".txt";

		for (int i = 0; i < listFiles.length; i++) {
			ArrayList<String> projects = new ArrayList<String>();
			categoriesName.add(listFiles[i].getName().substring(0, listFiles[i].getName().length() - extention.length()));
			BufferedReader in = new BufferedReader(new FileReader(listFiles[i]));
			String str;
			while ((str = in.readLine()) != null) {
				projects.add(str);
			}
			projectsByCategory.add(projects);
		}
	}

	private static void compareProjectsCategory(String projectsCategoriesFolder, String projectsCSVfile) throws IOException {
		System.out.println("Comecou:");
		File fileFolder = new File(projectsCategoriesFolder);
		File[] listFiles = fileFolder.listFiles();

		File projectsCSVfileFolder = new File(projectsCSVfile);
		File[] listFilesCSVprojects = projectsCSVfileFolder.listFiles();

		for (int i = 0; i < listFiles.length; i++) {
			ArrayList<String> projects = new ArrayList<String>();
			BufferedReader in = new BufferedReader(new FileReader(listFiles[i]));
			String str;
			int t = 0;
			int contadorProjetos = 0, contador = 0;
			while ((str = in.readLine()) != null) {
				BufferedReader inProj = new BufferedReader(new FileReader(listFilesCSVprojects[0]));
				String proj = "";
				int s = 0;
				while ((proj = inProj.readLine()) != null) {
					String[] splitProjectsName = proj.split(",");
					if (splitProjectsName[0].startsWith(str)) {
						contadorProjetos++;
					}
				}
				// t++;
				// System.out.println("valor S: " + s);
				inProj.close();
				// System.out.println("era para ser o tamanho do arquivo csv: " + s);
				// contador++;

			}
			System.out.println(listFiles[i].getName() + " : " + contadorProjetos);
			// System.out.println("valor de t: " + t);
			in.close();

		}

		System.out.println("Terminou");
	}

	private static ArrayList<String> getMetricNames(File file) throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(file));
		String str;
		ArrayList<String> names = new ArrayList<String>();
		while ((str = in.readLine()) != null) {
			String[] nameS = str.split(":");
			names.add(nameS[0].trim());
		}
		return names;
	}

}
