package br.cin.ufpe.groundhog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

public class CrawlProjectsNames extends WebCrawler{

	Pattern filters = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
			+ "|png|tiff?|mid|mp2|mp3|mp4" + "|wav|avi|mov|mpeg|ram|m4v|pdf"
			+ "|rm|smil|wmv|swf|wma))$");
	private static File destinyFolder;
	private static FileWriter log;
	private static BufferedWriter out;
	private static ArrayList<String> troveOR = new ArrayList<String>();
	private static ArrayList<String> troveAND = new ArrayList<String>();
	private static int numeroAchados;
	
	public boolean isNextPage (String href){
		boolean ok = true;
		
		if(href.startsWith("http://sourceforge.net/search/?words=&sort=num_downloads_week&sortdir=desc&offset=")
				&& !href.contains("platform")){
			boolean has = false;
			
			if (troveOR.isEmpty()){
				has = true;
			}else{
				for( int i = 0; i < troveOR.size(); i++){
					if (href.contains(troveOR.get(i))){
						has = true;
					}
				}
			}
			if (has){
				for( int i = 0; i < troveAND.size(); i++){
					if (!href.contains(troveAND.get(i))){
						ok = false;
					}
				}
			}else{
				ok = false;
			}
		}else{
			ok = false;
		}
		
		return ok;
	}
	
	public void recentlyUpdated(String html){
		String year;
		String analise;
		int iyear;
		String projectcs[] = html.split("</a>\\s*&nbsp;<small>Updated ");
		
		for (int i = 1; i < projectcs.length; i++){
			year = projectcs[i].substring(0, 4);
			iyear = Integer.parseInt(year);
			System.out.println(year);
			if (iyear >= 2005){
				analise = projectcs[i-1].substring(projectcs[i-1].lastIndexOf('\n'));
				analise = analise.substring(analise.indexOf("/projects/")+10, analise.lastIndexOf("/\""));
				addArquivo(analise);
			}			
		}
	}
	
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		String projectName;
		if (isNextPage(href) && !href.contains("&offset=0")){
			//System.out.println(href);
			return true;
		}
		return false;
	}
	
	public void visit(Page page) {        
		WebURL url = page.getWebURL();
		String href = url.getURL();
		
		recentlyUpdated(page.getHTML());
	}
	
	public static void addArquivo (String name){
		try{
	        out.write(name);
	        out.newLine();
	        out.flush();
	        numeroAchados++;
	        System.out.println("Numero de projetos Achados: "+ numeroAchados);
		}catch(IOException e){
		}
	}
	
	public static void closeArchive(){
		try {
			out.close();
		} catch (IOException e) {
		}		
	}
	
	public static String getTroveNumber (String trove){
		String split[] = trove.split(":",2);
		return split[1];
	}
	
	public static void main(String[] args) throws Exception {
						
		destinyFolder = new File(Configuration.getStringProperty("epona.CrawlProjectsNames.root"));
		if (!destinyFolder.exists()) {
			destinyFolder.mkdirs();
		}
		log = new FileWriter(destinyFolder.getAbsolutePath() + "/projectsNames.txt");
		out = new BufferedWriter(log);
		
		int numberOfCrawlers = Configuration.getIntProperty("epona.CrawlProjectsNames.crawlers_number");
		String seedBase = "http://sourceforge.net/search/?";
		if (Configuration.getBooleanProperty("epona.CrawlProjectsNames.0")){
			seedBase += Configuration.getStringProperty("epona.CrawlProjectsNames.l0");
			troveAND.add("&fq%5b%5d=trove%3a" + getTroveNumber(Configuration.getStringProperty("epona.CrawlProjectsNames.l0")));
		}
		
		int i;
		
		ArrayList<String> seeds = new ArrayList<String>();
		
		//adiciona os status
		for (i = 1; i <= 7; i++){
			if (Configuration.getBooleanProperty("epona.CrawlProjectsNames."+i)){
				seeds.add(seedBase + Configuration.getStringProperty("epona.CrawlProjectsNames.l"+i));
				troveOR.add("&fq%5b%5d=trove%3a" + getTroveNumber(Configuration.getStringProperty("epona.CrawlProjectsNames.l"+i)));
			}
		}
		
		//categorias
//		for (;i <= 27; i++){
//			if (Configuration.getBooleanProperty("epona.CrawlProjectsNames."+i)){
//				for (int j = 0; j < seeds.size(); j++){
//					seeds.set(j, seeds.get(j)+Configuration.getStringProperty("epona.CrawlProjectsNames.l"+i));
//					troveAND.add("&fq%5b%5d=trove%3a" + getTroveNumber(Configuration.getStringProperty("epona.CrawlProjectsNames.l"+i)));
//				}
//			}
//		}
		
		if (seeds.isEmpty()){
			seeds.add(seedBase);
		}
		
		CrawlController controller = new CrawlController(Configuration.getStringProperty("epona.CrawlProjectsNames.root"));
		
		for (int j = 0; j < seeds.size(); j++){
			controller.addSeed(seeds.get(j));
			System.out.println(seeds.get(j));
		}		
		
		// Be polite:
		// Make sure that we don't send more than 5 requests per second (200 milliseconds between requests).
		controller.setPolitenessDelay(600);
		
		System.out.println("running");
		controller.start(CrawlProjectsNames.class, numberOfCrawlers);
	}
}
