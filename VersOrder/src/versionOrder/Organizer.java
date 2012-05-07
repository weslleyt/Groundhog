package versionOrder;
import java.io.File;


public class Organizer implements Comparable<Organizer>{
	public String name;
	public String version[];
	public File file;
	
	@Override
	public int compareTo(Organizer other) {
		
		if (this.version != null && other.version != null){
			for (int i = 0; i < this.version.length && i < other.version.length; i++){
				if (this.version[i].toLowerCase().compareTo(other.version[i].toLowerCase()) != 0){
					return this.version[i].toLowerCase().compareTo(other.version[i].toLowerCase());
				}
			}
			
			if (this.version.length < other.version.length){
				return -1;
			}else if (this.version.length > other.version.length){
				return 1;
			}
		}	
		return 0;
		
	}
	
	public void separateVersions(String number){
		version = number.split("[-\\._]");
	}
	
	
}