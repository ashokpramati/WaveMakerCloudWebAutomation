package com.pramati.cloudjee.webdriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.pramati.wavemaker.util.BaseRemoteWebDriver;
import com.pramati.wavemaker.util.ConfigProperties;

/**
 * This class initializes the driver object for various browser drivers <br>
 * e.g. firefox, chrome, internet explorer, safari etc. <br>
 * It also provides the browser information.
 * 
 * @author Nellore Krishna Kumar
 * 
 */
public class DriverManager {

	public static final String INTERNET_EXPLORER = "ie";
	public static final String FIREFOX = "firefox";
	public static final String CHROME = "chrome";
	public static final String SAFARI = "safari";

	private static BaseRemoteWebDriver driver = null;
	private static String driverLoc = null;
	private static Logger log = Logger.getLogger(DriverManager.class);

	static {
		try {
			System.out.println(ConfigProperties.COREDRIVERLOC);
			System.out.println(new File(ConfigProperties.COREDRIVERLOC)
					.getCanonicalPath() + File.separator);
			driverLoc = new File(ConfigProperties.COREDRIVERLOC)
					.getCanonicalPath() + File.separator;
			log.info("Driver location is set to '" + driverLoc + "'");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the instance of the driver class for the driver that is specified in
	 * the <code>"config/config.properties"</code> file
	 * 
	 * @return The driver object
	 */
	public static final BaseRemoteWebDriver getDriver() {
		String browser = ConfigProperties.BROWSER;
		if (driver == null) {

			if (browser.equalsIgnoreCase(FIREFOX)) {

				log.info("initializing 'firefox' driver...");
				DesiredCapabilities capabilities = DesiredCapabilities
						.firefox();
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,
						true);
				try {
					driver = new BaseRemoteWebDriver(new URL(
							ConfigProperties.HUBURL), capabilities);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			else if (browser.equalsIgnoreCase(CHROME)) {

				if (isPlatformWindows())
				
				log.info("initializing 'chrome' driver...");

				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,
						true);
				try {
					driver = new BaseRemoteWebDriver(new URL(
							ConfigProperties.HUBURL), capabilities);

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			else if (browser.equalsIgnoreCase(INTERNET_EXPLORER)) {

				Assert.assertTrue(isPlatformWindows(),
						"Internet Explorer is not supporting in this OS");

				DesiredCapabilities capabilities = DesiredCapabilities
						.internetExplorer();
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,
						true);
				try {
					driver = new BaseRemoteWebDriver(new URL(
							ConfigProperties.HUBURL), capabilities);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				log.info("initializing 'internet explorer' driver...");

			}

			else if (browser.equalsIgnoreCase(SAFARI)) {

				Assert.assertTrue(isPlatformWindows() || isPlatformMac(),
						"Safari is not supporting in this OS");
				DesiredCapabilities capabilities = DesiredCapabilities.safari();
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,
						true);
				try {
					driver = new BaseRemoteWebDriver(new URL(
							ConfigProperties.HUBURL), capabilities);				
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			else if (browser.equalsIgnoreCase("html")) {

				log.info("initializing 'html' driver...");
				DesiredCapabilities capabilities = DesiredCapabilities.htmlUnit();
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,
						true);
				try {
					driver = new BaseRemoteWebDriver(new URL(
							ConfigProperties.HUBURL), capabilities);				
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return driver;
	}

	/**
	 * Returns <code>true</code> if the current platform is Windows
	 * 
	 * @return
	 */
	public static boolean isPlatformWindows() {
		Platform current = Platform.getCurrent();
		return Platform.WINDOWS.is(current);
	}

	/**
	 * Returns <code>true</code> if the current platform is Mac
	 * 
	 * @return
	 */
	public static boolean isPlatformMac() {
		Platform current = Platform.getCurrent();
		return Platform.MAC.is(current);
	}

	/**
	 * Checks whether the current specified browser is Internet Explorer
	 * 
	 * @return <code>true</code> if the browser is Internet Explorer
	 */
	public static boolean isBrowserIE() {
		return ConfigProperties.BROWSER.equalsIgnoreCase(INTERNET_EXPLORER);
	}

	/**
	 * Checks whether the current specified browser is Mozilla Firefox
	 * 
	 * @return <code>true</code> if the browser is Mozilla Firefox
	 */
	public static boolean isBrowserFireFox() {
		return ConfigProperties.BROWSER.equalsIgnoreCase(FIREFOX);
	}

	/**
	 * Checks whether the current specified browser is Google Chrome
	 * 
	 * @return <code>true</code> if the browser is Google Chrome
	 */
	public static boolean isBrowserChrome() {
		return ConfigProperties.BROWSER.equalsIgnoreCase(CHROME);
	}

	/**
	 * Checks whether the current specified browser is Safari
	 * 
	 * @return <code>true</code> if the browser is Safari
	 */
	public static boolean isBrowserSafari() {
		return ConfigProperties.BROWSER.equalsIgnoreCase(SAFARI);
	}

	/**
	 * Gets the current running browser name
	 * 
	 * @return
	 */
	public static String getCurrentBrowserName() {
		Capabilities c = ((RemoteWebDriver) driver).getCapabilities();
		return c.getBrowserName();
	}

	/**
	 * Gets the current running browser version
	 * 
	 * @return
	 */
	public static String getCurrentBrowserVersion() {
		Capabilities c = ((RemoteWebDriver) driver).getCapabilities();
		return c.getVersion();
	}
}
