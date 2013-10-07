package com.pramati.wavemaker.pages;



import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.pramati.wavemaker.page.BasePage;

/**
 * Deployment Page, All API's of Deployment page
 * 
 * should be written here
 * 
 * @author krishnakumarnellore
 *
 */
public class Deployment extends BasePage{

	private static final String WAVEMAKER_CLOUDACCOUNT = "studio_deploymentDialog_deploymentDialog_cjLoginDialog";
	private static final String USERNAME = "studio_deploymentDialog_deploymentDialog_loginDialogUserEditor";
	private static final String PASSWORD = "studio_deploymentDialog_deploymentDialog_loginDialogPasswordEditor";
	private static final String OK_BTN = "studio_deploymentDialog_deploymentDialog_cjLogonOkButton";


	private static final String SETTING_WINDOW = "studio_deploymentDialog_deploymentDialog_settingLayers_client";
	private static final String DEPLOYMENT_EDITOR = "studio_deploymentDialog_deploymentDialog_cjDeploymentNameEditor";
	private static final String DEPLOYMENT_TYPE = "studio_deploymentDialog_deploymentDialog_cjDeploymentTypeEditor";
	private static final String DEPLOYMENT_NAME = "studio_deploymentDialog_deploymentDialog_cjNameEditor";
	private static final String DEPLOYMENT_URL = "studio_deploymentDialog_deploymentDialog_cjUrlpanel";

	private static final String BUTTON_BAR = "studio_deploymentDialog_deploymentDialog_buttonBar";
	private static final String DEPLOY_NOW = "studio_deploymentDialog_deploymentDialog_deployButton";
	private static final String SAVE_BTN  = "studio_deploymentDialog_deploymentDialog_saveButton";
	private static final String CLOSE_BTN  = "studio_deploymentDialog_deploymentDialog_closeButton";
	private static final String MANAGE_CLOUD_APP  = "studio_deploymentDialog_deploymentDialog_manageUndeployButton";


	private static final String CONFIRM_DIALOG = "app_confirmDialog";
	private static final String CONFIRM_CANCEL_BTN ="app_confirmDialog_button2";
	private static final String CONFIRM_OK_BTN= "app_confirmDialog_button1";

	private static final String  CJCANCELBTN= "studio_deploymentDialog_deploymentDialog_cjLoginCancelButton";


	private static final String WAIT_DIALOG_MSG ="wmWaitMessage";


	private static final String STUDIO_DIALOG = "studio_dialog";

	private static final String ACCOUNT_USERNAME = "input[type='text']";
	private static final String ACCOUNT_PASSWORD = "input[type='password']";

	private static final String ALERT_TEXT = "a[target='_NewWindow']";

	private static final String DEPLOYMENT_URL_TEXT = "div[role='textbox'";
	private static final String DEPLOYMENT_INPUT ="input";
	private static final String DEPLOYMENT_ROLE = "div[role='presentation'";


	// Displayed after clicking of Deploy Now button.This is progress bar
	private static final String DIALOGTITLEBAR = "studio_progressDialog_titleBar";
	
	private static final String WAVEMAKER_CLOUDACCOUNTDIALOG = "studio_deploymentDialog_deploymentDialog_loginDialogTargetEditor";
	private static final String WAVEMAKER_CLOUDACCOUNT_TARGETURL = "wmeditor-readonlyNode";

	BasePage basePage = new BasePage();


	private static Logger log = Logger.getLogger(Deployment.class);

	/**
	 * Constructor of Deployment page
	 * 
	 * @return Weblement of Deployment Window
	 */
	public WebElement Deployment() {
		waitForElementLocatedByID(WAVEMAKER_CLOUDACCOUNT, getTimeOutInSeconds());
		log.info("In Deployment page, Waiting for element located by id "+ WAVEMAKER_CLOUDACCOUNT);
		return basePage.getElementByID(WAVEMAKER_CLOUDACCOUNT);
	}

	/**
	 * Gets the Wavemaker Cloud Account's Cloud Target text
	 * 
	 * @return
	 */
	public String getWaveMakerCloudAccount_CloudTargetTxt(){
		basePage.sleep(5000);
		WebElement cloudTargetEle = Deployment().findElement(By.id(WAVEMAKER_CLOUDACCOUNTDIALOG));
		//log.info("In Deployment page, Got wavemaker cloud account dialog window, Target url text "+cloudTargetEle.findElement(By.className(WAVEMAKER_CLOUDACCOUNT_TARGETURL)).getText());
		return cloudTargetEle.findElement(By.className(WAVEMAKER_CLOUDACCOUNT_TARGETURL)).getText();		
	}

