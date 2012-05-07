import java.io.File;
import java.io.IOException;


public class Util {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String rootSourceFolder = "/home/wst/logsErroEpona";
		File fileSourceFolder = new File (rootSourceFolder);
		printFolderProjects(fileSourceFolder);

	}
	
	public static void printFolderProjects(File file) throws IOException{
		File subFiles[] = file.listFiles();
		
		if (subFiles != null){
			for (int i = 0; i < subFiles.length; i++){
				System.out.println(subFiles[i].getName());				
			}
		}
	}

}
