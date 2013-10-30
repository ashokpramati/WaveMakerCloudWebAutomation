/**
 * Copyright (c) 2013 - 2014 CloudJee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of CloudJee Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with CloudJee Inc.
 */

package com.pramati.wavemaker.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.pramati.wavemaker.page.BasePage;

/**
 * 
 * @author <a href="mailto:pankaj.n@imaginea.com">pankaj</a>
 *
 */
public class WelcomeStudio extends BasePage {

	private static final String WELCOME_WAVEMAKER_DIALOG = "studio_startPageDialog_start_panel1";
	private static final String CREATE_NEW_PROJECT ="studio_startPageDialog_start_newProject";
	private static final String TUTORIALS = "studio_startPageDialog_start_tutorial";
	private static final String DOCUMENTATION = "studio_startPageDialog_start_documentation";
	private static final String REGISTER = "studio_startPageDialog_start_register";

	private static final String PROJECT_TAB = "studio_startPageDialog_start_tabLayers1_decorator_button2";
	private static final String EXISTING_PROJECT_LIST = "studio_startPageDialog_start_existingProjectList";
	
	private static final String INPUT_SEARCHBOX = "dijit_form_TextBox_1";
	private static final String WINDOW_LIST = "wmlist-list";
	
	private static final String STUDIO_DIALOG = "studio_dialog";
	
	private static final String WAIT_DIALOG_MSG ="wmWaitMessage";

	BasePage basePage = new BasePage();

	public WelcomeStudio() {
		super.init();		
		waitForElementLocatedByID(WELCOME_WAVEMAKER_DIALOG, getTimeOutInSeconds());	
	}

	public WebElement WelcomeTab() {
		waitForElementLocatedByID(WELCOME_WAVEMAKER_DIALOG, getTimeOutInSeconds());	
		return basePage.getElementByID(WELCOME_WAVEMAKER_DIALOG);
	}

	public void clickNewProject(){
		waitForElementLocatedByID(WELCOME_WAVEMAKER_DIALOG, getTimeOutInSeconds());		
		WelcomeTab().findElement(By.id(CREATE_NEW_PROJECT)).click();
	}

	public void clickTutorial(){
		waitForElementLocatedByID(TUTORIALS, getTimeOutInSeconds());	
		WelcomeTab().findElement(By.id(TUTORIALS)).click();
	}

	public void clickDocumentation(){
		waitForElementLocatedByID(DOCUMENTATION, getTimeOutInSeconds());	
		WelcomeTab().findElement(By.id(DOCUMENTATION)).click();
	}

	public void clickRegister(){
		waitForElementLocatedByID(REGISTER, getTimeOutInSeconds());	
		WelcomeTab().findElement(By.id(REGISTER)).click();
	}

	private WebElement getProjectTab(){
		waitForElementLocatedByID(PROJECT_TAB, getTimeOutInSeconds());
		return basePage.getElementByID(PROJECT_TAB);
	}

	public void openExistingProject(String projectName) {
		getProjectTab().click();
		waitForElementLocatedByID(INPUT_SEARCHBOX, getTimeOutInSeconds());
		
		basePage.getElementByID(INPUT_SEARCHBOX).sendKeys(projectName);
		WebElement existingPrjtList = basePage.getElementByID(EXISTING_PROJECT_LIST);
		List<WebElement> winPrjtList = existingPrjtList.findElement(By.className(WINDOW_LIST)).findElements(By.tagName("div"));
		for (WebElement prjtList : winPrjtList) {
			if(prjtList.getText().equalsIgnoreCase(projectName)){
				prjtList.click();
				break;
			}		
		}
		clickOpenProject();
		waitForPageToLoad(60);
//		waitForElementToDisableByID(STUDIO_DIALOG);
	}

	public void deleteExistingProject(String projectName){
		getProjectTab().click();
		waitForElementLocatedByID(PROJECT_TAB, getTimeOutInSeconds());
		WebElement existingPrjtList = basePage.getElementByID(EXISTING_PROJECT_LIST);
		List<WebElement> winPrjtList = existingPrjtList.findElement(By.id(WINDOW_LIST)).findElements(By.tagName("div"));
		for (WebElement prjtList : winPrjtList) {
			if(prjtList.getText().equalsIgnoreCase(projectName)){
				prjtList.click();
				break;
			}			
		}
		clickDeleteProject();
		waitForElementToDisableByClass(WAIT_DIALOG_MSG);
	}
	
	/**
	 * 
	 */
	public void createNewProject() {
		waitForElementLocatedByID("studio_startPageDialog_start_newProjectBtn", getTimeOutInSeconds());
		basePage.getElementByID("studio_startPageDialog_start_newProjectBtn").click();
	}

	/**
	 * 
	 */
	private void clickDeleteProject() {
		waitForElementLocatedByID("studio_startPageDialog_start_deleteProjectBtn", getTimeOutInSeconds());
		basePage.getElementByID("studio_startPageDialog_start_deleteProjectBtn").click();
	}

	/**
	 * 
	 */
	private void clickOpenProject() {
		waitForElementLocatedByID("studio_startPageDialog_start_openProjectBtn", getTimeOutInSeconds());
		basePage.getElementByID("studio_startPageDialog_start_openProjectBtn").click();
	}

}

