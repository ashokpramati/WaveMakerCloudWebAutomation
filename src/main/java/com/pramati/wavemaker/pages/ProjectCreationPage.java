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
public class ProjectCreationPage extends BasePage{

	private static final String NAV_MENU = "studio_navigationMenu";
	private static final String MENU_LIST = "div[id^='dijit_MenuBar_']";
	private static final String MENU_FILE_LIST = "tr[id^='dijit_MenuItem_']";
	private static final String MENU_FILE_SUB_LIST = "td[id^='dijit_MenuItem_']";

	private static final String DEPLOYMENT_DIALOG = "studio_deploymentDialog_deploymentDialog_newDeploymentDialog";
	private static final String DEPLOYMENT_TOMCAT = "studio_deploymentDialog_deploymentDialog_tomcatRadio";
	private static final String DEPLOYMENT_CLOUDJEE = "studio_deploymentDialog_deploymentDialog_cloudjeeRadio";
	private static final String DEPLOYMENT_APPFILE = "studio_deploymentDialog_deploymentDialog_appfilesRadio";
	private static final String DEPLOYMENT_BUTTON = "studio_deploymentDialog_deploymentDialog_buttonBar1";
	private static final String DEPLOYMENT_BUTTON_OK = "studio_deploymentDialog_deploymentDialog_okButton";
	private static final String DEPLOYMENT_BUTTON_CANCEL = "studio_deploymentDialog_deploymentDialog_cancelButton";

	private static final String WAIT_DIALOG_MSG ="wmWaitMessage";
	
	BasePage basePage = new BasePage();

	public WebElement ProjectCreationPage() {
		waitForElementLocatedByID(NAV_MENU, getTimeOutInSeconds());
		waitForElementLocatedByCSS(MENU_LIST, getTimeOutInSeconds());	
		return basePage.getElementByCSS(MENU_LIST);
	}

	/**
	 * Click on File Menu.
	 * 
	 * @param menuName
	 */
	public void clickMenuBar(String menuName,String subMenu){		
		List<WebElement> menuList = ProjectCreationPage().findElements(By.tagName("span"));
		for (WebElement mL : menuList) {
			if(mL.getText().equalsIgnoreCase(menuName)){				
				mL.click();
				clickFileSubMenu(subMenu);
			}
		}
	}

	/**
	 * Click on File Sub Menu
	 * 
	 * @param subMenu
	 */
	private void clickFileSubMenu(String subMenu){
		waitForElementLocatedByCSS(MENU_FILE_LIST, getTimeOutInSeconds());
		List<WebElement> subMenuParentEle = basePage.getElementsByCSS(MENU_FILE_LIST);
		for (WebElement sMPE : subMenuParentEle) {			
			List<WebElement> subMenuList = sMPE.findElements(By.tagName("td"));
			for (WebElement sML : subMenuList) {
				if(sML.getText().contains(subMenu)){
					sML.click();
					break;
				}
			}
		}
	}

	public void clickFileSubSubMenu(String subSubMenu){		
		waitForElementLocatedByCSS(MENU_FILE_SUB_LIST, getTimeOutInSeconds());
		List<WebElement> subMenuParentEle = basePage.getElementsByCSS(MENU_FILE_SUB_LIST);
		for (WebElement sML : subMenuParentEle) {
			if(sML.getText().contains(subSubMenu)){
				sML.click();
				break;
			}
		}
	}

	public void selectDeployment(String deploymentOption){
		waitForElementLocatedByID(DEPLOYMENT_DIALOG, getTimeOutInSeconds());
		WebElement DeploymentParentEle = basePage.getElementByID(DEPLOYMENT_DIALOG);
		if(deploymentOption.equalsIgnoreCase("Tomcat")){
			clickTomcat(DeploymentParentEle);
		}
		else if(deploymentOption.equalsIgnoreCase("WaveMaker Cloud (CloudJee)")){
			clickCloudJee(DeploymentParentEle);
		}
		else if(deploymentOption.equalsIgnoreCase("appfile")){
			clickAppFile(DeploymentParentEle);
		}
	}

	/**
	 * @param subMenuParentEle
	 */
	private void clickAppFile(WebElement subMenuParentEle) {
		subMenuParentEle.findElement(By.id(DEPLOYMENT_APPFILE)).click();
	}

	/**
	 * @param subMenuParentEle
	 */
	private void clickCloudJee(WebElement subMenuParentEle) {
//		subMenuParentEle.findElement(By.id(DEPLOYMENT_CLOUDJEE)).click();
		subMenuParentEle.findElement(By.id("dijit_form_RadioButton_6")).click();
	}

	/**
	 * @param subMenuParentEle
	 */
	private void clickTomcat(WebElement subMenuParentEle) {
		subMenuParentEle.findElement(By.id(DEPLOYMENT_TOMCAT)).click();
	}

	/**
	 * Click button of deployment Dialog box
	 * 
	 * @param btnTxt
	 */
	public void clickDeploymentBtn(String btnTxt){
		waitForElementLocatedByID(DEPLOYMENT_DIALOG, getTimeOutInSeconds());
		WebElement deplomentParentBtn = basePage.getElementByID(DEPLOYMENT_DIALOG);
		if(btnTxt.equalsIgnoreCase("ok")){
			clickOkBtn(deplomentParentBtn);
		}
		else if(btnTxt.equalsIgnoreCase("cancel")){
			clickCancelBtn(deplomentParentBtn);
		}		
	}

	/**
	 * @param deplomentParentBtn
	 */
	private void clickCancelBtn(WebElement deplomentParentBtn) {
		deplomentParentBtn.findElement(By.id(DEPLOYMENT_BUTTON_CANCEL)).click();
	}

	/**
	 * @param deplomentParentBtn
	 */
	private void clickOkBtn(WebElement deplomentParentBtn) {
		deplomentParentBtn.findElement(By.id(DEPLOYMENT_BUTTON_OK)).click();
	}

}