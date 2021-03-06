import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box.Filler;

public class DataProcessor {

	public static ArrayList<ArrayList<String>> projectsByCategory = new ArrayList<ArrayList<String>>();
	public static ArrayList<String> categoriesName = new ArrayList<String>();
	static File fileSourceFolder;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//String rootSourceFolder = "C:/Epona/logsGPCE/A_logs_REMOVED_EMPTY_FOLDERS_BY_DATE";
		//String rootSourceFolder = "C:/Epona/logsGPCE/logs";
//		String rootSourceFolder = "C:/novosLogsCorrigidos";
//		String rootDestinyFolder = "C:/Epona/smash/correlacao";
		String metricsNameSourceFolder = "/Users/weslleytorres/Google Drive/doutorado/ArtigoGroundHogJSS/Dados/projectsNames.txt";
		String projectsByCategoriesFolder = "/Users/weslleytorres/Google Drive/doutorado/ArtigoGroundHogJSS/Dados/projetcsByCategory";

//		fileSourceFolder = new File(rootSourceFolder);
		File metricsNameFile = new File(metricsNameSourceFolder);
		fillProjectsCategory(projectsByCategoriesFolder);

//		MetricProcessFileManager.destinyFolder = rootDestinyFolder;

	//	// JUCContructsMetricProcessor juc = new JUCContructsMetricProcessor(
		// "j.u.c", true, true,true);
	//	 juc.process(fileSourceFolder);
		
		 ArrayList<String> metricsNames = getMetricNames(metricsNameFile);
		 
		// SingleMetricProcessor smp = new SingleMetricProcessor(
		//		 "j.u.c", true, true,true);
	////	smp.process(fileSourceFolder);
		 
		 AllProjectbyCategoryMetricProcessor a = new AllProjectbyCategoryMetricProcessor();
		 a.process(fileSourceFolder);
		

	}

	private static void generateSingleMetrics(ArrayList<String> metricsNames,
			boolean hasThreshold, boolean printProjectName, boolean hasMetric)
			throws IOException {

		for (int i = 0; i < metricsNames.size(); i++) {
			SingleMetricProcessor smp = new SingleMetricProcessor(
					metricsNames.get(i), hasThreshold, printProjectName,
					hasMetric);
			smp.process(fileSourceFolder);
		}
	}

	private static void fillProjectsCategory(String sourceFolder)
			throws IOException {
		File fileFolder = new File(sourceFolder);
		File[] listFiles = fileFolder.listFiles();
		String extention = ".txt";

		for (int i = 0; i < listFiles.length; i++) {
			ArrayList<String> projects = new ArrayList<String>();
			categoriesName.add(listFiles[i].getName().substring(0,
					listFiles[i].getName().length() - extention.length()));
			BufferedReader in = new BufferedReader(new FileReader(listFiles[i]));
			String str;
			while ((str = in.readLine()) != null) {
				projects.add(str);
			}
			projectsByCategory.add(projects);
		}
	}

	private static ArrayList<String> getMetricNames(File file)
			throws IOException {

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
