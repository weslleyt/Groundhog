import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class baseSubProjectCheck {
	public File file;
	public String destFolder;
	
	public baseSubProjectCheck (String basePath){
		file = new File (basePath);
		this.destFolder = destFolder;
	}
	
	public void folderSearch(){
		File subFiles[] = file.listFiles();
	
		ExecutorService b = Executors.newFixedThreadPool(3);
		
		if (subFiles != null){
			for (int i = 0; i < subFiles.length; i++){
				subProjectCheck a = new subProjectCheck(subFiles[i]);
				b.execute(a);
			}
		}
		b.shutdown();
	}
	
	public static void main(String args[]){
		//String sourceFolder = "C:/logsMerged/logs";
		//descomentar a linha abaixo para voltar ao normal
		//String sourceFolder = "C:/Epona/logs";
		//String sourceFolder = "C:/Epona/Apache/Fonte2";
		//String sourceFolder = "C:/novosLogsCorrigidos";
		//String sourceFolder = "C:/Epona/AnaliseDetalhada/LogsAnaliseDetalhada";
		//String sourceFolder =  "C:/Epona/ArtigoOUTUBRO/Log";
		String sourceFolder =  "C:/Epona/ArtigoFevereiro/Log";


		//System.out.println("fez alguma coisa");
		baseSubProjectCheck parser = new baseSubProjectCheck(sourceFolder);
		parser.folderSearch();	
	}
}
