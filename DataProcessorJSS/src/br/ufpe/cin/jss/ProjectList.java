package br.ufpe.cin.jss;

import java.util.ArrayList;
import java.util.List;

public class ProjectList {
	
	public ProjectList() {
		super();
		this.projetos = new ArrayList<Projeto>();
	}

	private List<Projeto> projetos;
	
		public List<Projeto> getProjetos() {
		return projetos;
	}
	
	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}	
	
	public Projeto getProjectByName(String nome){
		Projeto proj = null;
		for (Projeto projeto : projetos) {
			if (projeto.getNome().equals(nome))
				proj = projeto;
		}
		
		return proj;		
	}
	
	public List<Category> getCategoriesFromProjects(boolean concurrent){
		
		List<Category> categories = new ArrayList<Category>();
		
		for (Projeto projeto : projetos) {
			for (SubProject sub : projeto.getSubProjetos()) {
				if(sub.isConcurrent()==concurrent && sub.hasVerionMoreThan1kLoC()){
					for (Category category : projeto.getCategories()) {
						
						//Category cat = new Category(category.getName());
						if(category!=null){
							if (!categories.contains(category)) {
								categories.add(category);							
							}
							
							int times;
							
							if (concurrent){
								times = category.getConcurrentTimes();
								times++;
								category.setConcurrentTimes(times);
							}  else{
								times = category.getNonConcurrentTimes();
								times++;
								category.setNonConcurrentTimes(times);
							}
						}
					}					
				}
			}
		}		
		
		return categories;		
	}

}
