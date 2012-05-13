package br.cin.ufpe.groundhog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;
import org.apache.commons.lang.StringUtils;

import javax.xml.bind.ParseConversionEvent;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import edu.uci.ics.crawler4j.util.IO;

import org.apache.log4j.Logger;

import Walker.Walker;
import parser.Parser;
import versionOrder.Organizer;

public class CrawlUpdateDates extends WebCrawler {

	private static File storageFolder;
	static Logger logger = Logger.getLogger(CrawlProjectsByName.class);
	private String projectName;
	final int BARS_HOME_FILES_PATH = 6;

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
		if (url.getURL().endsWith("/download")) {
			return false;
		}

		return should.shoudVisit();
	}

	public String correctName(String a) {
		return StringUtils.strip(a);
	}

	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		String html = page.getHTML();

		if (html != null) {
			// checks if its a last level folder (has a downloadable file)
			String lastLevelCheck[] = html
					.split("<th scope=\"row\" headers=\"files_name_h\">\\s*<a href=\"");
			if (lastLevelCheck != null && lastLevelCheck.length > 1) {
				String link = lastLevelCheck[1].substring(0,
						lastLevelCheck[1].indexOf('"'));

				if (link.endsWith("/download")) {
					String bars[] = url.split("/");
					String projectName = bars[4];

					// This block should only be use if folders name have been
					// fixed
					// projectName = projectName.replaceAll("%20", " ");
					// projectName = projectName.replaceAll("%5B", "[");
					// projectName = projectName.replaceAll("%5D", "]");
					// projectName = projectName.replaceAll("%28", "(");
					// projectName = projectName.replace("%29", ")");

					String subProjectName = null;
					boolean setSubProjectName = true;

					if (bars.length > BARS_HOME_FILES_PATH) {
						subProjectName = bars[BARS_HOME_FILES_PATH];
					} else {
						// subProjectName = projectName;
						setSubProjectName = false;
					}

					// System.out.println(subProjectName + "---" + projectName);

					String projectcs[] = html.split("class=\"name\">\\s*");

					// Here is the path to logs folder, can be edited to any
					// other
					String root = Configuration
							.getStringProperty("epona.CrawlProjectsNames.root");

					for (int i = 1; i < projectcs.length - 1; i++) {

						String name[] = projectcs[i].split("</a>");
						String fileName = name[0];
						fileName = correctName(fileName);

						System.out.println(fileName);

						if (!setSubProjectName) {
							subProjectName = fileName;
						}

						String path = root + projectName + "/" + subProjectName
								+ "/";
//						File arq = new File(path + fileName + "a.txt");
//						System.out.println(arq.getAbsolutePath());

						File file = getFile(fileName, path);

						if (file.exists()) {
							String findDate[] = projectcs[i]
									.split("<abbr title=\"");
							String date = findDate[1].substring(0, 10);

							File rename = new File(path + "[" + date + "]"
									+ file.getName());

							file.renameTo(rename);

							System.out.println("RENAMED: "+file.getAbsolutePath());
						}
					}
				}
			}
		}
	}

	private File getFile(String fileName, String path){
		File file = null;
		file = new File(path + fileName + "a.txt");
		if (!file.exists())
			file = new File(path + fileName + ".txt");
		if (!file.exists())
			try {
				file = new File(path + fileName.substring(0, fileName.lastIndexOf("."))   + ".txt");
			} catch (Exception e) {
				System.out.println(path+fileName);
				System.out.println(fileName.lastIndexOf("."));
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return file;
	}

	public static Vector<String> readProjectsNames() throws IOException {
		Vector<String> retorno = new Vector<String>();

		File sFile = new File(
				Configuration.getStringProperty("epona.projectsnames_path"));
		BufferedReader in = new BufferedReader(new FileReader(sFile));

		String str;

		while ((str = in.readLine()) != null) {
			retorno.add(str);
		}
		return retorno;
	}

	public static void main(String[] args) throws Exception {

		int numberOfCrawlers = Configuration
				.getIntProperty("epona.CrawlProjectsNames.crawlers_number");

		CrawlController controller = new CrawlController(
				Configuration
						.getStringProperty("epona.CrawlProjectsNames.root"));
		Vector<String> projects = readProjectsNames();

		for (String s : projects) {
			String url = String.format(
					"http://sourceforge.net/projects/%s/files/", s);
			controller.addSeed(url);
		}

		controller.setPolitenessDelay(800);

		CrawlProjectsByName.configure(Configuration
				.getStringProperty("epona.ProjectcsStorageFolder"));

		controller.start(CrawlUpdateDates.class, numberOfCrawlers);
	}
}
