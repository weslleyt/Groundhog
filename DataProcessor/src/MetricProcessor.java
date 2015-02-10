import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class MetricProcessor {

	private ArrayList<ArrayList<File>> projectVersions = new ArrayList<ArrayList<File>>();

	public boolean hasThreshold = false;
	public boolean printProjectName = false;
	public boolean toSort = true;

	public void recursiveSearch(File file) throws IOException {
		File subFiles[] = file.listFiles();

		if (subFiles != null) {
			for (int i = 0; i < subFiles.length; i++) {
				if (subFiles[i].isDirectory()) {
					recursiveSearch(subFiles[i]);
				} else if (subFiles[i].isFile() && !subFiles[i].getName().contains("lv-.txt")) {
					if (i == 0) {
						ArrayList<File> files = new ArrayList<File>();
						files.add(subFiles[i]);
						projectVersions.add(files);
					} else {
						projectVersions.get(projectVersions.size() - 1).add(subFiles[i]);
					}
				}
			}
		}
	}

	public abstract void readMetrics() throws IOException;

	public abstract void writeMetrics() throws IOException;

	public void process(File file) throws IOException {
		recursiveSearch(file);
		if (hasThreshold) {
			excludeProjectOutOfthreshold();
		}
		if (toSort) {
			sortProjectsVersion();
		}
		readMetrics();
		writeMetrics();
	}

	public ArrayList<ArrayList<File>> getProjectVersions() {
		return projectVersions;
	}

	public void sortProjectsVersion() {
		for (int i = 0; i < projectVersions.size(); i++) {
			// Collections.sort(projectVersions.get(i), new VersionComparator());
			Collections.sort(projectVersions.get(i));
		}
	}

	protected boolean firstVersionYear(int yearVersion, File projectVersion, String metricName) throws IOException {

		String name = projectVersion.getName();
		if (name.startsWith("[")) {
			int year = Integer.parseInt(name.substring(1, 5));
			if (year == yearVersion) {
				BufferedReader in = new BufferedReader(new FileReader(projectVersion));
				String str;
				while ((str = in.readLine()) != null) {
					String[] splitMetrics = str.split(":");
					if (splitMetrics[0].trim().startsWith(metricName)) {
						if (Integer.parseInt(splitMetrics[1].trim()) > 0) { return true; }
					}

				}
				in.close();
			}

		}
		return false;
	}

	protected boolean checkMetric(ArrayList<File> projectVersions, String metricName) throws IOException {
		int juc = -10;
		int lock = -10;
		for (File file: projectVersions) {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			while ((str = in.readLine()) != null) {
				String[] splitMetrics = str.split(":");
				if (splitMetrics[0].trim().startsWith(metricName)) {
					// if( Integer.parseInt(splitMetrics[1].trim()) > 0)
					// return true;
					juc = Integer.parseInt(splitMetrics[1].trim());
				}
				if (splitMetrics[0].trim().startsWith("Juc.Locks")) {
					// if( Integer.parseInt(splitMetrics[1].trim()) > 0)
					// return true;
					lock = Integer.parseInt(splitMetrics[1].trim());
				}
			}
			if (juc > lock) { return true; }
		}
		return false;
	}

	protected boolean firstVersionYear(int yearVersion, File projectVersion) throws IOException {

		String name = projectVersion.getName();
		if (name.startsWith("[")) {
			int year = Integer.parseInt(name.substring(1, 5));
			if (year == yearVersion) { return true; }
		}
		return false;
	}

	/**
	 * projeto lançado na da data informada
	 */
	protected boolean VersionYear(int yearVersion, File projectVersion) throws IOException {

		String name = projectVersion.getName();
		if (name.startsWith("[")) {
			int year = Integer.parseInt(name.substring(1, 5));
			if (year == yearVersion) { return true; }
		}
		return false;
	}

	/**
	 * projeto lançado depois da data informada
	 */
	protected boolean VersionYearAfter(int yearVersion, File projectVersion) throws IOException {

		String name = projectVersion.getName();
		if (name.startsWith("[")) {
			int year = Integer.parseInt(name.substring(1, 5));
			if (year > yearVersion) { return true; }
		}
		return false;
	}

	/**
	 * projeto lançado antes da data informada
	 */

	protected boolean VersionYearBefore(int yearVersion, File projectVersion) throws IOException {

		String name = projectVersion.getName();
		if (name.startsWith("[")) {
			int year = Integer.parseInt(name.substring(1, 5));
			if (year < yearVersion) { return true; }
		}
		return false;
	}

	/**
	 * projeto lançado entre periodo dado
	 */
	protected boolean VersionYearBetween(int firstYearVersion, int lastYearVersion, File projectVersion) throws IOException {

		String name = projectVersion.getName();
		if (name.startsWith("[")) {
			int year = Integer.parseInt(name.substring(1, 5));
			if (year >= firstYearVersion && year <= lastYearVersion) { return true; }
		}
		return false;
	}

	protected boolean inLimit(int threshold, ArrayList<File> projectVersions, String metricName) throws IOException {
		boolean size = false;
		boolean limite = false;
		for (File file: projectVersions) {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			while ((str = in.readLine()) != null) {

				String[] splitMetrics = str.split(":");

				if (splitMetrics[0].trim().startsWith(metricName)) {
					// Benito tinha colocado > que, mas eu acho que o correto seria maior ou igual, no caso
					// maior ou igual a 1... se não projetos que só tem 1 sync vão ficar de fora
					if (Integer.parseInt(splitMetrics[1].trim()) >= threshold) {
						limite = true;
					}

				}
				if (splitMetrics[0].trim().startsWith("Lines of Code")) {
					if (Integer.parseInt(splitMetrics[1].trim()) >= 1000) {
						size = true;
					}

				}
			}
			in.close();
		}
		if (size == true && limite == true) { return true; }

		return false;

	}

	protected boolean inLimit(int threshold, int sizeProject, ArrayList<File> projectVersions, String metricName) throws IOException {
		boolean size = false;
		boolean limite = false;
		for (File file: projectVersions) {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			while ((str = in.readLine()) != null) {

				String[] splitMetrics = str.split(":");

				if (splitMetrics[0].trim().startsWith(metricName)) {
					// Benito tinha colocado > que, mas eu acho que o correto seria maior ou igual, no caso
					// maior ou igual a 1... se não projetos que só tem 1 sync vão ficar de fora
					if (Integer.parseInt(splitMetrics[1].trim()) >= threshold) {
						limite = true;
					}

				}
				if (splitMetrics[0].trim().startsWith("Lines of Code")) {
					if (Integer.parseInt(splitMetrics[1].trim()) >= sizeProject) {
						size = true;
					}

				}
			}
		}
		if (size == true && limite == true) { return true; }

		return false;
	}

	protected boolean inLimitCorrelation(int threshold, ArrayList<File> projectVersions, String metricName) throws IOException {

		boolean group1 = false, group2 = false, group3 = false;

		boolean size = false;
		boolean metric1 = false;
		boolean metric2 = false;

		for (File file: projectVersions) {

			if (this.VersionYearBefore(2006, file)) {
				group1 = true;
			}
			if (this.VersionYearBetween(2007, 2008, file)) {
				group2 = true;
			}
			if (this.VersionYearAfter(2008, file)) {
				group3 = true;
			}

		}

		if (group1 && group2 && group3) {
			/*
			 * for (File file : projectVersions) { BufferedReader in = new BufferedReader(new FileReader(file)); String str; while ((str =
			 * in.readLine()) != null) { String[] splitMetrics = str.split(":"); if(splitMetrics[0].trim().startsWith(metricName)){ if(
			 * Integer.parseInt(splitMetrics[1].trim()) > 1){ metric1 = true; } } else
			 * if(splitMetrics[0].trim().startsWith("Lines of Code")){ if( Integer.parseInt(splitMetrics[1].trim()) >= 1000){ size = true; }
			 * } } } } if (size == true && metric1 == true)
			 */
			return true;
		}

		return false;
	}

	protected boolean isPossuiPrimeiraMetricaENaoPossuiSegunda(int threshold, int sizeProject, File projectVersion, String metric1, String metric2)
			throws IOException {

		boolean size = false;
		boolean possuiPrimeiraMetrica = false;
		boolean possuiSegundaMetrica = false;

		BufferedReader in = new BufferedReader(new FileReader(projectVersion));
		String str;
		while ((str = in.readLine()) != null) {

			String[] splitMetrics = str.split(":");

			if (splitMetrics[0].trim().startsWith(metric1)) {
				if (Integer.parseInt(splitMetrics[1].trim()) >= threshold) {
					possuiPrimeiraMetrica = true;
				}

			} else if (splitMetrics[0].trim().startsWith(metric2)) {
				if (Integer.parseInt(splitMetrics[1].trim()) >= threshold) {
					possuiSegundaMetrica = true;
				}

			} else if (splitMetrics[0].trim().startsWith("Lines of Code")) {
				if (Integer.parseInt(splitMetrics[1].trim()) >= sizeProject) {
					size = true;
				}

			}
		}
		in.close();
		if (size == true && possuiPrimeiraMetrica == true && !possuiSegundaMetrica) { return true; }

		return false;
	}

	protected boolean isValidProject(int threshold, File projectVersions, String metric1, String metric2) throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(projectVersions));
		String str;
		boolean result1 = false;
		boolean result2 = false;
		boolean result3 = false;

		while ((str = in.readLine()) != null) {
			String[] splitMetrics = str.split(":");
			if (splitMetrics[0].trim().startsWith(metric1)) {
				if (Integer.parseInt(splitMetrics[1].trim()) > 0) {
					result1 = true;
				}
			}
			if (splitMetrics[0].trim().startsWith(metric2)) {
				if (Integer.parseInt(splitMetrics[1].trim()) > 0) {
					result2 = true;
				}
			}
			if (splitMetrics[0].trim().startsWith("Lines of Code")) {
				if (Integer.parseInt(splitMetrics[1].trim()) >= threshold) {
					result3 = true;
				}
			}
		}

		if ((result1 == true || result2 == true) && result3 == true) { return true; }
		return false;
	}

	// exclusivo para uso de thread e sync
	protected boolean isValidUniqueProject(int threshold, File projectVersions, String metric1, String metric2) throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(projectVersions));
		String str;
		boolean result1 = false;
		boolean result2 = false;
		boolean result3 = false;
		boolean result4 = false;
		boolean result5 = false;
		boolean result6 = false;
		boolean result7 = false;

		while ((str = in.readLine()) != null) {
			String[] splitMetrics = str.split(":");
			if (splitMetrics[0].trim().startsWith(metric1)) {
				if (Integer.parseInt(splitMetrics[1].trim()) > 0) {
					result1 = true;
				}
			}
			if (splitMetrics[0].trim().startsWith("implements Runnable")) {
				if (Integer.parseInt(splitMetrics[1].trim()) > 0) {
					result4 = true;
				}
			}
			if (splitMetrics[0].trim().startsWith("extends Runnable")) {
				if (Integer.parseInt(splitMetrics[1].trim()) > 0) {
					result5 = true;
				}
			}
			if (splitMetrics[0].trim().startsWith(metric2)) {
				if (Integer.parseInt(splitMetrics[1].trim()) == 0) {
					result2 = true;
				}
			}
			if (splitMetrics[0].trim().startsWith("Thread methods")) {
				if (Integer.parseInt(splitMetrics[1].trim()) > 0) {
					result6 = true;
				}
			}
			if (splitMetrics[0].trim().startsWith("New Thread")) {
				if (Integer.parseInt(splitMetrics[1].trim()) > 0) {
					result7 = true;
				}
			}
			if (splitMetrics[0].trim().startsWith("Lines of Code")) {
				if (Integer.parseInt(splitMetrics[1].trim()) > threshold) {
					result3 = true;
				}
			}
		}
		// if para o caso de querer thread e n quer sync
		if ((result3 == true && result2 == true && result1 == true) && !(result4 == true || result5 == true || result6 == true || result7 == true)) { return true; }
		// if (result3 == true && result2 == true && result1 == true)
		// return true;
		return false;
	}

	protected boolean inLimitLOC(int threshold, ArrayList<File> projectVersions) throws IOException {

		for (File file: projectVersions) {
			if (inLimitLOC(1000, file)) { return true; }
		}

		return false;

	}

	protected boolean inLimitLOC(int threshold, File projectVersions) throws IOException {

		// for (File file : projectVersions) {
		BufferedReader in = new BufferedReader(new FileReader(projectVersions));
		String str;

		// boolean t1=false, t2=false, t3=false, t4=false, t5=false;

		while ((str = in.readLine()) != null) {

			String[] splitMetrics = str.split(":");

			if (splitMetrics[0].trim().startsWith("Lines of Code")) {
				// Comentei a linha abaixo para poder limitar os projetos com mais de 20KLOC ou outros
				if (Integer.parseInt(splitMetrics[1].trim()) > threshold) {
					// Comentei a linha a baixo para não esquecer o intervalo analisado
					// if( Integer.parseInt(splitMetrics[1].trim()) >= 1000 && Integer.parseInt(splitMetrics[1].trim()) < 20000 ){
					// if( Integer.parseInt(splitMetrics[1].trim()) >= 20000 && Integer.parseInt(splitMetrics[1].trim()) <=100000 ){
					// if( Integer.parseInt(splitMetrics[1].trim()) > 100000 ){

					in.close();
					return true;
					// }
				}
			}

			// IMPLEMENTAR UM SWITCH AQUI PARA REALIZAR A ESCOLHA DO IF, mais r�pido

			// //para fazer que tem sync e n tem outras coisas
			/*
			 * if(splitMetrics[0].trim().startsWith("extends Thread")){ //Comentei a linha abaixo para poder limitar os projetos com mais de
			 * 20KLOC ou outros //if( Integer.parseInt(splitMetrics[1].trim()) > threshold) //Comentei a linha a baixo para não esquecer o
			 * intervalo analisado //if( Integer.parseInt(splitMetrics[1].trim()) >= 1000 && Integer.parseInt(splitMetrics[1].trim()) <
			 * 20000 ) //if( Integer.parseInt(splitMetrics[1].trim()) >= 20000 && Integer.parseInt(splitMetrics[1].trim()) <=100000 ) if(
			 * Integer.parseInt(splitMetrics[1].trim()) == 0) t1=true; //return true; }
			 */
			/*
			 * else if(splitMetrics[0].trim().startsWith("implements Runnable")){ //Comentei a linha abaixo para poder limitar os projetos
			 * com mais de 20KLOC ou outros //if( Integer.parseInt(splitMetrics[1].trim()) > threshold) //Comentei a linha a baixo para não
			 * esquecer o intervalo analisado //if( Integer.parseInt(splitMetrics[1].trim()) >= 1000 &&
			 * Integer.parseInt(splitMetrics[1].trim()) < 20000 ) //if( Integer.parseInt(splitMetrics[1].trim()) >= 20000 &&
			 * Integer.parseInt(splitMetrics[1].trim()) <=100000 ) if( Integer.parseInt(splitMetrics[1].trim()) == 0 ) t2=true; //return
			 * true; } else if(splitMetrics[0].trim().startsWith("extends Runnable")){ //Comentei a linha abaixo para poder limitar os
			 * projetos com mais de 20KLOC ou outros //if( Integer.parseInt(splitMetrics[1].trim()) > threshold) //Comentei a linha a baixo
			 * para não esquecer o intervalo analisado //if( Integer.parseInt(splitMetrics[1].trim()) >= 1000 &&
			 * Integer.parseInt(splitMetrics[1].trim()) < 20000 ) //if( Integer.parseInt(splitMetrics[1].trim()) >= 20000 &&
			 * Integer.parseInt(splitMetrics[1].trim()) <=100000 ) if( Integer.parseInt(splitMetrics[1].trim()) == 0 ) t3=true; //return
			 * true; } else if(splitMetrics[0].trim().startsWith("Synchronized keyword")){ //Comentei a linha abaixo para poder limitar os
			 * projetos com mais de 20KLOC ou outros //if( Integer.parseInt(splitMetrics[1].trim()) > threshold) //Comentei a linha a baixo
			 * para não esquecer o intervalo analisado //if( Integer.parseInt(splitMetrics[1].trim()) >= 1000 &&
			 * Integer.parseInt(splitMetrics[1].trim()) < 20000 ) //if( Integer.parseInt(splitMetrics[1].trim()) >= 20000 &&
			 * Integer.parseInt(splitMetrics[1].trim()) <=100000 ) if( Integer.parseInt(splitMetrics[1].trim()) > 0 ) t4=true; //return
			 * true; } else if(splitMetrics[0].trim().startsWith("Lines of Code")){ //Comentei a linha abaixo para poder limitar os projetos
			 * com mais de 20KLOC ou outros //if( Integer.parseInt(splitMetrics[1].trim()) > threshold) //Comentei a linha a baixo para não
			 * esquecer o intervalo analisado //if( Integer.parseInt(splitMetrics[1].trim()) >= 1000 &&
			 * Integer.parseInt(splitMetrics[1].trim()) < 20000 ) //if( Integer.parseInt(splitMetrics[1].trim()) >= 20000 &&
			 * Integer.parseInt(splitMetrics[1].trim()) <=100000 ) //if( Integer.parseInt(splitMetrics[1].trim()) > 1000 ) t5=true; }
			 */
			// Comentei a linha abaixo para voltar ao normal
			// codigo abaixo gera os projetos que n�o tem nenhuma m�trica de concorrencia
			/*
			 * if(splitMetrics[0].trim().startsWith("Lines of Code")){ if(Integer.parseInt(splitMetrics[1].trim()) > 0 ){ return true; } }
			 * else if(Integer.parseInt(splitMetrics[1].trim()) > 0 ){ return false; } }
			 */
			// IMPORTANTE
			// modificar a linha abaixo, colocar FALSE ao descomentar as linhas que eu comentei para fazer
			// o if acima
			// }

			// if(t1 && t2 && t3 && t4 && t5)
			// return true;

		}
		in.close();
		return false;

	}

	public void excludeProjectOutOfthreshold() throws IOException {
		ArrayList<ArrayList<File>> clone = new ArrayList<ArrayList<File>>();
		clone.addAll(projectVersions);
		for (int i = 0; i < clone.size(); i++) {
			boolean exclude = true;
			// exclude = !inLimit(1,clone.get(i),"Synchronized keyword");

			if ((inLimit(1, clone.get(i), "Synchronized keyword")) || (inLimit(1, clone.get(i), "implements Runnable"))
					|| (inLimit(1, clone.get(i), "extends Runnable")) || (inLimit(1, clone.get(i), "extends Thread"))) {
				exclude = false;
			}

			if (exclude) {
				projectVersions.remove(clone.get(i));
			}
		}
	}

	protected String getProjectName(File version) {
		return version.getParentFile().getParentFile().getName() + "/" + version.getParentFile().getName();
	}

}

class VersionComparator implements Comparator<File> {

	@Override
	public int compare(File o1, File o2) {
		if (Integer.parseInt(o1.getName().substring(0, o1.getName().indexOf('.'))) > Integer
				.parseInt(o2.getName().substring(0, o2.getName().indexOf('.')))) { return 1; }

		return -1;
	}
}
