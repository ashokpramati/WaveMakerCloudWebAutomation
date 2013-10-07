package com.pramati.wavemaker.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.pramati.wavemaker.page.BasePage;
import com.pramati.wavemaker.pages.WelcomeStudio;

public class BaseTest {

	protected WelcomeStudio homePage = null;

	@BeforeTest(alwaysRun = true)
	public void runBeforeMethod() {
		homePage = new WelcomeStudio();		
	}

	@AfterTest(alwaysRun = true)
	public void runAfterMethod() {
		if (homePage != null) {			
			homePage.quitBrowser();
			homePage.resetDriver();			
			homePage = null;
		}

	}

}