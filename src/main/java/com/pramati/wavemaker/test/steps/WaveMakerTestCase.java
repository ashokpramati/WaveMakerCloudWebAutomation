package com.pramati.wavemaker.test.steps;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.pramati.wavemaker.base.BaseTest;
import com.pramati.wavemaker.page.BasePage;
import com.pramati.wavemaker.pages.CloudJeeApplication;
import com.pramati.wavemaker.pages.Deployment;
import com.pramati.wavemaker.pages.NewProjectDialog;
import com.pramati.wavemaker.pages.ProjectCreationPage;
import com.pramati.wavemaker.pages.WelcomeStudio;

/**
 * Test Case for deployement of Application in Cloud
 * 
 * @author krishnakumarnellore
 *
 */
public class WaveMakerTestCase extends BaseTest {
	Calendar calendar = new GregorianCalendar();

	String url = null;
	public static String projectName = null;

	NewProjectDialog newProjectDialog = null;
	ProjectCreationPage projectCreationPage = null;
	Deployment deployment = null;
	CloudJeeApplication cloudJeeApplication = null;

	private static final String UNDEPLOY = "UNDEPLOY";

	private static final String START = "START";
	private static final String STOP = "STOP";
	
	private static final String  FILE = "File";
	private static final String FILE_DEPLOY_PROJECT = "Deploy Project";
	private static final String FILE_NEW_DEPLOYMENT = "New Deployment";
	private static final String FILE_MANAGE_DEPLOYMENT = "Manage Deployment";
	
	private static final String MENU = "Menu";
	private static final String OK = "ok";
	private static final String DEPLOYMENT_ON = "cloudjee";
	//private static final String DEPLOY_URL = "https://apps.mywavemaker.com";
	private static final String DEPLOY_URL = "https://apps.wavemakercloud.com";
	
	
	private static Logger log = Logger.getLogger(WaveMakerTestCase.class);


	@Test(dataProvider="cj")//,dependsOnMethods="verifyMaxUnDeployApplication")	
	public void testDeployUndeployStartStopOfProject(String usename,String password) throws InterruptedException {
		homePage.clickNewProject();
	
		projectName = "Project"+"A"+calendar.get(Calendar.MINUTE)+calendar.get(Calendar.SECOND);

		newProjectDialog = new NewProjectDialog();
		newProjectDialog.setProjectName(projectName);
		
		
		newProjectDialog.setDeskTopTemplate(MENU);
		newProjectDialog.clickButton(OK);
		projectCreationPage = new ProjectCreationPage();
		projectCreationPage.clickMenuBar(FILE,FILE_DEPLOY_PROJECT);
		projectCreationPage.clickFileSubSubMenu(FILE_NEW_DEPLOYMENT);
		projectCreationPage.selectDeployment(DEPLOYMENT_ON);
		projectCreationPage.clickDeploymentBtn(OK);
		deployment = new Deployment();
		Assert.assertEquals(DEPLOY_URL, deployment.getWaveMakerCloudAccount_CloudTargetTxt(),"Target url didn't match");
		deployment.setUserPassword(usename, password);		
		deployment.clickDeployNowBtn();		
		List<String> deployDialogTxt = deployment.clickCloudAccountOkBtn(usename, password);
		
		Assert.assertTrue(deployDialogTxt.contains("Exporting "+WaveMakerTestCase.projectName+".war, this may take a few minutes"),WaveMakerTestCase.projectName+".war message is not displayed");
		Assert.assertTrue(deployDialogTxt.contains("Deploying "+WaveMakerTestCase.projectName+".war to WaveMaker Cloud, this may take a few minutes"),WaveMakerTestCase.projectName+".war message is not displayed");
		
		
		verifyNewProject();   // Verifies whether New Project is really deployed in Cloud
	
 		
		homePage = new WelcomeStudio(); // Getting instance of Driver to open existing project .
		
		homePage.openExistingProject(projectName);              // Open Newly created project.
		projectCreationPage = new ProjectCreationPage();
		projectCreationPage.clickMenuBar(FILE,FILE_DEPLOY_PROJECT);    
		projectCreationPage.clickFileSubSubMenu(FILE_MANAGE_DEPLOYMENT);
		
		
		deployment = new Deployment();
		deployment.setUserPassword(usename, password);
		deployment.clickManageWMCloudApBtn();
		cloudJeeApplication = new CloudJeeApplication();
		deployment.setUserPassword(usename, password);

		cloudJeeApplication.changeAPPStatus(projectName, STOP);  // Stopping the project 
		
		log.info("Deployment of project  "+ projectName+" is stopped");
		
		Assert.assertEquals(cloudJeeApplication.getAPPStatus(projectName),"STOPPED","App is not started");
		cloudJeeApplication.changeAPPStatus(projectName, START);         // Starting the project 
		
		log.info("Deployment of project  "+ projectName+" is started");
		
		Assert.assertEquals(cloudJeeApplication.getAPPStatus(projectName),"STARTED","App is not stopped");

		cloudJeeApplication.changeAPPStatus(projectName, UNDEPLOY);		
		
		log.info("Deployment of project  "+ projectName+" is undeployed");
		Assert.assertNull(cloudJeeApplication.getAPPStatus(projectName),"App not successfully undeployed");
	}

	/**
	 * Verify whether New project is created.
	 * 
	 */
	private void verifyNewProject() {
		url = deployment.alertGetLinkTextOfDeployment();
		
		Assert.assertTrue(url.startsWith("http"),url+" doesnot has link to depployment url");
		
		//Switch to new window opened
		for(String winHandle : BasePage.driver.getWindowHandles()){
			BasePage.driver.switchTo().window(winHandle);
		}

		homePage.waitForElementLocatedByID("main_logoutButton", 8000);
		Assert.assertEquals(projectName,BasePage.driver.getTitle(),"Title of the Application didn't match");
		Assert.assertEquals("Logout",BasePage.driver.findElement(By.id("main_logoutButton")).getText(),"Logout button text didn't match");
	}

	
	/**
	 * Data Provider .
	 * 
	 * All login credential are stored in this dataprovider.
	 * 
	 * @return
	 */
	@DataProvider(name = "cj")
	public Object[][] cloudjeeUserDetail() {
		Object[][] retObjArr = { { "ashok.c@imaginea.com", "pramati123" }//,
				/*{ "krishnakumar.nellore@imaginea.com", "imaginea" }*/};
		return (retObjArr);
	}


}