	/**
	 * Set's username and password in Account info dialog box
	 * 
	 * @param username
	 * @param password
	 */
	public void setUserPassword(String username,String password){
		waitForElementLocatedByID(USERNAME, getTimeOutInSeconds());		
		WebElement userEle = Deployment().findElement(By.id(USERNAME)).findElement(By.cssSelector(ACCOUNT_USERNAME));
		userEle.clear();
		userEle.sendKeys(username);
		log.info("In Deployment page, Setting username in username field of Dialog window "+ username);
		WebElement passEle = Deployment().findElement(By.id(PASSWORD)).findElement(By.cssSelector(ACCOUNT_PASSWORD));
		passEle.clear();
		log.info("In Deployment page, Setting password in password field of Dialog window "+ password);
		passEle.sendKeys(password);		
		log.info("In Deployment page, Clicking on Ok button of Dialog window ");
		Deployment().findElement(By.id(OK_BTN)).click();
		log.info("In Deployment page, Waiting for wait dialog window to close");
		waitForElementToDisableByID(STUDIO_DIALOG);
	}


	/**
	 * Click on Wavemaker Cloud Account Ok button, This is confirmation button.
	 * 
	 * Both OK And cancel are displayed in window.
	 * 
	 * This should be used after clicking on Deply Now button
	 * 
	 * This will wait till deployment happen 
	 * 
	 */
	public List<String> clickCloudAccountOkBtn(String username,String password){
		log.info("In Deployment page, Waiting for element located by id "+ CONFIRM_OK_BTN);
		waitForElementLocatedByID(CONFIRM_OK_BTN, getTimeOutInSeconds());
		log.info("In Deployment page, clicking on element located by id "+ CONFIRM_OK_BTN+" this is clicked on confirm dialog ok button");
		basePage.getElementByID(CONFIRM_OK_BTN).click();
		setUserPassword(username, password);
		log.info("In Deployment page, Username and password is set "+ CONFIRM_OK_BTN);		
		List<String> dialogText = waitForElementToDisableByID(DIALOGTITLEBAR); 
		log.info("In Deployment page, Getting Deployment dialog text, got text "+ dialogText);
		return dialogText;		
	}


	/**
	 * Get Error deployment message. Displayed once more than 5 war file are deployed in cloud.
	 * 
	 * @return
	 */
	public String alertErrorDeploying(){
		log.info("In Deployment page, Getting error deploying text. ");		
		log.info("In Deployment page, Getting Deployment dialog text, got text "+ basePage.getElementByID("app_alertDialog").findElement(By.id("app_alertDialog_userQuestionLabel"))
				.findElement(By.className("wmSizeNode")).getText());
		return basePage.getElementByID("app_alertDialog_genericInfoPanel").findElement(By.id("app_alertDialog_userQuestionLabel"))
				.findElement(By.className("wmSizeNode")).getText();
	}

	/**
	 * Get Link text of deployment page. Displayed Once successful deployment is done.
	 * 
	 * @return
	 */
	public String alertGetLinkTextOfDeployment(){
		log.info("In Deployment page, Getting Deployment dialog text, got link text "+ basePage.
				getElementByCSS(ALERT_TEXT).getText());
		//System.out.println(basePage.getElementByID("app_alertDialog").getText());
		WebElement alerttext = basePage.getElementByCSS(ALERT_TEXT);
		alerttext.click();
		return alerttext.getText();
	}

	/**
	 * Click on Wavemaker Cloud Account Cancel button
	 * 
	 * Both OK And cancel are displayed in window.
	 * 
	 * This should be used after clicking on Deply Now button
	 */
	public void clickCloudAccountCancelBtn(){
		log.info("In Deployment page, Waiting for element located by id "+ CONFIRM_OK_BTN);
		waitForElementLocatedByID(CONFIRM_OK_BTN, getTimeOutInSeconds());
		log.info("In Deployment page, Getting element located by id "+ CONFIRM_OK_BTN);
		basePage.getElementByID(CONFIRM_OK_BTN).click();
		log.info("In Deployment page, Clicking on cancel button, Finding element located by id "+ CJCANCELBTN  + "clicking");
		Deployment().findElement(By.id(CJCANCELBTN)).click();
	}


	/**
	 * Setting Window Webelement. This has Deployment URl,Deployment Name,Deployment type, Deployment App Name 
	 * 
	 * @return parent element of Setting window
	 */
	private WebElement getSettingWindowEle(){
		log.info("In Deployment page, Waiting for element located by id "+ SETTING_WINDOW);
		waitForElementLocatedByID(SETTING_WINDOW, getTimeOutInSeconds());
		log.info("In Deployment page, Getting element located by id "+ SETTING_WINDOW);
		return basePage.getElementByID(SETTING_WINDOW);
	}

	/**
	 * Get Name of Deployment field
	 * 
	 * @return
	 */
	public String getDeploymentName(){
		log.info("In Deployment page, Get's the deployment name from setting window of deployment page from tag name "+ DEPLOYMENT_INPUT);
		return getSettingWindowEle().findElement(By.id(DEPLOYMENT_EDITOR)).findElement(By.tagName(DEPLOYMENT_INPUT)).getText();
	}

