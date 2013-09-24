package com.pramati.wavemaker.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Read property from configuration file
 * 
 * @author krishnakumarnellore
 *
 */
public class ConfigProperties {

	// Browser Configuration
	public static final String URL;
	public static final String HUBURL;
	public static final String BROWSER;
	public static final String COREDRIVERLOC;
	public static final String TIMEOUT;
	public static final String USERNAME;
	public static final String PASSWORD;
	public static final String PROJECTNAME;
	public static final String DEPLOYMENT_URL;
	
	private static Logger log = Logger.getLogger(ConfigProperties.class);
	
	
	private static Properties properties = new Properties();

	static {
		init();
		URL = properties.getProperty("url");
		log.info("Got url value from property file "+ URL);
		
		BROWSER = properties.getProperty("browser");
		log.info("Got browser value from property file "+ BROWSER);
		
		COREDRIVERLOC = properties.getProperty("coreDriverLoc");
		log.info("Got driver value from property file "+ COREDRIVERLOC);
		
		TIMEOUT = properties.getProperty("timeout");
		log.info("Got timeout value from property file "+ TIMEOUT);
		
		USERNAME = properties.getProperty("cloudJeeUsername");
		log.info("Got Cloudjee Username value from property file "+ USERNAME);
		
		PASSWORD = properties.getProperty("cloudJeePassword");
		log.info("Got Cloudjee Password value from property file "+ PASSWORD);
		
		PROJECTNAME = properties.getProperty("projectName");
		log.info("Got Cloudjee project name value from property file "+ PROJECTNAME);
		
		DEPLOYMENT_URL = properties.getProperty("deploymentUrl");
		log.info("Got Cloudjee deployment url value from property file "+ DEPLOYMENT_URL);
		
		HUBURL = properties.getProperty("huburl");
		log.info("Got hub url value from property file "+ HUBURL);
		
		
	}

	public static void init() {
		try {
			properties.load(ConfigProperties.class
					.getResourceAsStream("/config.properties"));
			log.info("Successfully loaded config.properties file");			
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Unable to load property file");
		}
	}
	
	public static void setProperties(String key,String value){
		properties.setProperty(key, value);
	}

}
