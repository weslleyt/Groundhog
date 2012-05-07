import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Box.Filler;



public class DataProcessorUnique {

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
		//ABAIXO TEM URL DOS NOVOS
		//String rootSourceFolder = "C:/Epona/AnaliseDetalhada/LogsAnaliseDetalhada";
		//String rootSourceFolder = "C:/novosLogsCorrigidos";
//		String rootSourceFolder = "C:/Epona/AnaliseDetalhada/LogsAnaliseDetalhadaSegurancaAlgumasDatas";
		//String rootSourceFolder = "C:/Epona/ArtigoFevereiro/Log";
		
		String rootSourceFolder = "/media/Dados/DropBox/Dropbox/Dissertacao/ArtigoFevereiro/log/log";
		
	//	String rootSourceFolder = "/home/weslley/categoria/NovoMedio/teste";
	//	String rootSourceFolder = "/home/weslley/categoria/grande/Lancados2005";
		//String rootSourceFolder = "/home/weslley/categoria/todos";
		
		//String rootSourceFolder = "C:/Epona/AnaliseDetalhada/LogIndividual9";
		
		
		//String rootSourceFolder = "C:/testes/tomcat";
		//String rootSourceFolder = "C:/novosTomcat/hibernate";
		

		//Abaixo tem a url dos resultados novos dos projetos source forge
		//String rootDestinyFolder = "C:/Epona/ArtigoOUTUBRO/Evolucao/resultadosReuniao17-11/Proporcao/AntesDe2005";
		//String rootDestinyFolder = "C:/Epona/ArtigoFevereiro/Resultado/Proporcional/Geral";
		
		//String rootDestinyFolder = "/home/weslley/evolucao/medio/2010";
		String rootDestinyFolder = "/home/weslley/novoGIT";
		
		//String rootDestinyFolder = "C:/Epona/TMCcorrecao/AnaliseIndividual/Backport";
		
		//String metricsNameSourceFolder = "C:/Epona/logsGPCE/metricsNames.txt";
		//String metricsNameSourceFolder = "C:/Epona/ArtigoFevereiro/metricsNames.txt";
		String projectsByCategoriesFolder = "/home/weslley/projetcsByCategory";
		
		String metricsNameSourceFolder = "/media/Dados/DropBox/Dropbox/Dissertacao/ArtigoFevereiro/metricsNames.txt";
		
		fileSourceFolder = new File(rootSourceFolder);
		File metricsNameFile = new File(metricsNameSourceFolder);
		//fillProjectsCategory(projectsByCategoriesFolder);

		MetricProcessFileManager.destinyFolder = rootDestinyFolder;

		//verifica qual categoria faz parte
//	compareProjectsCategory(projectsByCategoriesFolder, rootSourceFolder);
		
		// JUCContructsMetricProcessor juc = new JUCContructsMetricProcessor(
		// "j.u.c", true, true,true);
		// juc.process(fileSourceFolder);
		
		 ArrayList<String> metricsNames = getMetricNames(metricsNameFile);

		 
			//LOC Por ano escolhido	
//			 LoCMetricProcessor loc = new LoCMetricProcessor(true, "2005");
//			 loc.process(fileSourceFolder);

		 //Pegar primeira Versão que utiliza JUC - sem import
	//	 	getProjectsFirstVersionMetric("j.u.c.WITHOUT.imports", true, true, "2010", true);
			
		 	//Pegar a evolucao
		 	 generateSingleMetricsEvolution(metricsNames, true,true,true,true, "2010",true);
			  generateSingleMetricsEvolution(metricsNames, false,true,true, true, "2010", true);
			 
			 //para coletar dados de 1 só projeto
			 //generateSingleMetrics(metricsNames, true,true,true,"2005",false, true);
			 //generateSingleMetrics(metricsNames, false,true,true,"2005",false, true);
			 
		 	
		 
		//	 generateSingleMetrics(metricsNames, false,true,true, true);
		//	 generateSingleMetrics(metricsNames, true,true,true,true);	 
			 
			 //LoCMetricProcessor loc = new LoCMetricProcessor(true);
			 //loc.process(fileSourceFolder);
			 