	/**
	 * Get Type of Deployment field
	 * 
	 * Example : "WaveMaker Cloud"
	 * 
	 * @return
	 */
	public String getDeploymentType(){
		log.info("In Deployment page, Get's the deployment url text from setting window of deployment page from css "+ DEPLOYMENT_URL_TEXT);
		return getSettingWindowEle().findElement(By.id(DEPLOYMENT_TYPE)).findElement(By.cssSelector(DEPLOYMENT_URL_TEXT)).getText();
	}

	/**
	 * Gets Deployment APP Name
	 * 
	 * @return
	 */
	public String getDeploymentAPPName(){
		log.info("In Deployment page, Get's the deployment APP Name text from setting window of deployment page from css "+ DEPLOYMENT_ROLE);
		return getSettingWindowEle().findElement(By.id(DEPLOYMENT_NAME)).findElement(By.cssSelector(DEPLOYMENT_ROLE)).getText();
	}

	/**
	 * Get Deployment app URL
	 * 
	 * @return
	 */
	public String getDeploymentAPPURL(){
		log.info("In Deployment page, Get's the deployment APP URL text from setting window of deployment page from css "+ DEPLOYMENT_URL_TEXT);
		basePage.sleep(5000);
		return getSettingWindowEle().findElement(By.id(DEPLOYMENT_URL)).findElement(By.cssSelector(DEPLOYMENT_URL_TEXT)).getText();
	} 


	/**
	 * Gets Deployment Setting Window Parent Element.
	 * 
	 * This help in getting Deploy Now.
	 * Manage WM Cloud APPs
	 * Save
	 * Close 
	 * Buttons
	 * 
	 * @return
	 */
	private WebElement getButtonParentEle(){
		log.info("In Deployment page, Waiting for element located by id "+ BUTTON_BAR);
		waitForElementLocatedByID(BUTTON_BAR, getTimeOutInSeconds());
		log.info("In Deployment page, Getting element located by id "+ BUTTON_BAR);
		return basePage.getElementByID(BUTTON_BAR);
	}

	/**
	 * Click on Deploy Now button in Deployment Setting Window
	 * 
	 */
	public void clickDeployNowBtn(){
		log.info("In Deployment page, Clicking elment located by id "+ DEPLOY_NOW);
		getButtonParentEle().findElement(By.id(DEPLOY_NOW)).click();
	}

	/**
	 * Click on Save button in Deployment Setting Window
	 * 
	 */
	public void clickSaveBtn(){
		log.info("In Deployment page, Clicking element located by id "+ SAVE_BTN);
		getButtonParentEle().findElement(By.id(SAVE_BTN)).click();
	}

	/**
	 * Click on Close button in Deployment Setting Window
	 * 
	 */
	public void clickCloseBtn(){
		log.info("In Deployment page, Clicking close button located by id "+ CLOSE_BTN);
		getButtonParentEle().findElement(By.id(CLOSE_BTN)).click();
	}

	/**
	 * Click on Manage WM Cloud APP button in Deployment Setting Window
	 * 
	 */
	public void clickManageWMCloudApBtn(){
		log.info("In Deployment page, Clicking Manage cloud app button, located by id "+ MANAGE_CLOUD_APP);
		getButtonParentEle().findElement(By.id(MANAGE_CLOUD_APP)).click();
	}

	/**
	 * Deployment Confirm dialog box
	 * 
	 * @return Parent webelement of Confirm dialog box
	 */
	private WebElement getConfirmWindowEle(){
		log.info("In Deployment page, Waiting for element located by id "+ CONFIRM_DIALOG);
		waitForElementLocatedByID(CONFIRM_DIALOG, getTimeOutInSeconds());
		log.info("In Deployment page, Get element located by id "+ CONFIRM_DIALOG);
		return basePage.getElementByID("CONFIRM_DIALOG");
	}

	/**
	 * Confirm Application deployment Cancel Button
	 * 
	 * This window is displayed, After clicking on Deploy Now button in Deployment Setting Window.
	 */
	public void clickCancelBtn(){
		log.info("In Deployment page, Click on confirm window cancel button, located by id "+ CONFIRM_CANCEL_BTN);
		getConfirmWindowEle().findElement(By.id(CONFIRM_CANCEL_BTN)).click();
	}

	/**
	 * Confirm Application deployment OK Button
	 * 
	 * This window is displayed, After clicking on Deploy Now button in Deployment Setting Window.
	 */
	public void clickOkBtn(){
		log.info("In Deployment page, Click on confirm window Ok button, located by id "+ CONFIRM_OK_BTN);
		basePage.getElementByID(CONFIRM_OK_BTN).click();
		log.info("In Deployment page, Waiting for Dialog message to disable located by css "+ WAIT_DIALOG_MSG);
		waitForElementToDisableByClass(WAIT_DIALOG_MSG);		
	}
	
	
	public void navigateToNewUrl(String url){		
		driver.get(url);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.ENTER).build().perform();
	}
}