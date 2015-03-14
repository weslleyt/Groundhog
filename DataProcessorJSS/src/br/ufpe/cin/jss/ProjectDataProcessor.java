package br.ufpe.cin.jss;

import java.io.BufferedReader;
import java.io.File;
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
					projeto.getCategoria().add(categoriesName);
				}else {
					projeto.getCategoria().add(categoriesName);
					projetos.add(projeto);
				}			
				
			}
			
			in.close();
		}
	}
	
	public void fillProjectVersion(String logSourceFolder){
	
		
	}
	
	

}
