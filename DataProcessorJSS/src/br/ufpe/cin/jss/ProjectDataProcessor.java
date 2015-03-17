package br.ufpe.cin.jss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDataProcessor {
	
	private List<Projeto> projetos;	

	public ProjectDataProcessor() {
		super();
		projetos = new ArrayList<Projeto>();
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}
	
	public Projeto getProjectByName(String nome){
		Projeto proj = null;
		for (Projeto projeto : projetos) {
			if (projeto.getNome().equals(nome))
				proj = projeto;
		}
		
		return proj;
		
		//return projetos.get(projetos.indexOf(new Projeto(nome)));
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	} 
	
	
	public void fillProjectsCategory(String dominioProjetosSourceFolder)
			throws IOException {
		File dominioFolder = new File(dominioProjetosSourceFolder);
		File[] categoriasFiles = dominioFolder.listFiles();
		String extention = ".txt";

		for (int i = 0; i < categoriasFiles.length; i++) {	
			
			String categoriesName = categoriasFiles[i].getName().substring(0,
					categoriasFiles[i].getName().length() - extention.length());
			
			BufferedReader in = new BufferedReader(new FileReader(categoriasFiles[i]));
			String nomeProjeto;
			
			while ((nomeProjeto = in.readLine()) != null) {
				
				Projeto projeto = new Projeto(nomeProjeto);
				
				if (projetos.contains(projeto))
				{
					projeto = projetos.get(projetos.indexOf(projeto));
					projeto.getCategories().add(categoriesName);
				}else {
					projeto.getCategories().add(categoriesName);
					projetos.add(projeto);
				}			
				
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
					Projeto projeto = getProjectByName(nome);
					if (projeto!=null){
						nomeProjeto = nome;
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
					
					Projeto projeto = getProjectByName(nomeProjeto);
					
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
				
				if (projetos.contains(project))
				{
					project = projetos.get(projetos.indexOf(project));
					subProject = new SubProject(subProjectName);
					
					List<SubProject> subProjetos = project.getSubProjetos();
					
					if (subProjetos.contains(subProject))
					{
						subProject = subProjetos.get(subProjetos.indexOf(subProject));
						subProject.setConcurrent(true);
					}
				}
			}
			
		}
		
	}
	
	
	
	
	
	

}
