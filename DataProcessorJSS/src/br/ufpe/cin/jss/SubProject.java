package br.ufpe.cin.jss;

import java.util.ArrayList;
import java.util.List;

public class SubProject extends Projeto{

	
	private List<Versao> versions;
	private boolean isConcurrent;
	
	public SubProject(String name) {
		super(name);
		versions = new ArrayList<Versao>();
	}
	
	public List<Versao> getVersions() {
		return versions;
	}

	public void setVersions(List<Versao> versoes) {
		this.versions = versoes;
	}

	public boolean isConcurrent() {
		return isConcurrent;
	}

	public void setConcurrent(boolean isConcurrent) {
		this.isConcurrent = isConcurrent;
	}	

}
