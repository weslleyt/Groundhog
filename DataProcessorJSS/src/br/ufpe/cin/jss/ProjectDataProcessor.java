package br.ufpe.cin.jss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDataProcessor {
	
	//private List<Projeto> projetos;	
	private ProjectList projetos;
	private CategoryList categoryList;

	public ProjectDataProcessor() {
		super();
		projetos = new ProjectList();
		categoryList = new CategoryList();
	}

	public ProjectList getProjetos() {
		return projetos;
	}
	
	

//	public void setProjetos(List<Projeto> projetos) {
//		this.projetos = new ;
//	} 
	
	public void fillProjects(String projectNamesFilePath ) throws IOException{
		File projectNamesFile = new File(projectNamesFilePath);
		
		BufferedReader in = new BufferedReader(new FileReader(projectNamesFile));
		
		String nomeProjeto;
		
		while ((nomeProjeto = in.readLine()) != null){
			Projeto projeto = new Projeto(nomeProjeto);
			projetos.getProjetos().add(projeto);			
		}
		
		in.close();
	}
	
	
	public void fillProjectsCategory(String dominioProjetosSourceFolder)
			throws IOException {
		File dominioFolder = new File(dominioProjetosSourceFolder);
		File[] categoriasFiles = dominioFolder.listFiles();
		String extention = ".txt";

		for (int i = 0; i < categoriasFiles.length; i++) {	
			
			String categoryName = categoriasFiles[i].getName().substring(0,
					categoriasFiles[i].getName().length() - extention.length());
			
			Category category = new Category(categoryName);
			categoryList.getCategories().add(category);
			
			BufferedReader in = new BufferedReader(new FileReader(categoriasFiles[i]));
			String nomeProjeto;
			
			while ((nomeProjeto = in.readLine()) != null) {
				
				Projeto projeto = new Projeto(nomeProjeto);
				
				if (projetos.getProjetos().contains(projeto))
				{
					projeto = projetos.getProjetos().get(projetos.getProjetos().indexOf(projeto));
					
					if (!projeto.getCategories().contains(category)){
						projeto.getCategories().add(category);
					}
				}/*else {
					projeto.getCategories().add(categoriesName);
					projetos.add(projeto);
				}*/			
				
			}
			
			in.close();
		}
	}
	
	public void fillProjectVersions(File sourceFolder,boolean isRootFolder, String nomeProjeto) throws NumberFormatException, IOException{
		
		
		File subFiles[] = sourceFolder.listFiles();

		if (subFiles != null) {
			for (int i = 0; i < subFiles.length; i++) {
				
				if (nomeProjeto==null || subFiles.length >1000){
					String nome = subFiles[i].getName();
					Projeto projeto = projetos.getProjectByName(nome);
					if (projeto!=null){
						nomeProjeto = nome;
					} else {
						continue;
					}
				}
				
//				if (subFiles[i].isDirectory()) { //Projeto Principal
//					nomeProjeto = subFiles[i].getName();
//					fillProjectVersions(subFiles[i],false,nomeProjeto);
//				}else 		
				if (subFiles[i].isDirectory()) { //Subprojetos					
					fillProjectVersions(subFiles[i],false,nomeProjeto);
				} else if (subFiles[i].isFile() && !subFiles[i].getName().contains("lv-.txt")) { //versões do projeto
//					if (i == 0) {
//						ArrayList<File> files = new ArrayList<File>();
//						files.add(subFiles[i]);
					
					Projeto projeto = projetos.getProjectByName(nomeProjeto);
					
					SubProject subProjeto = new SubProject(subFiles[i].getParentFile().getName());
					if(!projeto.getSubProjetos().contains(subProjeto)){
						projeto.getSubProjetos().add(subProjeto);
					}
					
					//projeto.getVersoes().add(new Versao(subFiles[i]));
					Versao versao = new Versao((subFiles[i]));
					projeto.getSubProjetos().get(projeto.getSubProjetos().indexOf(subProjeto)).getVersions().add(versao);
					
//						projectVersions.add(files);
//					} else {
//						projectVersions.get(projectVersions.size() - 1).add(subFiles[i]);
//					}
				}
			}
		}		
		
	}
	
	public void fillConcurrencyPropertie(File concurrentProjectListFile) throws IOException{
		
		BufferedReader in = new BufferedReader(new FileReader(concurrentProjectListFile));
		
		String str;
		Projeto project;
		SubProject subProject;
		
		while ((str = in.readLine()) != null) {
			if(str.indexOf("/")!=-1){
				String[] split = str.split("/");
				
				String projectName = split[1];
				String subProjectName = split[2];
				
				project = new Projeto(projectName);
				
				if (projetos.getProjetos().contains(project))
				{
					project = projetos.getProjetos().get(projetos.getProjetos().indexOf(project));
					subProject = new SubProject(subProjectName);
					
					List<SubProject> subProjetos = project.getSubProjetos();
					
					if (subProjetos.contains(subProject))
					{
						subProject = subProjetos.get(subProjetos.indexOf(subProject));
						subProject.setConcurrent(true);
					}else{
						System.out.println("nao tem o subprojeto: "+project.getNome() +"_"+subProjectName);
					}
				} else{
					System.out.println("nao tem o projeto: "+project);
				}
			}
			
		}
		
		in.close();
		
	}
	
	public static ArrayList<String> getMetricNames(String path) throws IOException{
		ArrayList<String> names = new ArrayList<String>();
		
		
		BufferedReader in = new BufferedReader(new FileReader(new File(path)));
		String str;
		
		while ((str = in.readLine()) != null) {
			names.add(str.split(":")[0].trim());
		}
		
		in.close();
		
		return names;
	}
	
	
	
	
	
	

}