			 //para gerar correlação 
/*				DateDoubleMetricProcessor dmdmp7 = new DateDoubleMetricProcessor("sync methods", "j.u.c.WITHOUT.imports",false, true);
				 dmdmp7.process(fileSourceFolder);	
			 
				 DateDoubleMetricProcessor dmdmp6 = new DateDoubleMetricProcessor("sync blocks", "j.u.c.WITHOUT.imports",false, true);
				 dmdmp6.process(fileSourceFolder);	
				 
				 DateDoubleMetricProcessor dmdmp8 = new DateDoubleMetricProcessor("sync methods", "Atomic variables",false, true);
				 dmdmp8.process(fileSourceFolder);
				 
				 DateDoubleMetricProcessor dmdmp9 = new DateDoubleMetricProcessor("sync blocks", "Atomic variables",false, true);
				 dmdmp9.process(fileSourceFolder);*/
				 
				 
		//	 CorrelationMetricProcessor dmdmp45 = new CorrelationMetricProcessor("HashMap", "j.u.c",false, true);
//			 dmdmp45.process(fileSourceFolder);
			
			 
	/*		 CorrelationMetricProcessor dmdmp7 = new CorrelationMetricProcessor("Synchronized keyword", "j.u.c",false,true);
				dmdmp7.process(fileSourceFolder);
			 
			CorrelationMetricProcessor dmdmp6 = new CorrelationMetricProcessor("Synchronized keyword", "Atomic variables",false, true);
				dmdmp6.process(fileSourceFolder);
			 
			 CorrelationMetricProcessor dmdmp50 = new CorrelationMetricProcessor("extends Thread", "executors",false, true);
			 dmdmp50.process(fileSourceFolder);
					
			 CorrelationMetricProcessor dmdmp8 = new CorrelationMetricProcessor("Synchronized keyword", "Juc.Locks",false, true);
			 dmdmp8.process(fileSourceFolder);
						
			 CorrelationMetricProcessor dmdmp9 = new CorrelationMetricProcessor("Hashtable", "Concurrent collections",false, true);
			 dmdmp9.process(fileSourceFolder);
			 
			 CorrelationMetricProcessor dmdmp10 = new CorrelationMetricProcessor("HashMap", "Concurrent collections",false, true);
			 dmdmp10.process(fileSourceFolder);
			 		 		 
			 CorrelationMetricProcessor dmdmp30 = new CorrelationMetricProcessor("Atomic variables", "Concurrent collections",false, true);
			 dmdmp30.process(fileSourceFolder);
			 
			 CorrelationMetricProcessor dmdmp31 = new CorrelationMetricProcessor("Atomic variables", "Hashtable",false, true);
			 dmdmp31.process(fileSourceFolder);
			 
			 CorrelationMetricProcessor dmdmp32 = new CorrelationMetricProcessor("Atomic variables", "HashMap",false, true);
			 dmdmp32.process(fileSourceFolder);
			 
			 CorrelationMetricProcessor dmdmp40 = new CorrelationMetricProcessor("notify()", "notifyAll()",false, true);
			 dmdmp40.process(fileSourceFolder);
			 
			 CorrelationMetricProcessor dmdmp41 = new CorrelationMetricProcessor("AllSynchronizedCollections", "Concurrent collections",false, true);
			 dmdmp41.process(fileSourceFolder);
			 
			 CorrelationMetricProcessor dmdmp12 = new CorrelationMetricProcessor("sync methods", "sync blocks",false, true);
			 dmdmp12.process(fileSourceFolder);
			 
			 CorrelationMetricProcessor dmdmp13 = new CorrelationMetricProcessor("Hashtable", "HashMap",false, true);
			 dmdmp13.process(fileSourceFolder);
			 
			 CorrelationMetricProcessor dmdmp14 = new CorrelationMetricProcessor("sync methods", "sync blocks",false, true);
			 dmdmp14.process(fileSourceFolder);
				*/
			 //para gerar evoluï¿œï¿œo software
			 
