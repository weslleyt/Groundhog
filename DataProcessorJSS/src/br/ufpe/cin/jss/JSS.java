package br.ufpe.cin.jss;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class JSS {
	
	public static ArrayList<ArrayList<String>> projectsByCategory = new ArrayList<ArrayList<String>>();
	public static ArrayList<String> categoriesName = new ArrayList<String>();
	static File fileSourceFolder;
	static String metricsNamesPath = "C:/Users/BenitoAvell/Google Drive/jss/ArtigoGroundHogJSS/Dados/metricsNames.txt";
		
	public static void main(String[] args) {
		
		String dominioSourceFolder = "C:/Users/BenitoAvell/Google Drive/jss/ArtigoGroundHogJSS/Dados/Dominio/DominioProjetos";
		String logSourceFolder ="C:/Users/BenitoAvell/Documents/mestrado/JSS/log/";
		String concurrentProjectListFilePath = "C:/Users/BenitoAvell/Google Drive/jss/ArtigoGroundHogJSS/Dados/ResultadoMetricas/Geral/Lines of CodeProjectNameHasMetric.csv";		
		String rootDestinyFolder = "C:/Users/BenitoAvell/Google Drive/jss/ArtigoGroundHogJSS/Dados/ResumoCaracteristicasV8/";
		String projectNamesFilePath = "C:/Users/BenitoAvell/Google Drive/jss/ArtigoGroundHogJSS/Dados/projectsNames.txt";
		String destinyFolderDomain = "C:/Users/BenitoAvell/Google Drive/jss/ArtigoGroundHogJSS/Dados/ResumoDominioBenito/";
		

		try {
			
			ProjectDataProcessor processor = new ProjectDataProcessor();
			processor.fillProjects(projectNamesFilePath);
			processor.fillProjectsCategory(dominioSourceFolder);
			
			DataProjectFileManager fileManager = new DataProjectFileManager(rootDestinyFolder);
			
			fileSourceFolder = new File (logSourceFolder);			
			processor.fillProjectVersions(fileSourceFolder,false,null);
			processor.fillConcurrencyPropertie(new File(concurrentProjectListFilePath));
			
			
			List<Category> categoriaConcorrente = processor.getProjetos().getCategoriesFromProjects(true);
			List<Category> categoriaNaoConcorrente = processor.getProjetos().getCategoriesFromProjects(false);
			
			gerarArquivoCsv(destinyFolderDomain, categoriaConcorrente, true);

			
			System.out.println("CONCURRENT");
			for (Category category : categoriaConcorrente) {
				System.out.println(category.getName()+";"+category.getConcurrentTimes());
			}
			
			System.out.println("NON-CONCURRENT");
			for (Category category : categoriaNaoConcorrente) {
				System.out.println(category.getName()+";"+category.getNonConcurrentTimes());
			}
			
			
			for (Projeto projeto : processor.getProjetos().getProjetos()) {
				
//				for (Projeto sub : projeto.getSubProjetos()) {
//					for (Versao versao : sub.getVersoes()) {
//						System.out.println(projeto.toString() +" "+ sub.toString() +" "+ versao.getVersaoLog().getName());
//					}
//				}	
				
				fileManager.writeProjectDataIntoCSV(projeto);
				fileManager.writeSubProjectDataIntoCSV(projeto);
							
			}
						
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
	private static void gerarArquivoCsv(String destino, List<Category> categorias, boolean concorrente) {

		if (concorrente) {
			destino = destino + "Concurrent" + "/";
		} else {
			destino = destino + "NonConcurrent" + "/";
		}
		
		File destinoFile = new File(destino);
		
		if (!destinoFile.exists()){
			destinoFile.mkdirs();
		}

		for (Category categoria: categorias) {
			try {
				FileWriter writer = new FileWriter(destino + categoria.getName() + ".csv");

				writer.append("Projeto");
				writer.append(';');
				writer.append("LoC");
				writer.append(';');
				
			
				ArrayList<String> metricsNameArrayList = ProjectDataProcessor.getMetricNames(metricsNamesPath);
				
				for (String name : metricsNameArrayList) {
					writer.append(name);
					writer.append(';');
				}			

				writer.append('\n');

				ArrayList<String> nomeProjetos = new ArrayList<String>();

				for (Projeto projeto: categoria.getListaProjetos()) {

					for (SubProject subprojeto: projeto.getSubProjetos()) {
						for (int i = (subprojeto.getVersions().size() - 1); i >= 0; i--) {

							if ((concorrente && subprojeto.getVersions().get(i).getLoc() >= 1000 && subprojeto.isConcurrent())
									|| (!concorrente && subprojeto.getVersions().get(i).getLoc() >= 1000)) {

								// gambi para nao adicionar o projeto mais de uma vez.
								if (!nomeProjetos.contains(subprojeto.getNome() + subprojeto.getVersions().get(i).getIdentificador())
										&& (subprojeto.isConcurrent() == concorrente)) {

									// if (categoria.getName().equals("ScientificEngineering")) {
									// System.out.println(projeto.getNome());
									// }

									writer.append(subprojeto.getVersions().get(i).getIdentificador());
									writer.append(';');
									writer.append(Integer.toString(subprojeto.getVersions().get(i).getLoc()));
									writer.append(';');
									
									//LinkedHashMap<String, Integer> metricas = subprojeto.getVersions().get(i).getMetricas();
									
									for (String string : metricsNameArrayList) {
										
										Double normalizedValue = subprojeto.getVersions().get(i).getNormalizedMetric(string);
										
										//Integer value = metricas.get(normalizedValue);
										
										if (normalizedValue != null){
											String valueString = String.format("%.2f",normalizedValue);
											writer.append(valueString);
											//writer.append(Double.toString(normalizedValue));
											
										}/* else
										{
											writer.append("NULL");
										}*/
										
										writer.append(';');
									}

									writer.append('\n');

									nomeProjetos.add(subprojeto.getNome() + subprojeto.getVersions().get(i).getIdentificador());

								}
								break;
							}
						}
					}

				}

				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	

}
