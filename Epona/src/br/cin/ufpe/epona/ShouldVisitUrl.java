package br.cin.ufpe.epona;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class ShouldVisitUrl {
	
	private String url;
	static Logger logger = Logger.getLogger(ShouldVisitUrl.class);
	Pattern filters = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
			+ "|png|tiff?|mid|mp2|mp3|mp4" + "|wav|avi|mov|mpeg|ram|m4v|pdf"
			+ "|md5|sha-256|sha1|lzma|rpm|bz2|jar" + "|rm|smil|wmv|swf|wma))/download$");
	
	
	public ShouldVisitUrl(String url)
	{
		this.url = url;
	}
	
	public boolean shoudVisit(){
		boolean retorno = false;

		logger.info("should visit " + url);
		if (url.contains("files/") && !url.endsWith("files/")) {
			retorno = true;
		}
		
		if (filters.matcher(url.toLowerCase()).matches()) {
			retorno = false;
		}
		
		
		if (url.endsWith("/download") && 
		    !(url.contains("src")||
		      verifyWordInVersionString(url, "source")||		
		      url.contains("-source"))) {
			retorno = false;
		}
		if (url.contains("stats")) {
			retorno = false;
		}

		logger.info(retorno);
		return retorno;
	}
	
	public boolean verifyWordInVersionString(String url,String word){		
		String[] urlS = url.split("/");		
		return urlS[urlS.length-2].contains(word);		
	}
	
	public static void main(String[] args) {
		
		 //Testing
		//ShouldVisitUrl s = new ShouldVisitUrl("http://sourceforge.net/projects/jboss/files/JBoss/JBoss-6.0.0.Final/jboss-as-distribution-6.0.0.Final-src.zip.MD5/download");
		ShouldVisitUrl s = new ShouldVisitUrl("http://sourceforge.net/projects/flaim/files/stable/xflaim/source/");
		boolean so = s.shoudVisit();
		System.out.println(so);
		
		//ShouldVisitUrl s2 = new ShouldVisitUrl("http://sourceforge.net/projects/jboss/files/JBoss/JBoss-6.0.0.Final/jboss-as-distribution-6.0.0.Final-src.zip/download");
		ShouldVisitUrl s2 = new ShouldVisitUrl("http://sourceforge.net/projects/flaim/files/");
		
		boolean so2 = s2.shoudVisit();
		System.out.println(so2);
		
	}
}