	/*		 DateDoubleMetricProcessor dmdmp6 = new DateDoubleMetricProcessor("Synchronized keyword", "Atomic variables",false, true);
				dmdmp6.process(fileSourceFolder);
			 
			 DateDoubleMetricProcessor dmdmp7 = new DateDoubleMetricProcessor("extends Thread", "executors",false, true);
			 dmdmp7.process(fileSourceFolder);
					
			 DateDoubleMetricProcessor dmdmp8 = new DateDoubleMetricProcessor("Synchronized keyword", "Juc.Locks",false, true);
			 dmdmp8.process(fileSourceFolder);
						
			 DateDoubleMetricProcessor dmdmp9 = new DateDoubleMetricProcessor("Hashtable", "Concurrent collections",false, true);
			 dmdmp9.process(fileSourceFolder);
			 
			 DateDoubleMetricProcessor dmdmp10 = new DateDoubleMetricProcessor("HashMap", "Concurrent collections",false, true);
			 dmdmp10.process(fileSourceFolder);
			 		 		 
			 DateDoubleMetricProcessor dmdmp12 = new DateDoubleMetricProcessor("sync methods", "sync blocks",false, true);
			 dmdmp12.process(fileSourceFolder);
			 
			 DateDoubleMetricProcessor dmdmp13 = new DateDoubleMetricProcessor("Hashtable", "HashMap",false, true);
			 dmdmp13.process(fileSourceFolder);
			 
			 DateDoubleMetricProcessor dmdmp14 = new DateDoubleMetricProcessor("sync methods", "sync blocks",false, true);
			 dmdmp14.process(fileSourceFolder);
	*/		 
//			 DateDoubleMetricProcessor dmdmp11 = new DateDoubleMetricProcessor("synchronizedCollection", "Concurrent collections",false, true);
//			 dmdmp11.process(fileSourceFolder);
			 
			 //para gerar os resultados relativos a mï¿œtrica por 100KLOC

				/*DateRelationshipMetricProcessor dmdmp12 = new DateRelationshipMetricProcessor("sync methods", "Lines of Code",false,true);
				dmdmp12.process(fileSourceFolder);
				DateRelationshipMetricProcessor dmdmp13 = new DateRelationshipMetricProcessor("sync blocks", "Lines of Code",false,true);
				dmdmp13.process(fileSourceFolder);

				DateRelationshipMetricProcessor dmdmp11 = new DateRelationshipMetricProcessor("j.u.c", "Lines of Code",false,true);
				dmdmp11.process(fileSourceFolder);
				
				DateRelationshipMetricProcessor dmdmp8 = new DateRelationshipMetricProcessor("extends Thread", "Lines of Code",false,true);
				dmdmp8.process(fileSourceFolder);
				DateRelationshipMetricProcessor dmdmp9 = new DateRelationshipMetricProcessor("implements Runnable", "Lines of Code",false,true);
				dmdmp9.process(fileSourceFolder);
				DateRelationshipMetricProcessor dmdmp10 = new DateRelationshipMetricProcessor("extends Runnable", "Lines of Code",false,true);
				dmdmp10.process(fileSourceFolder);
				 DateRelationshipMetricProcessor dmdmp7 = new DateRelationshipMetricProcessor("Synchronized keyword", "Lines of Code",false,true);
				dmdmp7.process(fileSourceFolder);*/		 
	//termina o cï¿œdigo referente a mï¿œtrica por 100KlOC
				
			 
			 
			//LOC normal	
//			 LoCMetricProcessor loc = new LoCMetricProcessor(true);
//			 loc.process(fileSourceFolder);
			
//			 LoCMetricProcessor loc2 = new LoCMetricProcessor(false);
//			 loc2.process(fileSourceFolder);
					
//				DateDoubleMetricProcessor2 dmdmp8 = new DateDoubleMetricProcessor2(
//						"Synchronized keyword", "Thread methods",false);
//				dmdmp8.process(fileSourceFolder);
			
		
//		DateDoubleMetricProcessor2 dmdmp6 = new DateDoubleMetricProcessor2(
//				"j.u.c", "extends Thread",true);
//		dmdmp6.process(fileSourceFolder);
		

