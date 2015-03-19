/**
 * 
 */
package br.ufpe.cin.jss;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BenitoAvell
 *
 */
public class CategoryList {
	
	
	public CategoryList() {
		super();
		this.categories = new ArrayList<Category>();
	}

	private List<Category> categories;
	
		public List<Category> getCategories() {
		return categories;
	}
	
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}	
	
	public Category getCategoryByName(String nome){
		Category cat = null;
		for (Category category : categories) {
			if (category.getName().equals(nome))
				cat = category;
		}
		
		return cat;		
	}

}
