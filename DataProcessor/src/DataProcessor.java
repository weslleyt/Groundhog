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
		String rootSourceFolder = "C:/novosLogsCorrigidos";
		String rootDestinyFolder = "C:/Epona/smash/correlacao";
		String metricsNameSourceFolder = "C:/Epona/logsGPCE/metricsNames.txt";
		String projectsByCategoriesFolder = "C:/Epona/logsGPCE/projetcsByCategory";

		fileSourceFolder = new File(rootSourceFolder);
		File metricsNameFile = new File(metricsNameSourceFolder);
		fillProjectsCategory(projectsByCategoriesFolder);

		MetricProcessFileManager.destinyFolder = rootDestinyFolder;

	//	// JUCContructsMetricProcessor juc = new JUCContructsMetricProcessor(
		// "j.u.c", true, true,true);
	//	 juc.process(fileSourceFolder);
		
		 ArrayList<String> metricsNames = getMetricNames(metricsNameFile);
		 
		// SingleMetricProcessor smp = new SingleMetricProcessor(
		//		 "j.u.c", true, true,true);
	////	smp.process(fileSourceFolder);
		 
		 AllProjectbyCategoryMetricProcessor a = new 
		 AllProjectbyCategoryMetricProcessor();
		 a.process(fileSourceFolder);
		
		 //generateSingleMetrics(metricsNames, false,false,false);
		// generateSingleMetrics(metricsNames, true,false,false);
		// generateSingleMetrics(metricsNames, true,true,false);
		// generateSingleMetrics(metricsNames, true,true,true);
		// generateSingleMetrics(metricsNames, false,true,false);

		DoubleMetricProcessor dmp = new DoubleMetricProcessor("j.u.c",
				"Synchronized keyword", true);
		dmp.process(fileSourceFolder);

		MeanDoubleMetricProcessor mdmp = new MeanDoubleMetricProcessor("j.u.c",
				"Synchronized keyword", true);
		mdmp.process(fileSourceFolder);

		DateDoubleMetricProcessor dmdmp = new DateDoubleMetricProcessor(
					"j.u.c", "Synchronized keyword", true);
			dmdmp.process(fileSourceFolder);
		 
//		
	
		DoubleMetricProcessor dmp2 = new DoubleMetricProcessor("j.u.c",
				"Object Methods Notification", true);
		dmp2.process(fileSourceFolder);

		MeanDoubleMetricProcessor mdmp2 = new MeanDoubleMetricProcessor(
				"j.u.c", "Object Methods Notification", true);
		mdmp2.process(fileSourceFolder);
		
		DateDoubleMetricProcessor dmdmp2 = new DateDoubleMetricProcessor(
					"j.u.c", "Object Methods Notification", true);
			dmdmp2.process(fileSourceFolder);
		 
//
		DoubleMetricProcessor dmp3 = new DoubleMetricProcessor("j.u.c",
				"Thread methods", true);
		dmp3.process(fileSourceFolder);

		MeanDoubleMetricProcessor mdmp3 = new MeanDoubleMetricProcessor(
				"j.u.c", "Thread methods", true);
		mdmp3.process(fileSourceFolder);
		
		 DateDoubleMetricProcessor dmdmp3 = new DateDoubleMetricProcessor(
					"j.u.c", "Thread methods", true);
			dmdmp3.process(fileSourceFolder);
		 
//
		DoubleMetricProcessor dmp4 = new DoubleMetricProcessor(
				"Atomic variables", "Synchronized keyword", true);
		 dmp4.process(fileSourceFolder);

		MeanDoubleMetricProcessor mdmp4 = new MeanDoubleMetricProcessor(
				"Atomic variables", "Synchronized keyword", true);
		mdmp4.process(fileSourceFolder);
		
		DateDoubleMetricProcessor dmdmp4 = new DateDoubleMetricProcessor(
					"Atomic variables", "Synchronized keyword", true);
			dmdmp4.process(fileSourceFolder);
//		
		DoubleMetricProcessor dmp5 = new DoubleMetricProcessor(
				"Concurrent collections", "Synchronized keyword", true);
		dmp5.process(fileSourceFolder);

		MeanDoubleMetricProcessor mdmp5 = new MeanDoubleMetricProcessor(
				"Concurrent collections", "Synchronized keyword", true);
		mdmp5.process(fileSourceFolder);
		
		DateDoubleMetricProcessor dmdmp5 = new DateDoubleMetricProcessor(
				"Concurrent collections", "Synchronized keyword", true);
		dmdmp5.process(fileSourceFolder);
//			
		DoubleMetricProcessor dmp6 = new DoubleMetricProcessor(
				"Juc.Locks", "Synchronized keyword", true);
		dmp5.process(fileSourceFolder);

		MeanDoubleMetricProcessor mdmp6 = new MeanDoubleMetricProcessor(
				"Juc.Locks", "Synchronized keyword", true);
		mdmp5.process(fileSourceFolder);
		
		DateDoubleMetricProcessor dmdmp6 = new DateDoubleMetricProcessor(
					"Juc.Locks", "Synchronized keyword", true);
			dmdmp6.process(fileSourceFolder);
		
		
			
		 
		
		 TrendMetricProcessor tmpJUC = new TrendMetricProcessor("j.u.c",
		 true);
		 tmpJUC.process(fileSourceFolder);

		 GroupTrendMetricProcessor gtmp = new
		 GroupTrendMetricProcessor("j.u.c",
		 true);
		 gtmp.process(fileSourceFolder);

		 LoCMetricProcessor loc = new LoCMetricProcessor(true);
		 loc.process(fileSourceFolder);
		
		 LoCMetricProcessor loc2 = new LoCMetricProcessor(false);
		 loc2.process(fileSourceFolder);

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