			 //comentei a baixo dia 3/7/11 as 22.30h
			/* DateDoubleMetricProcessor2 dmdmp6 = new DateDoubleMetricProcessor2(
						"Atomic variables", "Synchronized keyword",false);
				dmdmp6.process(fileSourceFolder);
			 
				DateDoubleMetricProcessor2 dmdmp7 = new DateDoubleMetricProcessor2(
						"extends Thread", "Synchronized keyword",false);
				dmdmp7.process(fileSourceFolder);
				DateDoubleMetricProcessor2 dmdmp8 = new DateDoubleMetricProcessor2(
						"New Thread", "Synchronized keyword",false);
				dmdmp8.process(fileSourceFolder);
				
				DateDoubleMetricProcessor2 dmdmp9 = new DateDoubleMetricProcessor2(
						"implements Runnable", "extends Thread",false);
				dmdmp9.process(fileSourceFolder);
				
				DateDoubleMetricProcessor2 dmdmp10 = new DateDoubleMetricProcessor2(
						"implements Runnable", "Synchronized keyword",false);
				dmdmp10.process(fileSourceFolder); 
				
					 // A linha abaixo era para dizer a intersessï¿œo geral de extends thred e cyn
	DoubleMetricProcessor dmp = new DoubleMetricProcessor(true, "extends Thread",
					"Synchronized keyword");
			dmp.process(fileSourceFolder);

			 
			 // ADT e suas variantes
			DateDoubleMetricProcessor2 admdmp6 = new DateDoubleMetricProcessor2(
					"Atomic variables", "Synchronized keyword",false);
			admdmp6.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 admdmp7 = new DateDoubleMetricProcessor2(
					"Atomic variables", "wait()",false);
			admdmp7.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 admdmp8 = new DateDoubleMetricProcessor2(
					"Atomic variables", "notify()",false);
			admdmp8.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 admdmp9 = new DateDoubleMetricProcessor2(
					"Atomic variables", "notifyAll()",false);
			admdmp9.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 admdmp10 = new DateDoubleMetricProcessor2(
					"Atomic variables", "Concurrent collections",false);
			admdmp10.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 admdmp11 = new DateDoubleMetricProcessor2(
					"Atomic variables", "Juc.Locks",false);
			admdmp11.process(fileSourceFolder);
			
			// ConcurrentCollections e suas variantes
			DateDoubleMetricProcessor2 dmdmpt6 = new DateDoubleMetricProcessor2(
					"Concurrent collections", "Synchronized keyword",false);
			dmdmpt6.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmpt7 = new DateDoubleMetricProcessor2(
					"Concurrent collections", "wait()",false);
			dmdmpt7.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmpt8 = new DateDoubleMetricProcessor2(
					"Concurrent collections", "notify()",false);
			dmdmpt8.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmpt9 = new DateDoubleMetricProcessor2(
					"Concurrent collections", "notifyAll()",false);
			dmdmpt9.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmpt10 = new DateDoubleMetricProcessor2(
					"Concurrent collections", "Atomic variables",false);
			dmdmpt10.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmpt11 = new DateDoubleMetricProcessor2(
					"Concurrent collections", "Juc.Locks",false);
			dmdmpt11.process(fileSourceFolder);
				
			// sync e suas variantes
			
			DateDoubleMetricProcessor2 dmpf6 = new DateDoubleMetricProcessor2(
					"Synchronized keyword", "Concurrent collections", false);
			dmpf6.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmwspf6 = new DateDoubleMetricProcessor2(
						"Synchronized keyword", "Juc.Locks", false);
			dmdmwspf6.process(fileSourceFolder);

			DateDoubleMetricProcessor2 dmdmwspf7 = new DateDoubleMetricProcessor2(
					"Synchronized keyword", "Atomic variables", false);
			dmdmwspf7.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmwspf8 = new DateDoubleMetricProcessor2(
					"Synchronized keyword", "wait()", false);
			dmdmwspf8.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmwspf9 = new DateDoubleMetricProcessor2(
					"Synchronized keyword", "notify()", false);
			dmdmwspf9.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmwspf10 = new DateDoubleMetricProcessor2(
					"Synchronized keyword", "notifyAll", false);
			dmdmwspf10.process(fileSourceFolder);
			
			//wait
			
			DateDoubleMetricProcessor2 dmpfq6 = new DateDoubleMetricProcessor2(
					"wait()", "Concurrent collections", false);
			dmpfq6.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmwspfq6 = new DateDoubleMetricProcessor2(
						"wait()", "Juc.Locks", false);
			dmdmwspfq6.process(fileSourceFolder);

			DateDoubleMetricProcessor2 dmdmwspfq7 = new DateDoubleMetricProcessor2(
					"wait()", "Atomic variables", false);
			dmdmwspfq7.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmwspfq8 = new DateDoubleMetricProcessor2(
					"wait()", "Synchronized keyword", false);
			dmdmwspfq8.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmwspfq9 = new DateDoubleMetricProcessor2(
					"wait()", "notify()", false);
			dmdmwspfq9.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmwspfq10 = new DateDoubleMetricProcessor2(
					"wait()", "notifyAll", false);
			dmdmwspfq10.process(fileSourceFolder);
			
			
			//

//			notify
			
			DateDoubleMetricProcessor2 dmpfqw6 = new DateDoubleMetricProcessor2(
					"notify()", "Concurrent collections", false);
			dmpfqw6.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmwspfqw6 = new DateDoubleMetricProcessor2(
						"notify()", "Juc.Locks", false);
			dmdmwspfqw6.process(fileSourceFolder);

			DateDoubleMetricProcessor2 dmdmwspfqw7 = new DateDoubleMetricProcessor2(
					"notify()", "Atomic variables", false);
			dmdmwspfqw7.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmwspfqw8 = new DateDoubleMetricProcessor2(
					"notify()", "Synchronized keyword", false);
			dmdmwspfqw8.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmwspfqw9 = new DateDoubleMetricProcessor2(
					"notify()", "wait()", false);
			dmdmwspfqw9.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmwspfqw10 = new DateDoubleMetricProcessor2(
					"notify()", "notifyAll", false);
			dmdmwspfqw10.process(fileSourceFolder);
			
			//
			
//			notifyall
			
			DateDoubleMetricProcessor2 dmpfqwr6 = new DateDoubleMetricProcessor2(
					"notifyAll()", "Concurrent collections", false);
			dmpfqwr6.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmwspfqwr6 = new DateDoubleMetricProcessor2(
						"notifyAll()", "Juc.Locks", false);
			dmdmwspfqwr6.process(fileSourceFolder);

			DateDoubleMetricProcessor2 dmdmwspfqwr7 = new DateDoubleMetricProcessor2(
					"notifyAll()", "Atomic variables", false);
			dmdmwspfqwr7.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmwspfqwr8 = new DateDoubleMetricProcessor2(
					"notifyAll()", "Synchronized keyword", false);
			dmdmwspfqwr8.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmwspfqwr9 = new DateDoubleMetricProcessor2(
					"notifyAll()", "wait()", false);
			dmdmwspfqwr9.process(fileSourceFolder);
			
			DateDoubleMetricProcessor2 dmdmwspfqwr10 = new DateDoubleMetricProcessor2(
					"notifyAll()", "notify", false);
			dmdmwspfqwr10.process(fileSourceFolder);
			
			//
			
			
			
	// JUC LOCK E SUAS VARIANTES	
		DateDoubleMetricProcessor2 dmp6 = new DateDoubleMetricProcessor2(
				"Juc.Locks", "Concurrent collections", false);
		dmp6.process(fileSourceFolder);
		
		DateDoubleMetricProcessor2 dmdmwsp6 = new DateDoubleMetricProcessor2(
					"Juc.Locks", "Synchronized keyword", false);
		dmdmwsp6.process(fileSourceFolder);

		DateDoubleMetricProcessor2 dmdmwsp7 = new DateDoubleMetricProcessor2(
				"Juc.Locks", "Atomic variables", false);
		dmdmwsp7.process(fileSourceFolder);
		
		DateDoubleMetricProcessor2 dmdmwsp8 = new DateDoubleMetricProcessor2(
				"Juc.Locks", "wait()", false);
		dmdmwsp8.process(fileSourceFolder);
		
		DateDoubleMetricProcessor2 dmdmwsp9 = new DateDoubleMetricProcessor2(
				"Juc.Locks", "notify()", false);
		dmdmwsp9.process(fileSourceFolder);
		
		DateDoubleMetricProcessor2 dmdmwsp10 = new DateDoubleMetricProcessor2(
				"Juc.Locks", "notifyAll", false);
		dmdmwsp10.process(fileSourceFolder);
		*/
		 	
		
		

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
		
