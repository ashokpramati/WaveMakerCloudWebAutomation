package com.pramati.wavemaker.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Base Class to take screen shot and run test case in remote System.
 * 
 * @author krishnakumarnellore
 *
 */
public class BaseRemoteWebDriver extends RemoteWebDriver 
implements TakesScreenshot{

	private final static String SCREENSHOTS_DIR = "screenshots";
	private static String imgLoc = null;
	private static File  screenShotFile = null;
	
	private static Logger log = Logger.getLogger(BaseRemoteWebDriver.class);
	

	static {
		try {			
			screenShotFile = new File("./target/"+SCREENSHOTS_DIR); 	// Creating Screen Shot in target directory.
			log.info("Screen shot file location has been set to "+ screenShotFile);
			
			screenShotFile.mkdir();                                 	// Making directory if deleted or Doesn't exist
			log.info("Screen shot file location is created"+ screenShotFile);
			
			imgLoc = screenShotFile.getCanonicalPath()+ File.separator; // Setting path of Image location
			
			log.info("Image location is set to "+ screenShotFile);
			
			

		} catch (IOException e) {
			e.printStackTrace();
			log.error("Error in creating image location");
		}
	}


	/**
	 * Gets the location(complete path) of screenshot directory
	 * 
	 * @return
	 */
	public static String getScreenshotDirectory() {
		log.info("Getting screen shot directory "+ imgLoc);
		return imgLoc;
	}



	public BaseRemoteWebDriver(URL url, DesiredCapabilities dc) {		
		super(url, dc);
		log.info("Called Remote webdriver constructor with "+ url + " desired capability "+ dc);
	}

	public <X> X getScreenshotAs(OutputType<X> target)
			throws WebDriverException {
		if ((Boolean) getCapabilities().getCapability(CapabilityType.TAKES_SCREENSHOT)) {
			return target.convertFromBase64Png(execute(DriverCommand.SCREENSHOT).getValue().toString());
		}
		return null;
	}

	public void takeScreenshot(String fileName) {
		File fileLoc = getScreenshotAs(OutputType.FILE);
		File screenshotFile = new File(fileName);
		try {
			FileUtils.copyFile(fileLoc, screenshotFile);
			log.info("Screen shot is created in location "+ screenshotFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
