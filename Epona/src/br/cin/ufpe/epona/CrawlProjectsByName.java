package br.cin.ufpe.epona;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;


import javax.xml.bind.ParseConversionEvent;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import edu.uci.ics.crawler4j.util.IO;

import org.apache.log4j.Logger;

import Walker.Walker;
import parser.Parser;

public class CrawlProjectsByName extends WebCrawler {
	
	private static File storageFolder;
	static Logger logger = Logger.getLogger(CrawlProjectsByName.class);
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

	public void visit(Page page) {
		int docid = page.getWebURL().getDocid();
		String url = page.getWebURL().getURL();
		int parentDocid = page.getWebURL().getParentDocid();
		String html = page.getHTML();

		System.out.println("Docid: " + docid);
		System.out.println("URL: " + url);
		System.out.println("Docid of parent page: " + parentDocid);
		System.out.println("=============");

		// store image
		if (page.isBinary()) {
			String filename;
			String[] tokens = null;
			try {
				int i = url.lastIndexOf(".");
				String extension = url.substring(i, i + 4);
				tokens = url.split("/");
				filename = tokens[tokens.length - 2];
				logger.info("Nome Arquivo :" + filename + "da Url :" + url);
				filename = Util.unescape(filename);
			} catch (Exception e) {
				filename = page.getWebURL().getURL();
			}
			String caminho = storageFolder.getAbsolutePath();
			File f = new File(caminho);
			
			if (!f.exists()){
				f.mkdir();
			}
			
			caminho = storageFolder.getAbsolutePath() + "\\" + tokens[4];
			f = new File(caminho);
			
			if (!f.exists()){
				f.mkdir();
			}
			
			caminho = caminho + "\\" + tokens[6];
			File f2 = new File(caminho);
			
			if (!f2.exists()){
				f2.mkdir();
			}
			
			System.out.println("Caminho :" + caminho);
			
			IO.writeBytesToFile(page.getBinaryData(),
					caminho + "\\" + filename);
			logger.info("Baixou o projeto: " + filename);
			
			//UnconpressAndParseProjetct(caminho + "\\" + filename);
			
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
	
	private void callWalker(File fileSource)
	{
		Walker walker = new Walker (Configuration.getStringProperty("epona.ProjectcsUncompressedFolder"));
		walker.extractFile(fileSource);
	}
	
	private void UnconpressAndParseProjetct(String filesource){
		File file = new File(filesource);
		callWalker(file);
		//file.delete();
		File sourceFolder = new File (Configuration.getStringProperty("epona.ProjectcsUncompressedFolder")
				+ "/" + file.getParent().substring(file.getParent().lastIndexOf('\\')+1) 
				+ "/" + file.getName());
		Parser parser = new Parser(sourceFolder);
		parser.run();
		//Util.deleteTree(sourceFolder);
	}
	
	public static void main(String[] args) throws Exception {

		int numberOfCrawlers = Configuration.getIntProperty("epona.CrawlProjectsNames.crawlers_number");

		CrawlController controller = new CrawlController(Configuration.getStringProperty("epona.CrawlProjectsNames.root"));
		Vector<String> projects = readProjectsNames();

		for (String s : projects) {
			String url = String.format(
					"http://sourceforge.net/projects/%s/files/", s);
			controller.addSeed(url);
		}

		controller.setPolitenessDelay(800);

		CrawlProjectsByName.configure(Configuration.getStringProperty("epona.ProjectcsStorageFolder"));

		controller.start(CrawlProjectsByName.class, numberOfCrawlers);
	}
}
