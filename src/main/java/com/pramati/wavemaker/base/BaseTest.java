/**
 * Copyright (c) 2013 - 2014 CloudJee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of CloudJee Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with CloudJee Inc.
 */

package com.pramati.wavemaker.base;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.pramati.wavemaker.pages.WelcomeStudio;

/**
 * 
 * @author <a href="mailto:pankaj.n@imaginea.com">pankaj</a>
 *
 */
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