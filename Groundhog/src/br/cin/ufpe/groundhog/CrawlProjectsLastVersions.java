package br.cin.ufpe.groundhog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import javax.xml.bind.ParseConversionEvent;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
//import edu.uci.ics.crawler4j.util.IO;

//import org.apache.log4j.Logger;

public class CrawlProjectsLastVersions extends WebCrawler {
	
	private static File storageFolder;
	//static Logger logger = Logger.getLogger(CrawlProjectsLastVersions.class);
	private String projectName;

	public static void configure(String storageFolderName) throws IOException {
		storageFolder = new File(storageFolderName);
		if (!storageFolder.exists()) {
			storageFolder.mkdirs();
		}
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String value) {
		this.projectName = value;
	}

	public boolean shouldVisit(WebURL url) {
		ShouldVisitUrl should = new ShouldVisitUrl(url.getURL());		
		return should.shoudVisit();
	}
	
	private String findNumber(String str){
		int beginIndex = 0, endIndex = 0;
		boolean found = false;
		
		for (int i = 0; i < str.length(); i++){
			if (str.charAt(i) >= '0' && str.charAt(i) <= '9'){
				beginIndex = i;
				found = true;
				break;
			}
		}
		
		for (int i = str.length() - 1; i >= 0 ; i--){
			if (str.charAt(i) >= '0' && str.charAt(i) <= '9'){
				endIndex = i;
				break;
			}
		}
		
		if (found){
			return str.substring(beginIndex, endIndex+1);
		}else{
			return null;
		}
	}

	public void visit(Page page) {
		int docid = page.getWebURL().getDocid();
		String url = page.getWebURL().getURL();
		int parentDocid = page.getWebURL().getParentDocid();
		String html = page.getHTML();
	
		String bars[] = url.split("/");
		if (bars.length == 5){
			String downloadUrl, lastUpdate;
			String projectcs[] = html.split("Download<br/>\\s*<small title=");
			String projectName = bars[4];
			
			if (projectcs.length > 1){
				downloadUrl = projectcs[1].substring(0, projectcs[1].indexOf("\">"));
				lastUpdate = downloadUrl.substring(downloadUrl.lastIndexOf('/')+1,downloadUrl.lastIndexOf("."));
				lastUpdate = findNumber(lastUpdate);
				String ramification[] = downloadUrl.split("/");
				
				addArquivo(projectName, lastUpdate, ramification[1]);
			}
			
			System.out.println(projectName + "             AQUIIIIIIIIIIIII");
			//System.out.println(lastUpdate + "             AQUIIIIIIIIIIIII");
			//System.out.println(downloadUrl + "             AQUIIIIIIIIIIIII");
			//System.out.println(ramification[1] + "             AQUIIIIIIIIIIIII");
		}
	}
	
	private static void addArquivo (String mainProjectName, String version, String subProject){
		try{
			File destinyFolder = new File("C:/Projetos2012/Projetos" + "/" + mainProjectName + "/" + subProject);
//			if (!destinyFolder.exists()) {
//				destinyFolder.mkdirs();
//			}
			FileWriter log = new FileWriter(destinyFolder.getAbsolutePath() + "/" + "lv-.txt");
			BufferedWriter out = new BufferedWriter(log);
			
			if (version != null){
				out.write(version);
			}
	        out.close();
		}catch(IOException e){
		}
	}

	public static Vector<String> readProjectsNames() throws IOException {
		Vector<String> retorno = new Vector<String>();

		File sFile = new File(Configuration.getStringProperty("epona.projectsnames_path"));
		BufferedReader in = new BufferedReader(new FileReader(sFile));

		String str;

		while ((str = in.readLine()) != null) {
			retorno.add(str);
		}
		return retorno;
	}
	
	public static void main(String[] args) throws Exception {

		int numberOfCrawlers = Configuration.getIntProperty("epona.CrawlProjectsNames.crawlers_number");

		CrawlController controller = new CrawlController(Configuration.getStringProperty("epona.CrawlProjectsNames.root"));
		Vector<String> projects = readProjectsNames();

		for (String s : projects) {
			controller.addSeed("http://sourceforge.net/projects/" + s);
		}

		controller.setPolitenessDelay(800);

		CrawlProjectsLastVersions.configure(Configuration.getStringProperty("epona.ProjectcsStorageFolder"));

		controller.start(CrawlProjectsLastVersions.class, numberOfCrawlers);
	}
}
