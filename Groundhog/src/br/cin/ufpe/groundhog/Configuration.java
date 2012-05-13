package br.cin.ufpe.groundhog;

import java.util.Properties;

import edu.uci.ics.crawler4j.crawler.Configurations;

public class Configuration {
	
	private static Properties prop = new Properties();
	
	public static String getStringProperty(String key) {
		if (prop == null) {
			return "";
		}
		return prop.getProperty(key);
	}
	
	public static int getIntProperty(String key){
		if (prop == null){
			return 0;
		}
		
		return Integer.parseInt(prop.getProperty(key));
	}
	
	public static boolean getBooleanProperty(String key){
		if (prop == null){
			return false;
		}
		
		return Boolean.parseBoolean(prop.getProperty(key));
	}
	
	static {
		try {
			prop.load(Configurations.class.getClassLoader()
					.getResourceAsStream("epona.properties"));
		} catch (Exception e) {
			prop = null;
			System.err.println("WARNING: Could not find epona.properties file in class path. I will use the default values.");
		}
	}
}