		private static void generateSingleMetrics(ArrayList<String> metricsNames,
				boolean hasThreshold, boolean printProjectName, boolean hasMetric, boolean proporcionalLoc)
				throws IOException {

			for (int i = 0; i < metricsNames.size(); i++) {
				SingleMetricProcessor smp = new SingleMetricProcessor(
						metricsNames.get(i), hasThreshold, printProjectName,
						hasMetric, proporcionalLoc);
				smp.process(fileSourceFolder);
			}
		}

		private static void generateSingleMetrics(ArrayList<String> metricsNames,
				boolean hasThreshold, boolean printProjectName, boolean hasMetric, String year)
				throws IOException {

			for (int i = 0; i < metricsNames.size(); i++) {
				SingleMetricProcessor smp = new SingleMetricProcessor(
						metricsNames.get(i), hasThreshold, printProjectName,
						hasMetric, year);
				smp.process(fileSourceFolder);
			}
		}
		
		private static void generateSingleMetrics(ArrayList<String> metricsNames,
				boolean hasThreshold, boolean printProjectName, boolean hasMetric, String year, Boolean proporcionalLoc)
				throws IOException {

			for (int i = 0; i < metricsNames.size(); i++) {
				SingleMetricProcessor smp = new SingleMetricProcessor(
						metricsNames.get(i), hasThreshold, printProjectName,
						hasMetric, year, proporcionalLoc);
				smp.process(fileSourceFolder);
			}
		}
		
		private static void getProjectsFirstVersionMetric(String metricName, boolean concurrentProjects,boolean printProjectName, String releasedDate, boolean proporcionalLoc ) throws IOException{
			
			SingleMetricProcessor smp = new SingleMetricProcessor(
					metricName, concurrentProjects, printProjectName, releasedDate, proporcionalLoc);
			smp.process(fileSourceFolder);
			
		}

		
		private static void generateSingleMetricsEvolution(ArrayList<String> metricsNames,
				boolean hasThreshold, boolean printProjectName, boolean hasMetric, boolean evolution, String year, Boolean proporcionalLoc)
				throws IOException {

			for (int i = 0; i < metricsNames.size(); i++) {
				SingleMetricProcessor smp = new SingleMetricProcessor(
						metricsNames.get(i), hasThreshold, printProjectName,
						hasMetric, evolution, year, proporcionalLoc);
				smp.process(fileSourceFolder);
			}
		}
		
		/**
		 * Metodo utilizado para coletar todas as mÃ©tricas de um sÃ³ projeto
		 * mas lembre-se de passar o caminho apenas do projeto.
		 * 
		 * */
		private static void generateSingleMetrics(ArrayList<String> metricsNames,
				boolean hasThreshold, boolean printProjectName, boolean hasMetric, String year, Boolean proporcionalLoc, Boolean onlyOneProject)
				throws IOException {

			for (int i = 0; i < metricsNames.size(); i++) {
				SingleMetricProcessor smp = new SingleMetricProcessor(
						metricsNames.get(i), hasThreshold, printProjectName,
						hasMetric, year, proporcionalLoc, onlyOneProject);
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
		
		private static void compareProjectsCategory(String projectsCategoriesFolder, String projectsCSVfile)
		throws IOException {
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
				int contadorProjetos=0, contador=0;
				while ((str = in.readLine()) != null) {
					BufferedReader inProj = new BufferedReader(new FileReader(listFilesCSVprojects[0]));
					String proj= "";
					int s = 0;
					while ((proj = inProj.readLine()) != null) {
						String[] splitProjectsName = proj.split(",");
							if (splitProjectsName[0].startsWith(str)){
								contadorProjetos++;
							}
					}
					//t++;
//					System.out.println("valor S: " + s);
					inProj.close();
					//System.out.println("era para ser o tamanho do arquivo csv: " + s);
				//	contador++;
					
				}
				System.out.println(listFiles[i].getName() + " : " + contadorProjetos );
				//System.out.println("valor de t: " + t);
				in.close();
				
			}
			
		
			
			System.out.println("Terminou");	
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